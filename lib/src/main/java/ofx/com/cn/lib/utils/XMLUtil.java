package ofx.com.cn.lib.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by OFX040 on 2018/3/29.
 */

public class XMLUtil {
    public static String getClassName(String path){
        String name=null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document document = dbBuilder.parse(path);
            NodeList nList = document.getElementsByTagName("className");
            for(int i = 0; i < nList.getLength(); i++){
                Element node = (Element) nList.item(i);
                name = node.getNodeName();
                log("NodeName="+name);
                if("className".equals(name)){
                    name = node.getTextContent();

                    log("NodeValue="+name);
                    break;
                }
            }
            return name;
        }catch (Exception e){
            log("Parse xml fail");
            e.printStackTrace();
            return null;
        }



    }

    private static void log(String msg){
        System.out.println(msg);
    }
}































