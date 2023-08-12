package com.philk7.ipr.reader;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;

public class XMLReader {

    private Document xmlDoc;
    private XPath xpath;

    /**
     * Builds a (XML) document from a given XML file path.
     * @param path the file path to the XML file
     * @throws ParserConfigurationException if something goes wrong with getting the DocumentBuilder
     * @throws IOException in case the file cannot be opened successfully
     * @throws SAXException the file cannot be parsed successfully
     */
    public void initReader(String path) throws ParserConfigurationException, IOException, SAXException {
        // build document
        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = fac.newDocumentBuilder();
        assert path.endsWith(".xml");
        this.xmlDoc = builder.parse(path);

        // prepare XPath parsing
        XPathFactory xpFac = XPathFactory.newInstance();
        this.xpath = xpFac.newXPath();
    }

    /**
     *
     * @param xpathStr
     * @return
     */
    public NodeList getElementsAtXpath(String xpathStr) {

        // make sure that needed objects are initialized
        if(this.xmlDoc == null || this.xpath == null) {
            System.out.println("Initialize the document an XPath parser first.");
            return null;
        }

        // read objects at XPath as NodeList
        try {
            XPathExpression exp = this.xpath.compile(xpathStr);
            return (NodeList) exp.evaluate(this.xmlDoc, XPathConstants.NODE);
        }
        catch (XPathExpressionException xpee) {
            System.err.println(xpee.getMessage());
            return null;
        }

    }
}
