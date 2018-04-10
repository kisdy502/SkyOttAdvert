package cn.sdt.ottadvert.helper;

/**
 * Created by Administrator on 2018/4/3.
 */

public class HeapSort {

    public static void main(String[] args) {
        int sourceArray[] = {8, 3, 4, 9, 1, 7, 2, 5};
        int len = sourceArray.length;
        int tempArray[] = new int[len + 1];
        for (int i = 1; i < len + 1; i++) {
            tempArray[i] = sourceArray[i - 1];
        }

        for (int i = 0; i < len / 2; i++) {
            while (i * 2 < len) {
                if (tempArray[i] > tempArray[2 * i + 1])
                    break;
                int temp = tempArray[i];
                tempArray[i] = tempArray[2 * i + 1];
                tempArray[2 * i + 1] = temp;
            }
        }

        for (int i = 0; i < len + 1; i++) {
            System.out.print(tempArray[i]);
        }
    }
}
