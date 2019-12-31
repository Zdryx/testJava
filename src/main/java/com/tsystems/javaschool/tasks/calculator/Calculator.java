package com.tsystems.javaschool.tasks.calculator;

public class Calculator {
    String[] statementBuf;
    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        statementBuf = new String[2*statement.length()];
        statementBuf[0] = "";
        int indexBuf = -1;
        boolean isPrevCharacter = false;
        boolean isPrevScopeLeft = false;
        boolean isPrevScopeRight = false;
        boolean isPrevNumber =false;
        boolean isNumber = false;
        boolean isScope =false;
        String[] bufferNumbers = {"0","1","2","3","4","5","6","7","8","9"};
        for (int i = 0; i <= statement.length()-1; i++) {
            for (int j=0; j<=9; j++) {
                if(statement.substring(i, i+1).equals(bufferNumbers[j])){
                    isNumber = true;
                }
            }
            if (isNumber){
                if (!isPrevNumber) {
                    indexBuf++;
                    statementBuf[indexBuf] = "";
                }
                isPrevCharacter = false;
                isPrevNumber = true;
                isNumber = false;
                isPrevScopeLeft = false;
                isPrevScopeRight =false;
                statementBuf[indexBuf] += statement.substring(i, i+1);
            } else if ((statement.substring(i, i+1).equals("+") || statement.substring(i, i+1).equals("-") || statement.substring(i, i+1).equals("*") || statement.substring(i, i+1).equals("/") ) && !isPrevCharacter) {
                indexBuf++;
                statementBuf[indexBuf] = "";
                statementBuf[indexBuf] += statement.substring(i, i+1);
                isPrevCharacter = true;
                isPrevScopeLeft = false;
                isPrevScopeRight =false;
                isPrevNumber = false;
            } else if (statement.substring(i, i+1).equals("(") && !isPrevScopeRight ) {
                indexBuf++;
                statementBuf[indexBuf] = "";
                isPrevScopeLeft = true;
                isPrevCharacter = false;
                isPrevNumber = false;
                statementBuf[indexBuf] += statement.substring(i, i+1);
            } else if (statement.substring(i, i+1).equals(")") && !isPrevScopeLeft ) {
                indexBuf++;
                statementBuf[indexBuf] = "";
                isPrevScopeRight = true;
                isPrevCharacter = false;
                isPrevNumber = false;
                statementBuf[indexBuf] += statement.substring(i, i+1);
            } else if (statement.substring(i, i+1).equals(" ")||statement.substring(i, i+1).equals("\"")) {
                //ok
            } else {
                String messageError = "Error, please enter a valid expression. You have to use numbers (0-9) or operations of arifmetic actions (+, -, *, /)";
                System.out.println(messageError);
                statementBuf[0] = null;
                break;
            }
        }
        // find the first expression in scope that we have to calculate
        while (indexBuf != 0 && (statementBuf[0]!= null)) {
            String expression = "";
            int startIndexExpression = 0;
            int endIndexExpression = 0;
            int countBuff = 0;
            for (int i = 0; i <= indexBuf; i++) {
                if (statementBuf[i].equals("(")) {
                    isScope =true;
                    expression = "";
                    startIndexExpression = i+1;
                } else if (statementBuf[i].equals(")") && isScope) {
                    endIndexExpression = i-1;
                    // Delete scope
                    for (int j = endIndexExpression+1; j <= indexBuf-1; j++) {
                        statementBuf[j] = statementBuf[j + 1];
                    }
                    indexBuf--;
                    for (int j = startIndexExpression-1; j <= indexBuf-1; j++) {
                        statementBuf[j] = statementBuf[j + 1];
                    }
                    indexBuf--;
                    startIndexExpression = startIndexExpression -1;
                    endIndexExpression = endIndexExpression -1;
                    String calculatedExpression = calculate(startIndexExpression, endIndexExpression);
                    statementBuf[startIndexExpression]  = calculatedExpression;

                    for (int j = startIndexExpression+1; j <= endIndexExpression ; j++) {
                        statementBuf[j] = statementBuf[j + endIndexExpression-startIndexExpression];
                       // if ((j + endIndexExpression-startIndexExpression) <= indexBuf){
                            countBuff ++;
                        //}
                    }
                    indexBuf=indexBuf-countBuff;
                    isScope =false;

                    break;
                } else if (((i==indexBuf) || statementBuf[i].equals(")")) && !isScope){
                    String calculetedExpression = calculate(0, indexBuf);
                    statementBuf[0]  = calculetedExpression;
                    indexBuf = indexBuf-indexBuf;
                } else{
                    expression = expression + statementBuf[i];
                }

            }
        }
        return statementBuf[0];
    }
    private String calculate(int startIndexExpression, int endIndexExpression) {
        int k = 0;
        int[] indexBuffPriority = new int[endIndexExpression - startIndexExpression + 1];
        String[] statementBufTemp = new String[endIndexExpression - startIndexExpression + 1 + 2];
        //Length of temp statement buffer
        int lengthStatementBufTemp = statementBufTemp.length-2;

        //Create new temp buffer with got exrpession
        for (int i = startIndexExpression; i <= endIndexExpression; i++) {
            statementBufTemp[i - startIndexExpression] = statementBuf[i];
        }
        //Find the operators "*" , "/" and set priority
        for (int i = 0; i <= lengthStatementBufTemp-1; i++) {
            if (statementBufTemp[i].equals("/") || statementBufTemp[i].equals("*")) {
                indexBuffPriority[k] = i;
                k++;
            }
        }

        //Find the operators "-" , "+" and set priority
        for (int i = 0; i <= lengthStatementBufTemp-1; i++) {
            if (statementBufTemp[i].equals("-") || statementBufTemp[i].equals("+")) {
                indexBuffPriority[k] = i;
                k++;
            }
        }

        //Length of buffer's priority
        int lengthIndexBuffPriority = k;

        for (int i = 0; i <= lengthIndexBuffPriority-1; i++) {
            if (statementBufTemp[indexBuffPriority[i]].equals("/")) {
                double tempResult = Double.parseDouble(statementBufTemp[indexBuffPriority[i] - 1]) / Double.parseDouble(statementBufTemp[indexBuffPriority[i] + 1]);
                statementBufTemp[indexBuffPriority[i]-1] = String.valueOf(tempResult);
                lengthStatementBufTemp = lengthStatementBufTemp - 2;
                for (int j = indexBuffPriority[i]; j <= indexBuffPriority[i] + lengthStatementBufTemp - 2; j++) {
                        statementBufTemp[j] = statementBufTemp[j + 2];
                }
                for (int j = i+1; j <= lengthIndexBuffPriority-1; j++) {
                    if(indexBuffPriority[j] >  indexBuffPriority[i]) {
                        indexBuffPriority[j] = indexBuffPriority[j] - 2;
                    }
                }
                continue;
            }

            if (statementBufTemp[indexBuffPriority[i]].equals("*")) {
                double tempResult = Double.parseDouble(statementBufTemp[indexBuffPriority[i] - 1]) * Double.parseDouble(statementBufTemp[indexBuffPriority[i] + 1]);
                statementBufTemp[indexBuffPriority[i]-1] = String.valueOf(tempResult);
                lengthStatementBufTemp = lengthStatementBufTemp - 2;
                for (int j = indexBuffPriority[i]; j <= indexBuffPriority[i] + lengthStatementBufTemp - 2; j++) {
                        statementBufTemp[j] = statementBufTemp[j + 2];
                }
                for (int j = i+1; j <= lengthIndexBuffPriority-1; j++) {
                    if(indexBuffPriority[j] >  indexBuffPriority[i]) {
                        indexBuffPriority[j] = indexBuffPriority[j] - 2;
                    }
                }
                continue;
            }
            if (statementBufTemp[indexBuffPriority[i]].equals("+")) {

                double tempResult = Double.parseDouble(statementBufTemp[indexBuffPriority[i] - 1]) + Double.parseDouble(statementBufTemp[indexBuffPriority[i] + 1]);
                statementBufTemp[indexBuffPriority[i]-1] = String.valueOf(tempResult);
                lengthStatementBufTemp = lengthStatementBufTemp - 2;
                for (int j = indexBuffPriority[i]; j <= indexBuffPriority[i] + lengthStatementBufTemp - 2; j++) {
                        statementBufTemp[j] = statementBufTemp[j + 2];
                }
                for (int j = i+1; j <= lengthIndexBuffPriority-1; j++) {
                    if(indexBuffPriority[j] >  indexBuffPriority[i]) {
                        indexBuffPriority[j] = indexBuffPriority[j] - 2;
                    }
                }
                continue;
            }

            if (statementBufTemp[indexBuffPriority[i]].equals("-")) {
                double tempResult = Double.parseDouble(statementBufTemp[indexBuffPriority[i] - 1]) - Double.parseDouble(statementBufTemp[indexBuffPriority[i] + 1]);
                statementBufTemp[indexBuffPriority[i]-1] = String.valueOf(tempResult);
                lengthStatementBufTemp = lengthStatementBufTemp - 2;
                for (int j = indexBuffPriority[i]; j <= indexBuffPriority[i] + lengthStatementBufTemp - 2; j++) {
                        statementBufTemp[j] = statementBufTemp[j + 2];
                }
                for (int j = i+1; j <= lengthIndexBuffPriority-1; j++) {
                    if(indexBuffPriority[j] >  indexBuffPriority[i]) {
                        indexBuffPriority[j] = indexBuffPriority[j] - 2;
                    }
                }
                continue;
            }
        }
        return statementBufTemp[0];
    }
}
