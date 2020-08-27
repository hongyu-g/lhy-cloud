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
                for (int j = m; j < k; j++) {
                    arr1[j + 1] = arr1[j];
                }
                arr1[m++] = arr2[n++];
            }
            print(arr1);
        }

        return arr1;
    }


    public void print(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        System.out.println("============");
    }


    public static void main(String[] args) {
        int[] arr1 = {1, 2, 4, 0, 0, 0};
        int[] arr2 = {3, 5, 6};
        ArrayDemo demo = new ArrayDemo();
        arr1 = demo.merge2(arr1, arr2, 3);

    }
}
