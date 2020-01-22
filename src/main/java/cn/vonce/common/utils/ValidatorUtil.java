package cn.vonce.common.utils;

import java.text.ParseException;
import java.util.regex.Pattern;

public class ValidatorUtil {

    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则表达式：验证密码<br>
     * ^[a-zA-Z0-9_]{6,16}$ 数字加字母加下划线，默认无下划线
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "((\\+86|0086)?\\s*)((134[0-8]\\d{7})|(((13([0-3]|[5-9]))|(14[5-9])|15([0-3]|[5-9])|(16(2|[5-7]))|17([0-3]|[5-8])|18[0-9]|19(1|[8-9]))\\d{8})|(14(0|1|4)0\\d{7})|(1740([0-5]|[6-9]|[10-12])\\d{7}))";

    /**
     * 电话格式验证
     **/
    private static final String REGEX_PHONE_CALL = "^(\\(\\d{3,4}\\)|\\d{3,4}-)?\\d{7,8}(-\\d{1,4})?$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";

    /**
     * 正则表达式：验证日期
     */
    public static final String REGEX_DETE = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";

    /**
     * 正则表达式：验证sql注入 \\b表示 限定单词边界 比如 select 不通过 1select则是可以的
     */
    private static final String REGEX_SQLINJECT = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
            + "(\\b(select|update|union|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobilePhone
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobilePhone(String mobilePhone) {
        return Pattern.matches(REGEX_MOBILE, mobilePhone);
    }

    /**
     * 验证电话号码的格式
     *
     * @param telePhone 校验电话字符串
     * @return 返回true, 否则为false
     * @author LinBilin
     */
    public static boolean isTelePhone(String telePhone) {
        return Pattern.matches(REGEX_PHONE_CALL, telePhone);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return IdCardUtil.validate(idCard).isOk();
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddress(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

    /**
     * 校验字符串是否存在sql注入
     *
     * @param str
     * @return
     * @author Jovi
     * @date 2018年2月28日下午8:49:09
     */
    public static boolean isSQLInject(String str) {
        Pattern sqlPattern = Pattern.compile(REGEX_SQLINJECT, Pattern.CASE_INSENSITIVE);
        if (sqlPattern.matcher(str).find()) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String username = "fdsdfsdj";
        System.out.println(ValidatorUtil.isUsername(username));
        System.out.println(isMobilePhone("14768000000"));
        System.out.println(isTelePhone("0596-3370653"));

        System.out.println(ValidatorUtil.isEmail("766255988@qq.com"));
        System.out.println(ValidatorUtil.isIDCard("44082519950510095X"));
        System.out.println(ValidatorUtil.isIPAddress("114.114.114.114"));
        System.out.println(ValidatorUtil.isUrl("https://wwws.sss.com"));
        System.out.println(ValidatorUtil.isMobilePhone("17768006232"));
        System.out.println(ValidatorUtil.isPassword("ssswww222222222"));

        System.out.println(isSQLInject("select1 * from essay where 1=1 "));
    }

}
