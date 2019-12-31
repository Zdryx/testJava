package com.tsystems.javaschool.tasks.pyramid;

import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        String[] bufferNumbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        int[] inputIntNumbers = new int[inputNumbers.size()];
        boolean isNumber = false;
        for (int i = 0; i <= inputNumbers.size() - 1; i++) {
            String elementList = inputNumbers.get(i).toString();
            String buff = "";
            for (int k = 0; k <= elementList.length() - 1; k++) {
                for (int j = 0; j <= 9; j++) {
                    if (elementList.substring(k, k + 1).equals(bufferNumbers[j])) {
                        isNumber = true;
                        buff = buff + elementList.substring(k, k + 1);
                    }
                }
            }
            if (isNumber) {
                inputIntNumbers[i] = Integer.parseInt(buff);
                isNumber = false;
            } else {
                String messageError = "There are not numbers";
                System.out.println(messageError);
                break;
            }
        }

        int levelPyramide = 1;
        int countLevelPyramide = 0;
        int lengthPiramide = inputIntNumbers.length;
        while (lengthPiramide >= levelPyramide) {
            lengthPiramide = lengthPiramide - levelPyramide;
            levelPyramide++;
            countLevelPyramide++;
        }

        int widthPyramide = countLevelPyramide * 2 - 1;
        int heightPyramide = countLevelPyramide;
        int[][] buffResult = new int[widthPyramide][heightPyramide];
        int[][] result = new int[widthPyramide][heightPyramide];
        // We can create pyramide
        if (lengthPiramide == 0) {
            for (int i = 0; i <= inputIntNumbers.length - 1; i++) {
                for (int j = 0; j <= inputIntNumbers.length - 2; j++) {
                    if (inputIntNumbers[j] >= inputIntNumbers[j + 1]) {
                        int buff = inputIntNumbers[j];
                        inputIntNumbers[j] = inputIntNumbers[j + 1];
                        inputIntNumbers[j + 1] = buff;
                    }
                }
            }
            int index = 0;
            for (int i = 0; i <= heightPyramide - 1; i++) {
                for (int j = 0; j <= i; j++) {
                    buffResult[i][j] = inputIntNumbers[index];
                    index++;
                }
            }
            int vertexPyramide = (widthPyramide - 1) / 2;
            for (int i = 0; i <= heightPyramide-1; i++) {
                if ((i + 1) % 2 == 1) {
                    int indexPlus = vertexPyramide;
                    int indexMinus = vertexPyramide;
                    for (int j = i / 2; j <= i; j++) {
                        result[indexPlus][i] = buffResult[i][j];
                        indexPlus = indexPlus + 2;
                    }
                }
                if ((i + 1) % 2 == 0) {
                    int indexPlus = vertexPyramide + 1;
                    int indexMinus = vertexPyramide - 1;
                    for (int j = (i + 1) / 2; j <= i; j++) {
                        result[indexPlus][i] = buffResult[i][j];
                        indexPlus = indexPlus + 2;
                    }
                    for (int j = (i + 1) / 2 - 1; j >= 0; j--) {
                        result[indexMinus][i] = buffResult[i][j];
                        indexMinus = indexMinus - 2;
                    }
                }
            }
        }
        System.out.println("Piramide");
        for (int k = 0; k <= heightPyramide - 1; k++) {
            System.out.println(" ");
            for (int l = 0; l <= widthPyramide - 1; l++) {
                System.out.print(result[l][k]);
                System.out.print(", ");
            }
        }
        System.out.println(" ");
        return result;
    }


}
