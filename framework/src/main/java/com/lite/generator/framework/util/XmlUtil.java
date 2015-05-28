package com.lite.generator.framework.util;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;

public class XmlUtil {

    public XmlUtil(){

    }

    public static Document read(String readFile){
        Document document;
        try {
            document = new SAXReader().read(new BufferedInputStream(new FileInputStream(readFile)));
        }
        catch (Exception exception) {
            throw new RuntimeException("读取XML失败:" + readFile, exception);
        }
        return document;
    }

    public static void write(Document document, String writeFile){
        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            XMLWriter writer = new XMLWriter(format);
            writer.setOutputStream(new BufferedOutputStream(new FileOutputStream(writeFile)));
            writer.write(document);
            writer.close();
        }
        catch (IOException exception) {
            throw new RuntimeException("写入XML失败:" + writeFile, exception);
        }
    }

}
