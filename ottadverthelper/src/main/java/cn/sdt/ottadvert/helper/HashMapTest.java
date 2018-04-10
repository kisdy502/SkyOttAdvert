package cn.sdt.ottadvert.helper;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/4/8.
 */

public class HashMapTest {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("E", 1);
        map.put("A", 2);
        map.put("D", 2);
        map.put("C", 2);
        map.put("F", 2);
        map.put("B", 2);


        map.put("AA",11);
        map.put("BB",22);
        map.put("DD",44);
        map.put("FF",66);
    }
}
