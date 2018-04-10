package cn.sdt.ottadvert.helper;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Administrator on 2018/4/2.
 */

public class BinTree<E> {


    Node root;

    public BinTree(E data) {
        this.root = new Node(data);
    }

    public BinTree(Node root) {
        this.root = root;
    }

    E getLeftChild(Node parent) {
        if (parent != null && parent.left != null) {
            return (E) parent.left.data;
        }
        return null;
    }

    E getRightChild(Node parent) {
        if (parent != null && parent.right != null) {
            return (E) parent.right.data;
        }
        return null;
    }

    Node insert(Node parent, E data, boolean isLeft) {
        Node node = new Node(data);
        if (parent != null) {
            if (isLeft && parent.left == null) {
                parent.left = node;
                return node;
            } else if (!isLeft && parent.right == null) {
                parent.right = node;
                return node;
            } else {
                System.out.printf("hasData");
            }
        } else {
            System.out.printf("parent is null");
        }
        return null;
    }

    int deep() {
        return deep(root);
    }

    private int deep(Node node) {
        if (node == null)
            return 0;
        if (node.left == null && node.right == null) {
            return 1;
        } else {
            int ld = deep(node.left);
            int rd = deep(node.right);
            int max = ld > rd ? ld : rd;
            return max + 1;
        }
    }


    static class Node<E> {
        E data;
        Node left;
        Node right;

        public Node(E data) {
            this.data = data;
        }
    }

    //根节点-左节点-右节点 (先序遍历)
    public static List<Node> preorderTraversal(Node root) {
        List<Node> nodeList = new ArrayList<>();
        if (root != null) {
            nodeList.add(root);
            if (root != null) {
                Node left = root.left;
                List list = preorderTraversal(left);
                if (list != null)
                    nodeList.addAll(list);
            }
            if (root != null) {
                Node right = root.right;
                List list = preorderTraversal(right);
                if (list != null)
                    nodeList.addAll(list);
            }
        }
        return nodeList;
    }

    //左节点-右节点-根节点
    public static List<Node> postOrderTraversal(Node root) {
        List<Node> nodeList = new ArrayList<>();
        if (root.left != null) {
            List<Node> list = postOrderTraversal(root.left);
            nodeList.addAll(list);
        }
        if (root.right != null) {
            List<Node> list = postOrderTraversal(root.right);
            nodeList.addAll(list);
        }
        nodeList.add(root);
        return nodeList;
    }

    //左节点-根节点-右节点
    public static List<Node> middleOrderTraversal(Node root) {
        List<Node> nodeList = new ArrayList<>();
        if (root.left != null) {
            List<Node> list = middleOrderTraversal(root.left);
            nodeList.addAll(list);
        }
        nodeList.add(root);
        if (root.right != null) {
            List<Node> list = middleOrderTraversal(root.right);
            nodeList.addAll(list);
        }

        return nodeList;
    }


    public static List<Node> breadthTraversal(Node root) {
        List<Node> nodeList = new ArrayList<>();
        ArrayDeque<Node> queue = new ArrayDeque<>();
        if (root != null) {
            queue.offer(root);
        }
        while (!queue.isEmpty()) {
            nodeList.add(queue.peek());//获取队列头元素，不取出
            Node node = queue.poll(); // 取出
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }

        return nodeList;
    }


    public static void printfTree(List<Node> list) {
        for (Node node : list) {
            System.out.println("val:" + node.data);
        }
    }


    public static void main(String[] args) {
        Node root = new Node("A");
        BinTree<String> binTree = new BinTree<String>(root);
        Node left1 = binTree.insert(root, "B", true);
        Node right1 = binTree.insert(root, "C", false);

        Node left2 = binTree.insert(left1, "D", true);
        Node right2 = binTree.insert(left1, "E", false);

        Node left22 = binTree.insert(right1, "F", true);
        Node right22 = binTree.insert(right1, "G", false);


        Node left3 = binTree.insert(left2, "H", true);
        Node left33 = binTree.insert(right22, "I", true);

        System.out.println("deep:" + binTree.deep());


        System.out.println("先序遍历结果:\n");
        List<Node> list = BinTree.preorderTraversal(root);
        printfTree(list);

        System.out.println("中序遍历结果:\n");
        list = BinTree.middleOrderTraversal(root);
        printfTree(list);

        System.out.println("后序遍历结果:\n");
        list = BinTree.postOrderTraversal(root);
        printfTree(list);


        System.out.println("广度遍历结果:\n");
        list = BinTree.breadthTraversal(root);
        printfTree(list);
    }
}
