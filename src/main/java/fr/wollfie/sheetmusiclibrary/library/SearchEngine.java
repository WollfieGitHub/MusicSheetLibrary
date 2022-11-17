package fr.wollfie.sheetmusiclibrary.library;

import fr.wollfie.sheetmusiclibrary.dto.MetadataObject;
import fr.wollfie.sheetmusiclibrary.utils.QuadFunction;
import fr.wollfie.sheetmusiclibrary.utils.TriFunction;
import fr.wollfie.sheetmusiclibrary.utils.Tuple;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.Math.*;

public class SearchEngine {
    private SearchEngine() {}

    // String must match at least 15%  
    private static final double DEFAULT_MAX_DISTANCE = 0.15;

    private static final DistanceAlgorithm DEFAULT_ALGORITHM = DistanceAlgorithm.SimpleLevenshtein;
    
    private static final BiFunction<Character, Character, Double> DEFAULT_GAMMA_COST = (c1, c2) -> {
        if (Character.toLowerCase(c1) == Character.toLowerCase(c2)) { return 0.0; }
        // Insertion costs 1
        if (c1 == '\0') { return 1.0; }
        // Deletion costs 1.5
        if (c2 == '\0') { return 1.5; }
        // Change (Can be typo) cost 1.25
        return 1.25;
    };
    
    private static final double DEFAULT_THRESHOLD = 0.2;
    private static final TriFunction<String, String, Double, Double> NORMALIZE_FUNCTION = 
            (ref, candidate, score) -> {
                // Discard this tuple
                if (candidate.length() == 0 && ref.length() == 0) { return 0.0; }
                // Otherwise the correspondence should be more than the ratio typed minus some margin for error
                return score / Math.max(ref.length(), candidate.length());
            };

    /**
     * Given a bunch of available metadata, reduce the list of metadata to match the reference. The fields examined
     * are the ones returned by the {@link MetadataObject#getSearchableTokenFields()} method. The Normalized Edit
     * Distance metric is used by default
     * @param reference The reference string used to reduce the number of possibilities
     * @param initialProposals The initial proposal of choices that should be reduced
     * @param maxNbItemsDesired The max number of items to return using the reduction
     * @param threshold Each result must resemble the reference at least <{@code threshold}>% to be included
     *                  according to {@link SearchEngine#NORMALIZE_FUNCTION}
     * @return A reduced number of results for the search based on the reference string
     * @param <M> The type of metadata to search among
     */
    public static <M extends MetadataObject> List<Tuple<M, Double>> updatePropositionsAccordingTo(
            String reference, List<M> initialProposals, int maxNbItemsDesired, double threshold
    ) {
        return updatePropositionsAccordingTo(
                reference, initialProposals,
                MetadataObject::getSearchableTokenFields,
                maxNbItemsDesired, DEFAULT_ALGORITHM, threshold
        );
    }
    
    /**
     * Given a bunch of available metadata, reduce the list of metadata to match the reference. The fields examined
     * are the ones returned by the {@link MetadataObject#getSearchableTokenFields()} method. The Normalized Edit
     * Distance metric is used by default
     * @param reference The reference string used to reduce the number of possibilities
     * @param initialProposals The initial proposal of choices that should be reduced
     * @param maxNbItemsDesired The max number of items to return using the reduction
     * @return A reduced number of results for the search based on the reference striung
     * @param <M> The type of metadata to search among
     */
    public static <M extends MetadataObject> List<M> updatePropositionsAccordingTo(
            String reference, List<M> initialProposals, int maxNbItemsDesired
    ) {
        return updatePropositionsAccordingTo(
                reference, initialProposals, 
                MetadataObject::getSearchableTokenFields,
                maxNbItemsDesired, DEFAULT_ALGORITHM, DEFAULT_THRESHOLD
        ).parallelStream().map(Tuple::left).toList();
    }

    /**
     * Given a bunch of available metadata, reduce the list of metadata to match the reference. The fields examined
     * are the ones returned by the {@link MetadataObject#getSearchableTokenFields()} method. The Normalized Edit
     * Distance metric is used by default
     * @param reference The reference string used to reduce the number of possibilities
     * @param initialProposals The initial proposal of choices that should be reduced
     * @param tokenExtractor Extract string of interest from an object
     * @param maxNbItemsDesired The max number of items to return using the reduction
     * @return A reduced number of results for the search based on the reference striung
     * @param <M> The type of metadata to search among
     */
    public static <M> List<M> updatePropositionsAccordingTo(
            String reference, List<M> initialProposals, Function<M, List<String>> tokenExtractor, int maxNbItemsDesired
    ) {
        return updatePropositionsAccordingTo(
                reference, initialProposals,
                tokenExtractor,
                maxNbItemsDesired, DEFAULT_ALGORITHM, DEFAULT_THRESHOLD
        ).parallelStream().map(Tuple::left).toList();
    }
            
    /**
     * Given a bunch of available metadata, reduce the list of metadata to match the reference. The fields examined
     * are the ones returned by the {@link MetadataObject#getSearchableTokenFields()} method. The Levenshtein distance is used
     * @param reference The reference string used to reduce the number of possibilities
     * @param initialProposals The initial proposal of choices that should be reduced
     * @param tokenExtractor Extract string of interest from an object
     * @param maxNbItemsDesired The max number of items to return using the reduction
     * @param algorithm The algorithm used to determine the distance between two words
     * @param threshold Each result must resemble the reference at least <{@code threshold}>% to be included
     *                  according to {@link SearchEngine#NORMALIZE_FUNCTION}
     * @return A reduced number of results for the search based on the reference string
     * @param <M> The type of metadata to search among
     */
    public static <M> List<Tuple<M, Double>> updatePropositionsAccordingTo(
            String reference, List<M> initialProposals,
            Function<M, List<String>> tokenExtractor,
            int maxNbItemsDesired,
            DistanceAlgorithm algorithm,
            double threshold
    ) {
        
        List<ProposalRecord<M>> records = new ArrayList<>();
        for (M initialProposal : initialProposals) {
            tokenExtractor.apply(initialProposal)
                .forEach(key -> {
                    String keyLowered = key.toLowerCase();
                    double score = switch (algorithm) {
                        case SimpleLevenshtein -> LevenshteinDistance.computeDistance(reference.toLowerCase(), keyLowered);
                        case NED -> NormalizedEditDistance.computeDistance(reference.toLowerCase(), keyLowered, DEFAULT_GAMMA_COST);
                    };
                    records.add(new ProposalRecord<>(key, NORMALIZE_FUNCTION.apply(reference, keyLowered, score), initialProposal));
                });
        }

        return records.stream()
                .filter(record -> threshold >= record.score)
                .collect(Collectors.toSet())
                .stream()
                .sorted(Comparator.comparing(ProposalRecord::getScore, Comparator.reverseOrder()))
                .limit(maxNbItemsDesired)
                .map(Tuple.fromMapping(ProposalRecord::getRef, ProposalRecord::getScore))
                .toList();
    }
    
    public enum DistanceAlgorithm {
        SimpleLevenshtein,
        NED
    }

    private static class ProposalRecord<M> {
        public final String key;
        public double score;
        public final M ref;

        public M getRef() {
            return ref;
        }

        public double getScore() {
            return score;
        }

        public ProposalRecord(String key, double score, M ref) {
            this.key = key;
            this.score = score;
            this.ref = ref;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProposalRecord<?> that = (ProposalRecord<?>) o;
            return ref.equals(that.ref);
        }

        @Override
        public int hashCode() {
            return ref.hashCode();
        }
    }
    
    private static class LevenshteinDistance {
        private LevenshteinDistance() {}
        
        /**
         * Computes the distance between the two strings, taking into account the number of letters in common
         * and the order in which they are placed
         * @implNote Using dynamic programming : 
         *      <a href="https://www.baeldung.com/java-levenshtein-distance">Reference to implementation here</a>
         * @param reference The reference string
         * @param candidate The candidate string which might be what the reference string is targeting
         * @return The distance between the two strings
         *
         */
        private static double computeDistance(String reference, String candidate) {
            if (reference.length() == 0 && candidate.length() == 0) { return Integer.MAX_VALUE; }
            int[][] dp = new int[reference.length() + 1][candidate.length() + 1];

            for (int i = 0; i <= reference.length(); i++) {
                for (int j = 0; j <= candidate.length(); j++) {
                    
                    if (i == 0) { dp[i][j] = j; }
                    else if (j == 0) { dp[i][j] = i; }
                    else {
                        dp[i][j] = min(dp[i - 1][j - 1] + costOfSubstitution(reference.charAt(i - 1), candidate.charAt(j - 1)),
                                dp[i - 1][j] + 1,
                                dp[i][j - 1] + 1);
                    }
                }
            }

            return 1 - dp[reference.length()][candidate.length()] / (double)Math.max(reference.length(), candidate.length());
        }

        private static int costOfSubstitution(char a, char b) {
            return a == b ? 0 : 1;
        }
    }
    
    
    private static class NormalizedEditDistance {
        private NormalizedEditDistance() {}

        /**
         * Source : IEEE TRANSACTIONS ON PAITERN ANALYSIS : Computation of Normalized Edit Distance and Applications
         * In this paper Normalized Edit Distance
         * @param reference The reference string
         * @param candidate The candidate string which might be what the reference string is targeting
         * @return The distance between the two strings, normalized
         */
        private static double computeDistance(String reference, String candidate, BiFunction<Character, Character, Double> gamma) {
            int P, C, ofs, ofsi, ofsj, ofsij;
            int temp;
            char[] X = reference.toCharArray(), Y = candidate.toCharArray();
            final int Previous = 0, Current = 1;
            double[][][] D = new double[Current-Previous+1][Y.length+1][Y.length+2+1];
            double result;
            
            P = Previous;
            C = Current;

            D[P][0][0] = Double.MAX_VALUE;
            D[P][0][1] = 0;
            D[P][0][2] = Double.MAX_VALUE;
            
            
            for (int j = 1; j <= Y.length; j++) {
                D[P][j][0] = Double.MAX_VALUE;
                D[P][j][1] = D[P][j-1][1] + gamma.apply('\0', Y[j-1]);
                D[P][j][2] = Double.MAX_VALUE;
            }
            for (int i = 1; i <= X.length; i++) {
                D[C][0][0] = Double.MAX_VALUE;
                D[C][0][1] = D[P][0][1] + gamma.apply(X[i-1], '\0');
                D[C][0][2] = Double.MAX_VALUE;

                for (int j = 1; j <= Y.length; j++) {
                    ofs = max(i, j) -1;
                    ofsi = max(i-1, j) -1;
                    ofsj = max(i, j-1) -1;
                    ofsij = max(i-1, j-1) -1;
                    
                    D[C][j][0] = Double.MAX_VALUE;
                    for (int k = max(i, j); k <= i+j; k++) {
                        D[C][j][k-ofs] = min(
                                D[P][j][k-1-ofsi] + gamma.apply(X[i-1], '\0'),
                                D[C][j-1][k-1-ofsj] + gamma.apply('\0', Y[j-1]),
                                D[P][j-1][k-1-ofsij] + gamma.apply(X[i-1], Y[j-1])
                        );
                    }
                    D[C][j][i+j+1-ofs] = Double.MAX_VALUE;
                }
                temp = C;
                C = P;
                P = temp;
            }
            result = Double.MAX_VALUE;
            for (int k = X.length; k <= X.length+Y.length; k++) {
                result = min(result, ( D[P][Y.length][k-X.length+1]/ k ));
            }
            return 1-result;
        }
    }

    private static int min(int... numbers) {
        return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
    }
    private static double min(double... numbers) {
        return Arrays.stream(numbers).min().orElse(Double.MAX_VALUE);
    }
}
