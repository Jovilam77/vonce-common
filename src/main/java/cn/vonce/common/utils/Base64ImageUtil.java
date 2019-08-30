package cn.vonce.common.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Base64ImageUtil
 *
 * @author Jovi
 * @version 1.0
 * @email 766255988@qq.com
 * @date 2019年7月5日上午10:41:23
 */
public class Base64ImageUtil {

    /**
     * 本地图片转换base64字符串
     *
     * @param imgFile
     * @return
     */
    public static String localImgToBase64(String imgFile) {
        InputStream in;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        return new String(Base64Util.encode(data));
    }

    /**
     * 在线图片转换字节数组
     *
     * @param imgURL
     * @return
     */
    public static byte[] remoteImgToByteArray(String imgURL) {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        try {
            // 创建URL
            URL url = new URL(imgURL);
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            InputStream is = conn.getInputStream();
            // 将内容读取内存中
            int len = -1;
            while ((len = is.read(bytes)) != -1) {
                data.write(bytes, 0, len);
            }
            // 关闭流
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 在线图片转换base64字符串
     *
     * @param imgURL
     * @return
     */
    public static String remoteImgToBase64(String imgURL) {
        // 对字节数组Base64编码
        return new String(Base64Util.encode(remoteImgToByteArray(imgURL)));
    }

    /**
     * base64字符串转换字节数组
     *
     * @param base64
     * @return
     * @throws IOException
     */
    public static byte[] base64ToByteArray(String base64) {
        if (StringUtil.isEmpty(base64)) // 图像数据为空
            return null;
        return Base64Util.decode(base64.toCharArray());
    }

    /**
     * base64字符串转换输入流
     *
     * @param base64
     * @return
     * @throws IOException
     */
    public static InputStream base64ToInputStream(String base64) {
        return new ByteArrayInputStream(base64ToByteArray(base64));
    }

    /**
     * base64字符串转换图片文件
     *
     * @param base64
     * @param imgFilePath
     * @return
     * @throws IOException
     */
    public static boolean base64ToImgFile(String base64, String imgFilePath) throws IOException {
        if (StringUtil.isEmpty(base64)) // 图像数据为空
            return false;
//        String[] base64 = imgStr.split(";");
        byte[] b = Base64Util.decode(base64.toCharArray());
        File file = new File(imgFilePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Files.write(Paths.get(imgFilePath), b);
        return true;
    }

    /**
     * 获取base64的图片后缀
     *
     * @param base64Img
     * @return
     */
    public static String getBase64ImgSuffix(String base64Img) {
        String suffix = null;
        if (base64Img.indexOf("data:image/png;") != -1) {
            suffix = ".png";
        } else if (base64Img.indexOf("data:image/jpeg;") != -1) {
            suffix = ".jpg";
        } else if (base64Img.indexOf("data:image/gif;") != -1) {
            suffix = ".gif";
        } else if (base64Img.indexOf("data:image/x-icon;") != -1) {
            suffix = ".ico";
        }
        return suffix;
    }

}
