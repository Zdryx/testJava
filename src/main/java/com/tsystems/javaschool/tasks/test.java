package com.tsystems.javaschool.tasks;

import com.tsystems.javaschool.tasks.calculator.Calculator;
import com.tsystems.javaschool.tasks.pyramid.PyramidBuilder;
import com.tsystems.javaschool.tasks.subsequence.Subsequence;


import java.util.ArrayList;
import java.util.Arrays;


public class test {
    public static void main(String[] args) {
        Calculator c = new Calculator();
        //System.out.println(c.evaluate("((1-38/12*22)*4)-5")); // Result: 151
        //System.out.println(c.evaluate("7*6/2+8")); // Result: 29
        //System.out.println(c.evaluate("-12)1//(")); // Result: null

        ArrayList A = new ArrayList();
        A.add(333);
        A.add(4);
        A.add(5);
        A.add(2);
        A.add(1);
        A.add(10);
        PyramidBuilder p = new PyramidBuilder();
        //int[][] Pyramide = p.buildPyramid(A);

        Subsequence s = new Subsequence();
        boolean b = s.find(Arrays.asList("A", "B", "C", "D"),
                Arrays.asList("BD", "A", "ABC", "B", "M", "D", "M", "C", "DC", "D"));
        System.out.println(b);
    }
}
