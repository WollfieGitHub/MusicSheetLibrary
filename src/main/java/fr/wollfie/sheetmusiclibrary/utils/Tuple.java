package fr.wollfie.sheetmusiclibrary.utils;

import fr.wollfie.sheetmusiclibrary.io.serialization.JsonSerializable;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public record Tuple<T1, T2>(
        T1 left,
        T2 right
) implements JsonSerializable {
    
    public static <T1, T2> Tuple<T1, T2> of(T1 t1, T2 t2) {
        return new Tuple<>(t1, t2);
    }
    
    public static <R1, T1, T2> Stream<Tuple<R1, T2>> mapLeft(Stream<Tuple<T1, T2>> stream, Function<T1, R1> func) {
        return stream.map(tuple -> new Tuple<>(func.apply(tuple.left), tuple.right));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple<?, ?> tuple = (Tuple<?, ?>) o;

        if (!Objects.equals(left, tuple.left)) return false;
        return Objects.equals(right, tuple.right);
    }

    @Override
    public int hashCode() {
        int result = left != null ? left.hashCode() : 0;
        result = 31 * result + (right != null ? right.hashCode() : 0);
        return result;
    }
}
