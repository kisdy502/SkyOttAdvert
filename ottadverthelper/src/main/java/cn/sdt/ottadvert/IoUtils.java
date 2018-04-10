package cn.sdt.ottadvert;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by SDT13411 on 2018/3/21.
 */

public class IoUtils {
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
