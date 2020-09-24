package com.hong.utilservice.data;

import java.util.*;

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


    public double func(double x) {
        if (x == 0 || x == 1 || x == -1) {
            return x;
        }
        double left = x > 0 ? 0 : x;
        double right = x > 0 ? x : 0;
        double mid;
        double target;
        while (left < right) {
            mid = (left + right) / 2;
            target = mid * mid * mid;
            if (target > x) {
                right = mid;
            } else if (target < x) {
                left = mid;
            } else {
                return mid;
            }
        }
        return 0;
    }

    public void find2(int[] data, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < data.length; i++) {
            if (map.get(data[i]) != null) {
                System.out.println(map.get(data[i]));
                System.out.println(i);
                return;
            }
            map.put(target - data[i], i);
        }
    }


    public int removeElement(int[] nums, int val) {
        if (nums == null) {
            return -1;
        }
        int i = 0;
        int j = nums.length - 1;
        int temp = 0;
        while (i <= j) {
            while (i <= j && nums[i] != val) {
                i++;
            }
            while (i <= j && nums[j] == val) {
                j--;
            }
            if (i < j) {
                temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        return i;
    }


    public int removeElement2(int[] nums, int val) {
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[j] = nums[i];
                j++;
            }
        }
        return j;
    }


    public boolean canConstruct(String ransomNote, String magazine) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < magazine.length(); i++) {
            if (map.get(magazine.charAt(i)) != null) {
                map.put(magazine.charAt(i), map.get(magazine.charAt(i)) + 1);
            } else {
                map.put(magazine.charAt(i), 1);
            }
        }
        for (int i = 0; i < ransomNote.length(); i++) {
            char c = ransomNote.charAt(i);
            if (map.get(c) != null && map.get(c) != 0) {
                map.put(c, map.get(c) - 1);
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 长度最小的子数组
     */
    public int minSubArrayLen(int s, int[] nums) {
        int left = 0;
        int right = 0;
        int sum = 0;
        int ans = Integer.MAX_VALUE;
        while (right < nums.length) {
            sum += nums[right];
            while (sum >= s) {
                ans = Math.min(ans, right - left + 1);
                sum -= nums[left];
                left++;
            }
            right++;
        }
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }

    /**
     * 分糖果
     */
    public int distributeCandies(int[] candies) {
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < candies.length; i++) {
            set.add(candies[i]);
        }
        return Math.min(set.size(), candies.length / 2);
    }


    public void quick(int[] data, int l, int r) {
        int i = l;
        int j = r;
        int num = data[l];
        int temp = 0;
        while (i < j) {
            while (i < j && data[j] > num) {
                j--;
            }
            if (i < j) {
                temp = num;
                num = data[j];
                data[j] = temp;
            }
            while (i < j && data[i] < num) {
                i++;
            }
            if (i < j) {
                temp = num;
                num = data[i];
                data[i] = temp;
            }
        }
        if (l < r) {
            quick(data, l, i - 1);
            quick(data, i + 1, r);
        }
    }

    public static void main(String[] args) {
        ListNode listNode3 = new ListNode(3);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode7 = new ListNode(7);
        ListNode listNode5 = new ListNode(5);
        ListNode listNode6 = new ListNode(6);
        ListNode listNode1 = new ListNode(1);
        ListNode listNode8 = new ListNode(8);
        ListNode listNode9 = new ListNode(9);
        listNode3.next = listNode2;
        listNode2.next = listNode4;
        listNode4.next = listNode7;
        listNode9.next = listNode6;
        listNode2.son = listNode9;
        listNode7.son = listNode8;
        listNode9.son = listNode1;
        listNode8.son = listNode5;
        ArrayDemo demo = new ArrayDemo();
        demo.find2(listNode3);
    }


    public void find(ListNode head) {
        ListNode son = null;
        ListNode son2 = null;
        ListNode node2 = new ListNode();
        ListNode node22 = node2;
        ListNode node3 = new ListNode();
        ListNode node33 = node3;
        ListNode sonNext = null;
        while (head != null) {
            son = head.son;
            if (son != null) {
                //第二层
                sonNext = son;
                while (sonNext != null) {
                    node2.next = sonNext;
                    node2 = sonNext;
                    sonNext = sonNext.next;
                }
                //第三层
                son2 = son.son;
                if (son2 != null) {
                    sonNext = son2;
                    while (sonNext != null) {
                        node3.next = sonNext;
                        node3 = sonNext;
                        sonNext = sonNext.next;
                    }
                }
            }
            System.out.println(head.value);
            head = head.next;
        }
        node22 = node22.next;
        node33 = node33.next;
        while (node22 != null) {
            System.out.println(node22.value);
            node22 = node22.next;
        }
        while (node33 != null) {
            System.out.println(node33.value);
            node33 = node33.next;
        }
    }


    public void find2(ListNode head) {
        ListNode sonAll = new ListNode();
        ListNode node = head;
        while (node != null) {
            test(node, sonAll);
            System.out.println(node.value);
            node = node.next;
        }
        sonAll = sonAll.next;
        while (sonAll != null) {
            System.out.println(sonAll.value);
            sonAll = sonAll.next;
        }
    }

    public void test(ListNode node, ListNode sonAll) {
        ListNode son = node.son;
        ListNode sonNext;
        if (son != null) {
            sonNext = son;
            while (sonNext != null) {
                sonAll.next = sonNext;
                sonAll = sonNext;
                sonNext = sonNext.next;
            }
        }
    }

}

class ListNode {
    int value;
    ListNode next;

    ListNode son;


    ListNode() {
    }

    ListNode(int value) {
        this.value = value;
    }


}
