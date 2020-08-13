package com.hong.utilservice.data;

public class BST {

    private Node root;

    public void put(int key, String value) {
        root = put(root, key, value);
    }

    /**
     * 找到更新，没有新增结点
     *
     * @param node
     * @param key
     * @param value
     */
    public Node put(Node node, int key, String value) {
        if (node == null) {
            return new Node(key, value, 1);
        }
        if (key < node.key) {
            node.left = put(node.left, key, value);
        } else if (key > node.key) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }
        node.n = size(node.left) + size(node.right) + 1;
        return node;
    }

    public String get(int key) {
        return get(root, key);
    }

    public String get(Node node, int key) {
        if (node == null) {
            return null;
        }
        if (key < node.key) {
            return get(node.left, key);
        } else if (node.key == key) {
            return node.value;
        } else {
            return get(node.right, key);
        }
    }

    public int size() {
        return size(root);
    }

    public int size(Node node) {
        if (node == null) {
            return 0;
        } else {
            return node.n;
        }
    }


    public int minKey() {
        return min(root).key;
    }

    public Node min(Node node) {
        Node left = node.left;
        if (left == null) {
            return node;
        }
        return min(left);
    }

    public int maxKey() {
        return max(root).key;
    }

    public Node max(Node node) {
        Node right = node.right;
        if (right == null) {
            return node;
        }
        return max(right);
    }

    /**
     * 排名为k的结点
     * 树中结点数和k的比较
     *
     * @param k
     * @return
     */
    public int select(int k) {
        Node node = select(root, k);
        if (node == null) {
            return -1;
        }
        return node.key;
    }

    public Node select(Node node, int k) {
        if (node == null) {
            return null;
        }
        int t = size(node.left);
        if (k < t) {
            return select(node.left, k);
        } else if (k > t) {
            return select(node.right, k - t - 1);
        } else {
            return node;
        }
    }

    /**
     * 键排名
     *
     * @param key
     * @return
     */
    public int rank(int key) {
        return rank(root, key);
    }

    public int rank(Node node, int key) {
        if (node.key == key) {
            return size(node.left) + 1;
        } else if (key < node.key) {
            return rank(node.left, key);
        } else {
            return rank(node.right, key) + size(node.left) + 1;
        }
    }


    public static void main(String[] args) {
        BST bst = new BST();
        bst.put(6, "6");
        bst.put(4, "4");
        bst.put(3, "3");
        bst.put(8, "8");
        System.out.println(bst.select(3));
    }

    private class Node {
        private int key;
        private String value;
        private Node left;
        private Node right;
        private int n;

        Node(int key, String value, int n) {
            this.key = key;
            this.value = value;
            this.n = n;
        }

        void setLeft(Node left) {
            this.left = left;
        }

        void setRight(Node right) {
            this.right = right;
        }

    }
}
