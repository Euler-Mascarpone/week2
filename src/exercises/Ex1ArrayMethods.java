package exercises;

import java.util.Arrays;
import java.util.Random;

import static java.lang.StrictMath.round;
import static java.lang.System.*;

/*
 *  Methods with array params and/or return value. Implement methods.
 *
 *  See:
 *  - MathMethods
 *  - ArrayMethods
 */
public class Ex1ArrayMethods {

    public static void main(String[] args) {
        new Ex1ArrayMethods().program();
    }

    final static Random rand = new Random();

    void program() {
        int[] arr = {1, 2, 2, 5, 3, 2, 4, 2, 7};  // Hard coded test data
        // Uncomment one at a time and implement
        // Count occurrences of some element in arr
        out.println(count(arr, 2) == 4);      // There are four 2's
        out.println(count(arr, 7) == 1);

        // Generate array with 100 elements with 25% distribution of -1's and 1's (remaining will be 0)
        arr = generateDistribution(100, 0.25, 0.25);
        out.println(count(arr, 1) == 25);
        out.println(count(arr, -1) == 25);
        out.println(count(arr, 0) == 50);

        // Generate array with 14 elements with 40% 1's and 30% -1's
        arr = generateDistribution(14, 0.4, 0.3);
        out.println(count(arr, 1) == 6);
        out.println(count(arr, -1) == 4);

        for (int i = 0; i < 10; i++) {
            // Random reordering of arr, have to check by inspecting output
            shuffle(arr);
            out.println(Arrays.toString(arr));  // Does it look random?
        }
        int i = 0;

    }


    // ---- Write methods below this ------------

    int count(int[] arr, int occur) {
        int counter = 0;
        for (int i = 0; i < arr.length; i++) { //Runs through all indices
            if (arr[i] == occur) { //If we encounter an element
                counter++; //Increase
            }
        }
        return counter;
    }

    int[] generateDistribution(int size, double ones, double minusOnes) {
        int[] arr = new int[size]; //Create int array with zeros
        long limitFirst = Math.round(ones * size); // Have to use long type because Math.Round returns long
        long limitLast = Math.round(minusOnes * size);
        for (int i = 0; i < limitFirst; i++) { //Start with the -1:s
            arr[i] = 1; //Set them to 1
        }
        for (long i = limitFirst; i < limitFirst + limitLast; i++) { //Starts where we left off above
            arr[Math.toIntExact(i)] = -1;
        }
        return arr;
    }

    void shuffle(int[] arr) {
        int randNumb = -1; //Will be assigned a random number later
        for (int i = 0; i < arr.length; i++) {
            randNumb = rand.nextInt(arr.length ); //Random numbers from 0 to length - 1
            int temp = arr[i]; //Saving current number
            arr[i] = arr[randNumb]; //Replaces by the int at place randNumb
            arr[randNumb] = temp; //Now they have switched places
        }
        //return nothing because we are only modifying the array that was entered.
    }

}