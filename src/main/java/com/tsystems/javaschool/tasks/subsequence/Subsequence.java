package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        int listXsize =x.size();
        int listYsize =y.size();
        String elementList = "";
        boolean[] flags = new boolean[listXsize];
        boolean mainFlag = false;
        int indexShift = 0;
        for (int i = 0; i<=listXsize-1; i++) {
            elementList = x.get(i).toString();
            for(int j = indexShift; j<=listYsize-1; j++){
                if(elementList.equals(y.get(j).toString())){
                    indexShift = i;
                    flags[i] = true;
                    break;
                }
            }
        }
        mainFlag = true;
        for (int i = 0; i<=listXsize-1; i++) {
            if (!flags[i]){
                mainFlag = false;
            }
        }
        return mainFlag;
    }
}
