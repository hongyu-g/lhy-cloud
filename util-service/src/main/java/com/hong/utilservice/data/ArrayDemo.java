package com.hong.utilservice.data;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
        while (j >= 0) {
            nums1[k--] = nums2[j--];
        }
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

    /**
     * 删除重复出现的元素
     * 0,0,1,1,1,2,2,3,3,4
     * 0,1,2,3,4
     */
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int k = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[k] != nums[i]) {
                k++;
                nums[k] = nums[i];
            }
        }
        return k + 1;
    }

    /**
     * 删除元素
     * 0,1,2,2,3,0,4,    2
     * 0, 1, 3, 0, 4
     */
    public int removeElement(int[] nums, int val) {
        int k = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[k++] = nums[i];
            }
        }
        return k;
    }


    /**
     * [4,3,2,1]
     * [4,3,2,2]
     */
    public int[] plusOne(int[] digits) {
        int temp = 1;
        int num = 0;
        for (int i = digits.length - 1; i >= 0; i--) {
            num = digits[i] + temp;
            digits[i] = num % 10;
            temp = num / 10;
        }
        if (temp < 1) {
            return digits;
        }
        digits = new int[digits.length + 1];
        digits[0] = 1;
        return digits;
    }


    /**
     * 所有偶数元素之后跟着所有奇数元素
     * [3,1,2,4]
     * <p>
     * [2,4,3,1]
     */
    public int[] sortArrayByParity(int[] data) {
        if (data.length == 0) {
            return data;
        }
        int i = 0;
        int j = data.length - 1;
        int temp = 0;
        while (i < j) {
            while (i < j && (data[i] & 1) == 0) {
                i++;
            }
            while (i < j && (data[j] & 1) == 1) {
                j--;
            }
            if (i < j) {
                temp = data[i];
                data[i++] = data[j];
                data[j--] = temp;
            }
        }
        return data;
    }


    /**
     * 第三大数
     * [2, 2, 3, 1]  1
     */
    public int thirdMax(int[] nums) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        int num;
        for (int i = 0; i < nums.length; i++) {
            if (treeSet.size() < 3) {
                treeSet.add(nums[i]);
                continue;
            }
            System.out.println(treeSet);
            num = treeSet.first();
            if (nums[i] > num) {
                treeSet.add(nums[i]);
                if (treeSet.size() > 3) {
                    treeSet.remove(num);
                }
            }
        }
        if (treeSet.size() < 3) {
            return treeSet.last();
        }
        return treeSet.first();
    }


    public int singleNumber(int[] nums) {
        if (nums == null) {
            return -1;
        }
        int num = 0;
        for (int i = 0; i < nums.length; i++) {
            num = num ^ nums[i];
        }
        return num;
    }


    /**
     * 数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     * 假设数组中无重复元素
     *
     * @param nums
     * @param target
     * @return
     */
    public int searchInsert(int[] nums, int target) {
        int k = 0;
        while (k < nums.length) {
            if (nums[k] == target) {
                return k;
            }
            if (nums[k] > target) {
                return k;
            }
            k++;
        }
        return nums.length;
    }


    public int searchInsert2(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int m;
        while (left <= right) {
            m = (left + right) / 2;
            if (nums[m] >= target) {
                right = m - 1;
            } else {
                left = m + 1;
            }
        }
        return left;
    }


    public static void main(String[] args) {
        ArrayDemo demo = new ArrayDemo();
        int[] arr1 = {1, 3, 5, 6};
        System.out.println(demo.searchInsert2(arr1, 0));
    }
}
