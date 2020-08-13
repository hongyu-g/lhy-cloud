package com.hong.utilservice.data;

/**
 * @author liang
 * @description
 * @date 2020/6/23 18:26
 */
public class ListNodeDemo1 {


    /**
     * 合并两个有序的链表
     */
    public static ListNode merge(ListNode listNode1, ListNode listNode2) {
        if (listNode1 == null && listNode2 == null) {
            return null;
        }
        if (listNode1 == null) {
            return listNode2;
        }
        if (listNode2 == null) {
            return listNode1;
        }
        int value1 = listNode1.val;
        int value2 = listNode2.val;
        ListNode node;
        if (value1 < value2) {
            node = listNode1;
            node.next = merge(listNode1.next, listNode2);
        } else {
            node = listNode2;
            node.next = merge(listNode1, listNode2.next);
        }
        return node;
    }

    /**
     * 正序删除
     */
    public ListNode removeNth(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        if (n == 1) {
            head = head.next;
        } else {
            ListNode node;
            ListNode p = head;
            for (int i = 1; i < n - 1; i++) {
                p = p.next;
            }
            node = p.next;
            p.next = node.next;
            node = null;
        }
        return head;
    }

    /**
     * 删除链表 倒数第 k 个节点
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        int size = 0;
        for (ListNode node = head; node != null; node = node.next) {
            size++;
        }
        int index = size - n + 1;
        return removeNth(head, index);
    }

    /**
     * 删除链表 倒数第 k 个节点
     * 一遍循环
     * 1->2->3->4->5->6, 和 n = 2
     * <p>
     * 1->2->3->4 ->6
     */
    public ListNode removeNthFromEnd2(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode p1 = dummy;
        ListNode p2 = dummy;
        for (int i = 1; i <= n; i++) {
            p1 = p1.next;
        }
        while (p1.next != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        p2.next = p2.next.next;
        return dummy.next;
    }


    /**
     * 1->2->3
     * 3->2->1
     */
    public ListNode reverse(ListNode node) {
        ListNode pre = null;
        ListNode next = null;
        while (node != null) {
            next = node.next;
            node.next = pre;
            pre = node;
            node = next;
        }
        return pre;
    }


    public ListNode deleteNode(ListNode head, int val) {
        if (head == null) {
            return null;
        }
        if (head.val == val) {
            return head.next;
        }
        ListNode pre = head;
        ListNode node = head.next;
        while (node != null) {
            if (node.val == val) {
                pre.next = node.next;
                break;
            }
            pre = node;
            node = node.next;
        }
        return head;
    }


    public static void main(String[] args) {
        ListNodeDemo1 demo1 = new ListNodeDemo1();
        ListNode node1 = demo1.new ListNode(1);
        ListNode node2 = demo1.new ListNode(2);
        ListNode node3 = demo1.new ListNode(3);
        ListNode node4 = demo1.new ListNode(4);
        ListNode node5 = demo1.new ListNode(5);
        ListNode node6 = demo1.new ListNode(6);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;

        demo1.deleteNode(node1, 2).print();
    }


    private class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        void print() {
            for (ListNode node = this; node != null; node = node.next) {
                System.out.println(node.val);
            }
            System.out.println("=========================");
        }
    }

}