package com.hong.utilservice.data;

/**
 * @author liang
 * @description
 * @date 2020/8/27 17:58
 */
public class ArrayDemo {


    public int[] merge(int[] arr1, int[] arr2) {
        int[] data = new int[arr1.length + arr2.length];
        int p = 0;
        int q = 0;
        for (int i = 0; i < data.length; i++) {
            if (p >= arr1.length) {
                data[i] = arr2[q++];
            } else if (q >= arr2.length) {
                data[i] = arr1[p++];
            } else if (arr1[p] >= arr2[q]) {
                data[i] = arr2[q++];
            } else {
                data[i] = arr1[p++];
            }
        }
        return data;
    }

    public int[] merge2(int[] arr1, int[] arr2, int k) {
        int m = 0;
        int n = 0;
        int h = k + arr2.length;
        while (m < h) {
            if (n >= arr2.length) {
                break;
            }
            if (m >= k) {
                arr1[m++] = arr2[n++];
            } else if (arr1[m] < arr2[n]) {
                m++;
            } else {
                for (int j = k - 1; j >= m; j--) {
                    arr1[j + 1] = arr1[j];
                }
                arr1[m++] = arr2[n++];
                k++;
            }
            print(arr1);
        }

        return arr1;
    }

    public int[] merge3(int[] arr1, int[] arr2, int k) {
        int i = k - 1;
        int j = arr2.length - 1;
        int n = arr2.length + k - 1;
        while (i >= 0 && j >= 0) {
            if (arr1[i] > arr2[j]) {
                arr1[n--] = arr1[i--];
            } else {
                arr1[n--] = arr2[j--];
            }
        }
        while (j >= 0) {
            arr1[n--] = arr2[j--];
        }
        return arr1;
    }


    public void print(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        System.out.println("============");
    }

    public void arr(int[] arr) {
        for (int i = 2; i >= 0; i--) {
            arr[i + 1] = arr[i];
        }
        print(arr);
    }


    public static void main(String[] args) {
        int[] arr1 = {4, 9, 10, 0, 0, 0};
        int[] arr2 = {1, 11, 15};
        ArrayDemo demo = new ArrayDemo();
        arr1 = demo.merge3(arr1, arr2, 3);
        demo.print(arr1);
    }
}
