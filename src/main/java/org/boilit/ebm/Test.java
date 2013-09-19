package org.boilit.ebm;

/**
 * @author Boilit
 * @see
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(toString(100));
    }

    private static void s(Class clazz) {
        if (clazz == Double.class) {
        } else if (clazz == Integer.class) {
        } else if (clazz == Long.class) {
        } else if (clazz == Byte.class) {
        }
    }

    private static final int[] INTEGER1 = new int[]{
            9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE
    };

    private static final int[] INTEGER2 = new int[]{
            4, 7, 10, 14, 17, 20, 24, 27, 30, 31
    };

    private static final int sizeOf(final int x) {
        for (int i = 0; ; i++) {
            if (x <= INTEGER1[i]) {
                return i + 1;
            }
        }
    }

    private static String toString(int v) {
        final int size = sizeOf(v);
        final int length = INTEGER2[size - 1];
        final char[] chars = new char[v < 0 ? size + 1 : size];
        if (v < 0) {
            chars[0] = '-';
        }
        final int value = v < 0 ? -v : v;
        for (int i = 0; i < length; i++) {
            chars[length - i - 1] = (char) ((value >> i) & 1);
        }
        return new String(chars);
    }
}
