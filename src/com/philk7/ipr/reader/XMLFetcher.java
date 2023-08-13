package com.philk7.ipr.reader;

import com.philk7.ipr.tracks.TrackDict;
import com.philk7.ipr.tracks.TrackInfo;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.LinkedList;
import java.util.List;

public class XMLFetcher {

    // queries certain XPaths in the XML, and constructs the objects to model from the nodes

    /**
     * Obtains the idx-th track listed in the given XML document.
     *
     * @param reader an XMLReader object (initialized on an XML document)
     * @param idx    a node index, starting at 1
     * @return a new TrackInfo object
     */
    private TrackInfo getTrackFromIndex(XMLReader reader, int idx) {
        // obtain node list at index (<key>, <integer>, <string> etc.)
        String trackXpath = "/plist/dict/dict/dict[" + idx + "]";
        NodeList trackNodes = (NodeList) reader.getDocElementsAtXpath(trackXpath);
        if (trackNodes == null)
            return null;  // nothing at this index (and at greater indices)
        assert trackNodes.getLength() == 88;

        // extract values via node indices
        // TODO track <dict> elements are sadly inconsistent, reimplement using further xpaths instead of indices
        Node idNode = (Node) reader.getDocElementsAtXpath(trackXpath + "/integer[1]");
        int id = Integer.parseInt(idNode.getTextContent());
        Node numNode = (Node) reader.getDocElementsAtXpath(trackXpath + "/integer[6]");
        short num = Short.parseShort(numNode.getTextContent());
        Node tracksNode = (Node) reader.getDocElementsAtXpath(trackXpath + "/integer[7]");
        short tracks = Short.parseShort(tracksNode.getTextContent());
        Node yearNode = (Node) reader.getDocElementsAtXpath(trackXpath + "/integer[8]");
        short year = Short.parseShort(yearNode.getTextContent());
        Node nameNode = (Node) reader.getDocElementsAtXpath(trackXpath + "/string[3]");
        String name = nameNode.getTextContent();
        Node artistNode = (Node) reader.getDocElementsAtXpath(trackXpath + "/string[4]");
        String artist = artistNode.getTextContent();
        Node albumNode = (Node) reader.getDocElementsAtXpath(trackXpath + "/string[7]");
        String album = albumNode.getTextContent();

        return new TrackInfo(id, num, tracks, year, name, artist, album);
    }

    /**
     * Obtains all tracks in the given XML document.
     *
     * @param reader an XMLReader object (initialized on an XML document)
     * @return a TrackDict that contains all tracks listed
     */
    public TrackDict getAllTracks(XMLReader reader) {
        List<TrackInfo> tracksList = new LinkedList<>();

        // fetch all tracks until done
        TrackInfo t;
        for(int idx = 1; (t = getTrackFromIndex(reader, idx)) != null; idx++)
            tracksList.add(t);

        // construct track mapping
        TrackInfo[] tracks = tracksList.toArray(new TrackInfo[0]);
        TrackDict mapping = new TrackDict(tracks);

        return mapping;
    }
}
