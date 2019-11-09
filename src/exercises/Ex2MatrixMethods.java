package exercises;

import java.util.Arrays;

import static java.lang.StrictMath.*;
import static java.lang.System.out;

/*
 * Methods with array/matrix params and/or return value. Implement methods.
 *
 * See:
 * - Matrices
 */
public class Ex2MatrixMethods {

    public static void main(String[] args) {
        new Ex2MatrixMethods().program();
    }

    void program() {
        int[][] m = {           // Hard coded test data
                {-1, 0, -5, 3},
                {6, 7, -2, 0},
                {9, -2, -6, 8},
                {0, 0, 5, -6}
        };

        // TODO uncomment one at a time and implement

        // Return array with all negatives in m
        int[] negs = getNegatives(m);
        out.println(negs.length == 6);
        out.println(Arrays.toString(negs).equals("[-1, -5, -2, -2, -6, -6]")); // Possibly other ordering!

        // Mark all negatives with a 1, others as 0
        // (create matrix on the fly)
        int[][] marked = markNegatives(new int[][]{
                {1, -2, 3,},
                {-4, 5, -6,},
                {7, -8, 9,},
        });


        /* marked should be
        { {0, 1, 0},
          {1, 0, 1},
          {0, 1, 0} }
        */
        out.println(Arrays.toString(marked[0]).equals("[0, 1, 0]"));
        out.println(Arrays.toString(marked[1]).equals("[1, 0, 1]"));
        out.println(Arrays.toString(marked[2]).equals("[0, 1, 0]"));

        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        // Create matrix from array
        int[][] matrix = toMatrix(arr);
        /* matrix should be
        { {1, 2, 3},
          {4, 5, 6},
          {7, 8, 9} }
        */
        //plot(matrix);  // If manual inspection
        out.println(Arrays.toString(matrix[0]).equals("[1, 2, 3]"));
        out.println(Arrays.toString(matrix[1]).equals("[4, 5, 6]"));
        out.println(Arrays.toString(matrix[2]).equals("[7, 8, 9]"));

        // Sum of all directly surrounding elements to some element in matrix
        // (not counting the element itself)
        // NOTE: Should be possible to expand method to include more distant neighbours
        out.println(sumNeighbours(matrix, 0, 0) == 11);
        out.println(sumNeighbours(matrix, 1, 1) == 40);
        out.println(sumNeighbours(matrix, 1, 0) == 23);
    }

    // -------- Write methods below this -----------------------

    // TODO

    // Use if you like (during development)
    void plot(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            out.println(Arrays.toString(matrix[row]));
        }
    }

    int matSize(int[][] m) {
        int size = 0; //Counts the number of elements in a matrix
        for (int i = 0; i < m.length; i++) {
            size = size + m[i].length; //Adds number of elements in current row
        }
        return size;
    }

    int[] getNegatives(int[][] m) {
        int size = matSize(m); //Gets size of matrix
        int[] negs = new int[size]; //Worst possible case, everything is negative
        int counter = 0; //Will count the number of elements in neg
        for (int i = 0; i < m.length; i++) { //Traversing the rows, m.length gives "height of matrix"
            for (int j = 0; j < m[i].length; j++) { //Traversing each element in a row
                int k = m[i][j];
                if (k < 0) {
                    negs[counter] = k;
                    counter++;
                }
            }
        }
        int[] realNegs = copy(negs, counter); //Removes all zeros from matrix

        return realNegs;
    }

    int[] copy(int[] m, int n) { //Copies the first n elements of m to copy
        int[] copy = new int[n];
        for (int i = 0; i < n; i++) {
            copy[i] = m[i]; //Just copies over data from negs
        }
        return copy;
    }


    int[][] markNegatives(int[][] m) { //Marks negatives with 1 positives with 0
        int width = m[0].length;
        int[][] marked = new int[m.length][width]; //Only works for rectangular 2d arrays
        for (int i = 0; i < m.length; i++) { //Traversing the rows, m.length gives "height of matrix"
            for (int j = 0; j < m[i].length; j++) { //Traversing each element in a row
                int k = m[i][j];
                if (k < 0) {
                    marked[i][j] = 1;
                } else {
                    marked[i][j] = 0;
                }
            }
        }
        return marked;
    }

    int[][] toMatrix(int[] arr) { //Assuming square matrix
        int k = (int) Math.round(sqrt(arr.length)); //If square matrix, then this is int
        int[][] m = new int[k][k]; //Creating matrix
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                m[i][j] = arr[j + k * i]; //Translating from index in matrix to index in array
            }
        }
        return m;
    }

    boolean isValidPosition(int[][] m, int i, int j) { //Will work for rectangular 2d arrays
        int rows = m.length;
        int cols = m[0].length;
        if (0 <= i && 0 <= j && i < rows && j < cols) { //If this is a valid position in a matrix
            return true;
        } else {
            return false;
        }
    }

    int sumNeighbours(int[][] m, int i, int j) {
        int sum = 0;
        for (int iTemp = i - 1; iTemp <= i + 1; iTemp++) { //Variable already initialized
            for (int jTemp = j - 1; jTemp <= j + 1; jTemp++) {
                if (isValidPosition(m, iTemp, jTemp) && (iTemp != i || jTemp != j)) { //If it isn't out of bounds and not the element in question
                    sum = sum + m[iTemp][jTemp];
                }
            }
        }
        return sum;
    }


}
