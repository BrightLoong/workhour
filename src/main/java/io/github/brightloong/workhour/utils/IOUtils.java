package io.github.brightloong.workhour.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

/**
 * IO处理相关的工具类
 */
public final class IOUtils {
    public static final String WH_TEMP_DIR = ".wh";

    private static final Logger log = LoggerFactory.getLogger(IOUtils.class);

    /**
     * 私有构造方法
     */
    private IOUtils() {
        super();
    }

    /**
     * 安静的关闭输出资源
     * @param os
     */
    public static void closeQuietly(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                log.error("关闭输出资源出错！", e);
            }
        }
    }

    /**
     * 安静的关闭输入资源
     * @param is
     */
    public static void closeQuietly(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                log.error("关闭输入资源出错！", e);
            }
        }
    }

    /**
     * 获取某相对路径下的绝对资源路径
     * @param fileName 文件路径
     * @return
     */
    public static File getFile(String fileName) {
        String sep = File.separator;
        // 获取文件
        File file = null;
        OutputStream fos = null;
        try {
            // 获取到jar包中的文件输入流，并将其复制到用户目录的某个临时文件夹中
            ClassPathResource cpr = new ClassPathResource(fileName);
            InputStream is = cpr.getInputStream();

            // 创建文件的临时目录和文件，此处应该先创建目录
            String filePath = new StringBuilder(System.getProperty("user.home")).append(sep)
                    .append(WH_TEMP_DIR)
                    .append(sep)
                    .append(fileName)
                    .toString();
            String fileDir = filePath.substring(0, filePath.lastIndexOf(sep));
            new File(fileDir).mkdirs();
            file = new File(filePath);

            // 复制项目中的文件输入流到临时文件的输出流中
            fos = new FileOutputStream(file);
            FileCopyUtils.copy(is, fos);
        } catch (Exception e) {
            log.error("创建临时文件出错！", e);
        } finally {
            IOUtils.closeQuietly(fos);
        }
        return file;
    }

    /**
     * 获取某文件的全路径的文件夹名称
     * @param fileName
     * @return
     */
    public static String getParentFilePath(String fileName) {
        File file = getFile(fileName);
        return file != null ? file.getParent() : "";
    }

    /**
     * 获取某文件的全路径的文件名称
     * @param fileName
     * @return
     */
    public static String getFilePath(String fileName) {
        File file = getFile(fileName);
        return file != null ? file.getPath() : "";
    }

}