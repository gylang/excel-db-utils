package com.gylang.excel.db.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件读取
 *
 * @author gylang,
 * date 2020/4/25,
 * @version 1.0
 */
public class FileUtils {


    /**
     * 读取文件
     *
     * @param filePath 文件路径
     * @return 文件文本数据
     */
    public static String readFile(String filePath) {

        StringBuilder stringBuffer = new StringBuilder();
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader(filePath))) {

            String s;
            while (null != (s = bufferedReader.readLine())) {
                stringBuffer.append(s).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("文件读取失败 请检查文件目录是否正确");
        } catch (IOException e) {
            System.out.println("读取数据异常");
        }
        return stringBuffer.toString();
    }

    /**
     * 读取路径下的文件列表
     *
     * @param path 路径
     * @return 文件路径列表
     */
    public static List<String> readPath(String path) {
        List<String> fileList = new ArrayList<>();
        String[] list = new File(path).list();
        if (null != list) {
            for (String fileName : list) {
                fileList.add(path + fileName);
            }
        }
        return fileList;
    }

    /**
     * 写入
     *
     * @param filePath 文件路径
     * @return 文件文本数据
     */
    public static void writeFile(String filePath, String content) {


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            writer.append(content);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
