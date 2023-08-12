package com.philk7.ipr.reader;

import com.philk7.ipr.tracks.TrackDict;
import com.philk7.ipr.tracks.TrackInfo;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLFetcher {

    // queries certain XPaths in the XML, and constructs the objects to model from the nodes

    /**
     * Obtains the idx-th track listed in the given XML document.
     * @param reader an XMLReader object (initialized on an XML document)
     * @param idx a node index, starting at 1
     * @return a new TrackInfo object
     */
    public TrackInfo getTrackFromIndex(XMLReader reader, int idx) {
        // obtain node list at index (<key>, <integer>, <string> etc.)
        String trackXpath = "/plist/dict/dict/dict[" + idx + "]";
        NodeList trackNodes = reader.getElementsAtXpath(trackXpath);
        assert trackNodes.getLength() == 88;

        // extract values via node indices
        int id = Integer.parseInt(trackNodes.item(2).getTextContent());
        short num = Short.parseShort(trackNodes.item(17).getTextContent());
        short tracks = Short.parseShort(trackNodes.item(20).getTextContent());
        short year = Short.parseShort(trackNodes.item(23).getTextContent());
        String name = trackNodes.item(65).getTextContent();
        String artist = trackNodes.item(68).getTextContent();
        String album = trackNodes.item(74).getTextContent();

        return new TrackInfo(id, num, tracks, year, name, artist, album);
    }
}
