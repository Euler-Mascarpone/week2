package exercises;

import java.util.Arrays;

import static java.lang.System.out;

public class Ex5DutchSort {

    public static void main(String[] args) {
        new Ex5DutchSort().program();
    }

    void program() {
        int[] arr = {0,1,0,1,2,1,1,1,0,1,2, 2, 2, 0, 0, 0, 1, 1};
        dutchSort(arr);
        out.println(Arrays.toString(arr)); // [0,0,0,1,1,2,2,2]
    }


    // ----------- Methods ----------------

    // TODO


    void dutchSort(int[] arr) {
        int highCount = arr.length - 1; //Index for highest index we don't know is a two
        int lowCount = 0;
        int[] count = {lowCount,highCount};
        int i = 0;
        while (arr[i] == 0) { //runs past the zero elements that doesn't need to be sorted
            i++;
        }
        count[0] = i; //Index for highest index we don't know is zero
        while (i <= count[1]) { //runs through the rest of the code.
            if (arr[i] == 0) {
                leftSwitcher(arr, i, count);//Do stuff
            } else if (arr[i] == 2) {
                rightSwitcher(arr, i, count);//Do other stuff
            } else {
                //Nothing, ones are left as they are.
            }
            i++;
        }
    }

    void leftSwitcher(int[] arr, int i, int[] count) {
        if (i == count[0]) {
            count[0]=count[0] + 1; //Everything already sorted
        } else { //Not sorted, element at lowCount index must be a 1,
            arr[count[0]] = 0;
            arr[i] = 1; //Switching
            count[0]=count[0] + 1;
        }
    }

    void rightSwitcher(int[] arr, int i, int[] count) {

        int k = arr[count[1]];
        while (k == 2) {
            if (i == count[1]) {
                //Then we are done
                break;
            } else {
                k = arr[count[1] - 1];
            }
            count[1] = count[1] - 1;
        }
        //Here k != 2
        if (k == 0){
            leftSwitcher(arr,i,count); //Send zero to the left
        } else { //Then k == 1
            arr[i] = 1;
        }
        arr[count[1]] = 2;
        count[1] = count[1] - 1;
    }

}

