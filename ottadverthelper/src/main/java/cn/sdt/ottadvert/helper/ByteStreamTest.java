package cn.sdt.ottadvert.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/4/2.
 */

public class ByteStreamTest {

    public static void main(String args[]) throws IOException {
        System.out.println("enter a char");
        ByteArrayOutputStream bOutput = new ByteArrayOutputStream(12);
        while (bOutput.size() != 10) {
            bOutput.write(System.in.read());
        }
        byte[] b = bOutput.toByteArray();
        System.out.println("Print the enter content");
        for (int x = 0; x < b.length; x++) {
            System.out.print((char) b[x] + "   ");     // 打印字符
        }
        System.out.println("_____________________");

        int c;

        ByteArrayInputStream bInput = new ByteArrayInputStream(b);
        System.out.println("Converting characters to Upper case ");

        for (int y = 0; y < 1; y++) {
            while ((c = bInput.read()) != -1) {
                System.out.println(Character.toUpperCase((char) c));
            }
            bInput.reset();
        }
    }
}
