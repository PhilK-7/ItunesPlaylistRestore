package com.philk7.ipr.reader;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;

public class XMLReader {

    public Document getXmlDoc() {
        return xmlDoc;
    }

    private Document xmlDoc;
    private XPath xpath;

    /**
     * Builds a (XML) document from a given XML file path.
     * @param path the file path to the XML file
     * @throws ParserConfigurationException if something goes wrong with getting the DocumentBuilder
     * @throws IOException in case the file cannot be opened successfully
     * @throws SAXException the file cannot be parsed successfully
     */
    private void initReader(String path) throws ParserConfigurationException, IOException, SAXException {
        // build document
        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = fac.newDocumentBuilder();
        assert path.endsWith(".xml");
        this.xmlDoc = builder.parse(path);

        // prepare XPath parsing
        XPathFactory xpFac = XPathFactory.newInstance();
        this.xpath = xpFac.newXPath();
    }
}
