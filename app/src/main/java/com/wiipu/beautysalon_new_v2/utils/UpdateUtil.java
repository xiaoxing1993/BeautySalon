package com.wiipu.beautysalon_new_v2.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Ken~Jc on 2016/3/11.
 * 检查更新的工具类
 */
public class UpdateUtil {
    /**
     * @return 是否可更新
     */
    public static boolean isUpdate(Context mContext) {
        int versionCode = getVersionCode(mContext);
        return false;
    }

    /**
     * @param context
     * @return app的版本号
     *
     */
    public static int getVersionCode(Context context) {
        try {
            int versionCode = context.getPackageManager().getPackageInfo(
                    "com.wiipu.beautysalon_new_v2", 0).versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private class checkVersionTask implements Runnable{

        @Override
        public void run() {
        }
    }


    /**
     * @author zy
     * @description 解析服务器上的xml数据
     *
     */
    private class ParseXmlService {
        public HashMap<String, String> parseXml(InputStream inputStream)
                throws ParserConfigurationException, SAXException, IOException {
            HashMap<String, String> map = new HashMap<String, String>();
            // 实例化一个文档构建器工厂
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            // 通过文档构建器工厂获取一个文档构建器
            DocumentBuilder builder = factory.newDocumentBuilder();
            // 通过文档通过文档构建器构建一个文档实例
            Document document = builder.parse(inputStream);
            // 获取XML文件根节点
            Element root = document.getDocumentElement();
            // 获得所有子节点
            NodeList childNodes = root.getChildNodes();

            for (int j = 0; j < childNodes.getLength(); j++) {
                Node childNode = childNodes.item(j);
                if (childNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element childElement = (Element) childNode;
                    // 版本号
                    if ("version".equals(childElement.getNodeName())) {
                        map.put("version", childElement.getFirstChild()
                                .getNodeValue());
                    }
                    // 软件名称
                    else if (("name".equals(childElement.getNodeName()))) {
                        map.put("name", childElement.getFirstChild()
                                .getNodeValue());
                    }
                    // 下载地址
                    else if (("url".equals(childElement.getNodeName()))) {
                        map.put("url", childElement.getFirstChild()
                                .getNodeValue());
                    }
                }
            }
            return map;
        }
    }
}