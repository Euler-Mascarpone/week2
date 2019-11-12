package exercises;

import java.util.Arrays;

import static java.lang.System.out;

/*
    Find three elements in sorted array that adds up to 0.
    (first found is returned)

 */
public class Ex6ThreeSumToZero {

    public static void main(String[] args) {
        new Ex6ThreeSumToZero().program();
    }


    void program() {
        int[] arr1 = {-25, -10, -7, -3, 2, 4, 8, 10}; // Must be sorted
        int[] arr2 = {0, 1, 2, 4, 8, 10};
        int[] arr3 = {-2, 1, 1};
        int[] arr4 = {0, 0, 0, 0};

        out.println(Arrays.toString(getThreeSum(arr1)));    // [1, 4, 6]
        out.println(Arrays.toString(getThreeSum(arr2)));    // []
        out.println(Arrays.toString(getThreeSum(arr3)));    // [0, 1, 2]
        out.println(Arrays.toString(getThreeSum(arr4)));    // [ 0, 1 ,3 ] or any
    }

    int[] getThreeSum(int[] arr) {
        int k;
        for (int i = 0; i < arr.length; i++) {
            k = arr[i];
            int low = i + 1;
            int high = arr.length - 1;
            while(low < high){
                if(k + arr[low] + arr[high] < 0){ //Then we need to increase low,
                    //Even if high could be increased, since it starts at length - 1 we have already checked all possibilites above the current high
                    //Since Array is sorted
                    low++;
                } else if(k + arr[low] + arr[high] > 0){ //Then we need to decrease high
                    high--;
                } else {
                    int[] index = {i,low,high};
                    return index;
                }
            }
        }
        int[] index = {};
        return index;
    }

}
