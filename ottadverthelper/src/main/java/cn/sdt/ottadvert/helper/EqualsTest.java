package cn.sdt.ottadvert.helper;

import java.util.HashMap;

/**
 * Created by SDT13411 on 2018/3/9.
 */

public class EqualsTest {
    /**
     * 为什么重写了类的equals方法必须重写其hashCode方法呢
     */
    public static void test() {
        HashMap<PhoneNumber, String> map = new HashMap<>();
        PhoneNumber phoneNumber = new PhoneNumber(707, 867, 5309);
        map.put(phoneNumber, "Jeny");
        String val = map.get(new PhoneNumber(707, 867, 5309));
        System.out.println("val:" + val);

    }

    final static class PhoneNumber {

        private final short prifex;
        private final short areaCode;
        private final short lineNumber;

        public PhoneNumber(int prifex, int areaCode, int lineNumber) {
            this.prifex = (short) prifex;
            this.areaCode = (short) areaCode;
            this.lineNumber = (short) lineNumber;
        }


        @Override
        public boolean equals(Object obj) {
            if (obj != null) {
                if (obj == this)
                    return true;
                else if (obj instanceof PhoneNumber) {
                    PhoneNumber num = (PhoneNumber) obj;
                    return num.areaCode == this.areaCode
                            && num.lineNumber == this.lineNumber
                            && num.prifex == this.prifex;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + prifex;
            result = 31 * result + areaCode;
            result = 31 * result + lineNumber;
            return result;

        }

        private static void rangeCheck(int arg, int max, String name) {

        }
    }
}
