package com.laohe;

import com.laohe.task.TaskStart;
import com.laohe.util.FileUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Hello world!*
 */
public class App 
{
    public static void main( String[] args ) {
        //主函数
        Scanner scanner = new Scanner(System.in);
        System.out.println("1.请输入你要读取的文件路径:");
        String readFile = scanner.next();
        System.out.println("2.请输入你要存放爬取数据的文件路径:");
        String writeFile = scanner.next();
        System.out.println("-----------开始爆破-----------");
        Map<String,String> map = FileUtils.readFile(readFile);
        //开始爆破线程
        TaskStart taskStart = new TaskStart((ConcurrentHashMap) map,writeFile);
        new Thread(taskStart).start();
        new Thread(taskStart).start();
        new Thread(taskStart).start();
    }
}
