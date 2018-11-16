package cn.chenmixuexi.util;

import java.util.ArrayList;

public class SqlUtils {
    public static String builderSql(String sql) {
        int length = sql.length();
        StringBuilder stringBuilder = new StringBuilder();
        boolean tag = false;
        for (int i = 0; i < length; i++) {
            char c = sql.charAt(i);
            if (c == '#') {
                stringBuilder.append('?');
                tag = true;
            }
            if (!tag) {
                stringBuilder.append(c);
            }
            if (c == '}') {
                tag = false;
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 获取参数列表
     *
     * @param sql
     * @return
     */
    public static ArrayList<String> SqlParameter(String sql) {
        int length = sql.length();
        ArrayList<String> strings = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        boolean tag = true;
        for (int i = 0; i < length; i++) {
            char c = sql.charAt(i);
            if (c == '}') {
                tag = true;
                strings.add(stringBuilder.toString());
                stringBuilder.delete(0, stringBuilder.length());
            }
            if (!tag) {
                stringBuilder.append(c);
            }
            if (c == '{') {
                tag = false;
            }

        }
        return strings;
    }


    public static String builderSql0(String sql) {
        for (; ; ) {
            int begin = sql.indexOf("#{");
            if (begin == -1) {
                break;
            }
            int end = sql.indexOf("}", begin);
            sql = sql.substring(0, begin) + "?" + sql.substring(end + 1);
        }
        return sql;
    }
}
