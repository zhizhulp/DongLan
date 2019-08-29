package com.cn.danceland.myapplication.utils.multipartrequest;

/**
 * Created by shy on 2017/11/8 10:40
 * Email:644563767@qq.com
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * Created by ${新根} on 2016/11/6.
 * 博客：http://blog.csdn.net/hexingen
 * <p/>
 * 用途：
 * 使用httpUrlConnection上传文件到服务器
 */
public class HttpUrlConnectionOpts {

    /**
     * 字符编码格式
     */
    private static final String PROTOCOL_CHARSET = "utf-8";
    private static final String BOUNDARY = "----------" + System.currentTimeMillis();
    /**
     * 请求的内容类型
     */
    private static final String PROTOCOL_CONTENT_TYPE = "multipart/form-data; boundary=" + BOUNDARY;

    /**
     * 多个文件间的间隔
     */
    private static final String FILEINTERVAL = "\r\n";

    public HttpUrlConnectionOpts() {

    }
    public void fileUpLoad(String url, Map<String, File> files) {

        HttpURLConnection connection = createMultiPartConnection(url);
        addIfParameter(connection, files);
        String responeContent = getResponeFromService(connection);
    }
    /**
     * 获取从服务器相应的数据
     *
     * @param connection
     * @return
     */
    public String getResponeFromService(HttpURLConnection connection) {
        String responeContent = null;
        BufferedReader bufferedReader = null;
        try {
            if (connection != null) {
                connection.connect();
                int responeCode = connection.getResponseCode();
                if (responeCode == 200) {
                    bufferedReader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuffer.append(line);
                    }
                    responeContent = stringBuffer.toString();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            responeContent = null;
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return responeContent;
    }

    /**
     * 若是文件列表不为空，则将文件列表上传。
     *
     * @param connection
     * @param files
     */
    public void addIfParameter(HttpURLConnection connection, Map<String, File> files) {
        if (files != null && connection != null) {
            DataOutputStream dataOutputStream = null;
            try {
                dataOutputStream = new DataOutputStream(connection.getOutputStream());
                int i = 1;
                Set<Map.Entry<String, File>> set = files.entrySet();
                for (Map.Entry<String, File> fileEntry : set) {
                    byte[] contentHeader = getFileHead(fileEntry.getKey());
                    //添加文件的头部格式
                    dataOutputStream.write(contentHeader, 0, contentHeader.length);
                    //添加文件数据
                    readFileData(fileEntry.getValue(), dataOutputStream);
                    //添加文件间的间隔，若是一个文件则不用添加间隔。若是多个文件时，最后一个文件不用添加间隔。
                    if (set.size() > 1 && i < set.size()) {
                        i++;
                        dataOutputStream.write(FILEINTERVAL.getBytes(PROTOCOL_CHARSET));
                    }
                }
                //写入文件的尾部格式
                byte[] contentFoot = getFileFoot();
                dataOutputStream.write(contentFoot, 0, contentFoot.length);
                //刷新数据到流中
                dataOutputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (dataOutputStream != null) {
                        dataOutputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将file数据写入流中
     *
     * @param file
     * @param outputStream
     */
    public void readFileData(File file, OutputStream outputStream) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fileInputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建和设置HttpUrlConnection的内容格式为文件上传格式
     *
     * @param url
     * @return
     */
    public HttpURLConnection createMultiPartConnection(String url) {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            //设置请求方式为post
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            //设置不使用缓存
            httpURLConnection.setUseCaches(false);
            //设置数据字符编码格式
            httpURLConnection.setRequestProperty("Charsert", PROTOCOL_CHARSET);
            //设置内容上传类型（multipart/form-data），这步是关键
            httpURLConnection.setRequestProperty("Content-Type", PROTOCOL_CONTENT_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpURLConnection;
    }


    /**
     * 获取到文件的head
     *
     * @return
     */
    public byte[] getFileHead(String fileName) {
        try {
            StringBuffer buffer = new StringBuffer();
            buffer.append("--");
            buffer.append(BOUNDARY);
            buffer.append("\r\n");
            buffer.append("Content-Disposition: form-data;name=\"media\";filename=\"");
            buffer.append(fileName);
            buffer.append("\"\r\n");
            buffer.append("Content-Type:application/octet-stream\r\n\r\n");
            String s = buffer.toString();
            return s.getBytes("utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取文件的foot
     *
     * @return
     */
    public byte[] getFileFoot() {
        try {
            StringBuffer buffer = new StringBuffer();
            buffer.append("\r\n--");
            buffer.append(BOUNDARY);
            buffer.append("--\r\n");
            String s = buffer.toString();
            return s.getBytes("utf-8");
        } catch (Exception e) {
            return null;
        }
    }
}