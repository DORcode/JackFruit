package common.lib.utils;

/**
 * @项目名称 JackFruit
 * @类：common.lib.utils
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/5 16:34
 * @修改
 * @修改时期 2017/1/5 16:34
 */
public class CommonUtils {

    /**
     * 字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0 || str.equalsIgnoreCase("null") || str.isEmpty() || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }
}
