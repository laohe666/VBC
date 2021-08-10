package com.laohe.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Sun Jingchun
 * @version 5.0.0
 * created at 2021/8/10 18:53
 * copyright @2021 北京沐融信息科技股份有限公司
 */
public class FileUtils {
    /**
     * 写入文件
     * */
    public static String writeToFile(String fileName, String writeContext){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName,true));
            writer.write(writeContext);
        } catch (IOException e) {
            return "写入文件流错误";
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "写入成功";
    }

    /**
     * 读取文件
     * */
    public static Map<String,String> readFile(String filename){
        Map<String,String> map = new ConcurrentHashMap<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));
            while (reader.read() != -1){
                String usrNamAndPsd = reader.readLine();
                try {
                    String[] split = usrNamAndPsd.split("----");
                    String username = split[0];
                    String password = split[1];
                    map.put(username,password);
                } catch (Exception e) {
                    System.out.println(usrNamAndPsd + "------格式错误");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
