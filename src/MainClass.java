import com.philk7.ipr.reader.XMLReader;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class MainClass {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        XMLReader reader = new XMLReader();

        reader.initReader("resources/itml.xml");
        NodeList nodes = reader.getElementsAtXpath("/plist/dict/dict");
        System.out.println(nodes.getLength());

    }
}
