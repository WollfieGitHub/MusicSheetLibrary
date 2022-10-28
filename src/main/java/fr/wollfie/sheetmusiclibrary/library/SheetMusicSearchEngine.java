package fr.wollfie.sheetmusiclibrary.library;

import fr.wollfie.sheetmusiclibrary.dto.Metadata;
import fr.wollfie.sheetmusiclibrary.dto.SheetMusic;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.utils.Utils;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

public class SheetMusicSearchEngine {
    private SheetMusicSearchEngine() {}

    /**
     * Given a bunch of available metadata, reduce the list of metadata to match the reference. The fields examined
     * are the ones returned by the {@link Metadata#getSearchableTokenFields()} method. The Levenshtein distance is used
     * @param reference The reference string used to reduce the number of possibilities
     * @param initialProposals The initial proposal of choices that should be reduced
     * @param maxNbItemsDesired The max number of items to return using the reduction
     * @param maxDistanceCriterion The max distance that any of the results should have relative to the reference string
     * @return A reduced number of results for the search based on the reference striung
     * @param <M> The type of metadata to search among
     */
    public static <M extends Metadata> List<M> updatePropositionsAccordingTo(
            String reference, List<M> initialProposals, int maxNbItemsDesired, int maxDistanceCriterion
    ) {
        List<ProposalRecord<M>> records = new ArrayList<>();
        for (M initialProposal : initialProposals) {
            initialProposal
                .getSearchableTokenFields()
                .parallelStream()
                .forEach(key -> records.add(new ProposalRecord<>(
                        key, LevenshteinDistance.computeDistance(reference, key), initialProposal
                )));
        }

        return records.stream()
                .filter(Utils.lessThan(maxDistanceCriterion, ProposalRecord::getScore))
                .collect(Collectors.toSet())
                .stream()
                .sorted(Comparator.comparing(ProposalRecord::getScore, Comparator.naturalOrder()))
                .limit(maxNbItemsDesired)
                .peek(record -> Logger.debug(record.ref))
                .map(ProposalRecord::getRef)
                .toList();
    }

    private static class ProposalRecord<M extends Metadata> {
        public final String key;
        public int score;
        public final M ref;

        public M getRef() {
            return ref;
        }

        public int getScore() {
            return score;
        }

        public ProposalRecord(String key, int score, M ref) {
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
        private static int computeDistance(String reference, String candidate) {
            int[][] dp = new int[reference.length() + 1][candidate.length() + 1];

            for (int i = 0; i <= reference.length(); i++) {
                for (int j = 0; j <= candidate.length(); j++) {
                    if (i == 0) {
                        dp[i][j] = j;
                    }
                    else if (j == 0) {
                        dp[i][j] = i;
                    }
                    else {
                        dp[i][j] = min(dp[i - 1][j - 1]
                                        + costOfSubstitution(reference.charAt(i - 1), candidate.charAt(j - 1)),
                                dp[i - 1][j] + 1,
                                dp[i][j - 1] + 1);
                    }
                }
            }

            return dp[reference.length()][candidate.length()];
        }

        private static int costOfSubstitution(char a, char b) {
            return a == b ? 0 : 1;
        }

        private static int min(int... numbers) {
            return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
        }
    }
}
