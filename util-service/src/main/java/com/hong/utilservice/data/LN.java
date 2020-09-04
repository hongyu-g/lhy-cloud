package com.hong.utilservice.data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author liang
 * @description
 * @date 2020/8/25 11:16
 */
public class LN {


    /**
     * 1->2->3
     * <p>
     * 4->5->6
     */
    public ListNode merge(ListNode node1, ListNode node2) {
        if (node1 == null && node2 == null) {
            return null;
        }
        if (node1 == null) {
            return node2;
        }
        if (node2 == null) {
            return node1;
        }
        ListNode node;
        if (node1.value < node2.value) {
            node = node1;
            node.next = merge(node1.next, node2);
        } else {
            node = node2;
            node.next = merge(node1, node2.next);
        }
        return node;
    }

    public ListNode merge2(ListNode node1, ListNode node2) {
        if (node1 == null && node2 == null) {
            return null;
        }
        if (node1 == null) {
            return node2;
        }
        if (node2 == null) {
            return node1;
        }
        ListNode node = new ListNode();
        ListNode temp = node;
        while (node1 != null && node2 != null) {
            if (node1.value < node2.value) {
                temp.next = node1;
                node1 = node1.next;
            } else {
                temp.next = node2;
                node2 = node2.next;
            }
            temp = temp.next;
        }
        if (node1 != null) {
            temp.next = node1;
        }
        if (node2 != null) {
            temp.next = node2;
        }
        return node.next;
    }


    public ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode odd = new ListNode();
        ListNode oddTemp = odd;
        ListNode even = new ListNode();
        ListNode evenTemp = even;
        int index = 1;
        while (head != null) {
            if ((index & 1) == 1) {
                oddTemp.next = head;
                oddTemp = head;
            } else {
                evenTemp.next = head;
                evenTemp = head;
            }
            head = head.next;
            index++;
        }
        oddTemp.next = even.next;
        evenTemp.next = null;
        return odd.next;
    }

    /**
     * 奇偶链表
     * 输入: 1->2->3->4->5->NULL
     * 输出: 1->3->5->2->4->NULL
     */
    public ListNode oddEvenList2(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenHead = even;
        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }

    /**
     * 是不是环形链表
     */
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            if (set.contains(head)) {
                return true;
            }
            set.add(head);
            head = head.next;
        }
        return false;
    }


    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        int index = 0;
        ListNode node = head;
        while (node != null) {
            node = node.next;
            index++;
        }
        int p = index - n;
        if (p == 0) {
            return head.next;
        }
        ListNode node1 = head;
        while (p != 1) {
            node1 = node1.next;
            p--;
        }
        ListNode next = node1.next;
        if (next != null) {
            next = next.next;
        }
        node1.next = next;
        return head;
    }

    /**
     * 给定一个链表: 1->2->3->4->5, 和 n = 2.
     * <p>
     * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
     */
    public ListNode removeNthFromEnd2(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        int k = n + 1;
        ListNode p1 = head;
        while (k > 1 && p1 != null) {
            p1 = p1.next;
            k--;
        }
        if (p1 == null) {
            return head.next;
        }
        while (p1 != null) {

            p1 = p1.next;
        }
        return head;
    }

    void print(ListNode node) {
        while (node != null) {
            System.out.print(node.value);
            node = node.next;
        }
        System.out.println();
    }

    class ListNode {
        int value;
        ListNode next;


        ListNode() {
        }

        ListNode(int value) {
            this.value = value;
        }


    }


    public static void main(String[] args) {
        LN ln = new LN();
        ListNode node1 = ln.new ListNode(1);
        ListNode node2 = ln.new ListNode(2);
        ListNode node3 = ln.new ListNode(3);
        ListNode node4 = ln.new ListNode(4);
        ListNode node5 = ln.new ListNode(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        ListNode node = ln.removeNthFromEnd(node1, 4);
        ln.print(node);
    }

}


