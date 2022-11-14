package fr.wollfie.sheetmusiclibrary.utils;

import static java.lang.Math.*;

/**
 * Should map [0, 1] to [0, 1]
 */
@FunctionalInterface
public interface Curve {
    
    double apply(double t);

    Curve EXPONENTIAL = t -> (exp(1-t) - 1) / (E-1);
    
    Curve IDENTITY = t -> t;
    Curve ZERO = t -> 0;
    Curve ONE = t -> 1;
}
