package fr.wollfie.sheetmusiclibrary.utils;

@FunctionalInterface
public interface QuadFunction<A1, A2, A3, A4, R> {
    
    R apply(A1 a1, A2 a2, A3 a3, A4 a4);
}
