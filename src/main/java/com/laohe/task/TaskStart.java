package com.laohe.task;

import com.laohe.util.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Sun Jingchun
 * @version 5.0.0
 * created at 2021/8/10 19:52
 * copyright @2021 北京沐融信息科技股份有限公司
 */
public class TaskStart implements Runnable {

    private ConcurrentHashMap<String, String> map;

    private String writeFile;
    public TaskStart(ConcurrentHashMap map, String writeFile) {
        this.map = map;
        this.writeFile = writeFile;
    }


    @Override
    public synchronized void run() {
        Set<Map.Entry<String, String>> entries = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            getHttp(iterator.next().getKey(),iterator.next().getValue());
            map.remove(iterator.next().getKey());
        }

    }

    public void getHttp(String userName, String password) {
        //创建默认属性的连接
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(CommonStr.URL);
        request.setHeader("User-Agent", CommonStr.USER_AGENT);
        request.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        try {
            request.setEntity(new StringEntity("format=json&username=" + userName + "&password=" + password + "&captcha="));
            //设置响应
            CloseableHttpResponse response = httpClient.execute(request);
            //获取响应信息
            String rspText = EntityUtils.toString(response.getEntity());
            //
            System.out.println(userName+rspText);
            if (!rspText.contains("用户名或密码错误")) {
                FileUtils.writeToFile(writeFile,userName + "----" + password);
            }
        } catch (IOException e) {
            System.out.println("响应超时");
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
