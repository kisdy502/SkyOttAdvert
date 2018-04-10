package cn.sdt.ottadvert.helper;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/30.
 */

public class HuffmanTree {

    private final static String TAG = "Huffman";

    static class Node<E> {
        E data;
        int weight;
        Node leftChild;
        Node rightChild;

        public Node(E data, int weight) {
            this.data = data;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return data + "," + weight;
        }
    }


    public static List<Node> init() {
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(new Node("A", 9));
        nodeList.add(new Node("B", 12));
        nodeList.add(new Node("C", 3));
        nodeList.add(new Node("D", 6));
        nodeList.add(new Node("E", 5));
        nodeList.add(new Node("F", 15));
        return nodeList;
    }

    /**
     * 构建哈夫曼树
     *
     * @param nodeList
     * @return
     */
    public static Node createHuffmanTree(List<Node> nodeList) {
        for (Node node : nodeList) {
            Log.d(TAG, "weight:" + node.weight);
        }
        while (nodeList.size() > 1) {
            quick(nodeList, 0, nodeList.size() - 1);
            Node left = nodeList.get(nodeList.size() - 1);
            Node right = nodeList.get(nodeList.size() - 2);
            Node parent = new Node(null, left.weight + right.weight);
            parent.leftChild = left;
            parent.rightChild = right;
            nodeList.remove(nodeList.size() - 1);
            nodeList.remove(nodeList.size() - 1);
            nodeList.add(parent);
        }
        return nodeList.get(0);
    }


    /**
     * 快速排序法
     *
     * @param nodeList
     * @param low
     * @param high
     */
    private static void quick(List<Node> nodeList, int low, int high) {
        int start = low, end = high;
        int currentWeight = nodeList.get(start).weight;

        Node temp;
        while (start < end) {
            while (start < end && nodeList.get(end).weight >= currentWeight) {
                end--;
            }
            if (nodeList.get(end).weight < currentWeight) {
                temp = nodeList.get(end);
                nodeList.set(end, nodeList.get(start));
                nodeList.set(start, temp);
            }

            while (start < end && nodeList.get(start).weight <= currentWeight) {
                start++;
            }
            if (nodeList.get(start).weight > currentWeight) {
                temp = nodeList.get(end);
                nodeList.set(end, nodeList.get(start));
                nodeList.set(start, temp);
            }
        }
        if (start > low)
            quick(nodeList, low, start - 1);
        if (start < end)
            quick(nodeList, start + 1, end);

    }

    public static void main(String[] args) {
        List<HuffmanTree.Node> list = HuffmanTree.init();
        HuffmanTree.Node haffmanTree = HuffmanTree.createHuffmanTree(list);
    }
}
