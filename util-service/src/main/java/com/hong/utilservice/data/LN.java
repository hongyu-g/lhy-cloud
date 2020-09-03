package com.hong.utilservice.data;

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
        ListNode node = ln.oddEvenList2(node1);
        ln.print(node);
    }

}


