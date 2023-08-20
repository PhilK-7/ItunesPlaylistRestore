package com.philk7.ipr.reader;

import com.philk7.ipr.playlists.Playlist;
import com.philk7.ipr.playlists.PlaylistDict;
import com.philk7.ipr.tracks.TrackDict;
import com.philk7.ipr.tracks.TrackInfo;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Arrays;
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
        // TODO build construct that models entire track dict: all keys, respective value types, counts, paths
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
        // position of Album varies
        Node albumNode;
        Node aaCheckNode = (Node) reader.getDocElementsAtXpath(trackXpath + "/key[26]");
        Node compCheckNode = (Node) reader.getDocElementsAtXpath(trackXpath + "/key[29]");
        if (aaCheckNode.getTextContent().equals("Album Artist") ||
                compCheckNode != null && compCheckNode.getTextContent().equals("Composer"))
            albumNode = (Node) reader.getDocElementsAtXpath(trackXpath + "/string[7]");
        else
            albumNode = (Node) reader.getDocElementsAtXpath(trackXpath + "/string[6]");
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
        for (int idx = 1; (t = getTrackFromIndex(reader, idx)) != null; idx++)
            tracksList.add(t);

        // construct track mapping
        TrackInfo[] tracks = tracksList.toArray(new TrackInfo[0]);
        TrackDict mapping = new TrackDict(tracks);

        return mapping;
    }


    /**
     * Retrieves a user-made playlist, if exists, at a specific index.
     *
     * @param reader an XMLReader object (initialized on an XML document)
     * @param idx    an index (starting at 1) in the array of playlists
     * @return a playlist object, if a user-made playlist exists at the given index, or null
     */
    public Playlist getPlaylistFromIndex(XMLReader reader, int idx) {
        // check root node of playlist dict
        String playlistXpath = "/plist/dict/array/dict[" + idx + "]";
        Node plKey1 = (Node) reader.getDocElementsAtXpath(playlistXpath + "/key[1]");
        Node plKey4 = (Node) reader.getDocElementsAtXpath(playlistXpath + "/key[4]");
        // nothing at all at this playlist index -> end
        if (plKey1 == null || plKey4 == null) {
            System.out.println("The end of the playlists array was reached.");
            return null;
        }
        // user-made playlists have ID at element #1 and name at element #4
        if (!(plKey1.getTextContent().equals("Playlist ID") && plKey4.getTextContent().equals("Name"))) {
            // only extract user-made playlists
            System.out.println("Skipping irrelevant system playlist.");
            return null;
        }

        // extract relevant playlist info
        Node idNode = (Node) reader.getDocElementsAtXpath(playlistXpath + "/integer[1]");
        int id = Integer.parseInt(idNode.getTextContent());
        Node nameNode = (Node) reader.getDocElementsAtXpath(playlistXpath + "/string[2]");
        String name = nameNode.getTextContent();
        // extract all track (reference) IDs
        Node trackRefNode;
        List<Integer> trackRefIds = new LinkedList<>();
        for (int tidx = 1; (trackRefNode = (Node) reader.getDocElementsAtXpath(
                playlistXpath + "/array/dict[" + tidx + "]/integer")) != null; tidx++) {
            int trackId = Integer.parseInt(trackRefNode.getTextContent());
            trackRefIds.add(trackId);
        }

        // construct track dictionary
        TrackDict tracksMapping = getAllTracks(reader);
        // TODO call getAllTracks one time globally, and then pick the playlist tracks using trackRefIds

        return new Playlist(id, name, tracksMapping);
    }

    /**
     * Retrieves every user-made playlist, and returns a dictionary of those.
     *
     * @param reader an XMLReader object (initialized on an XML document)
     * @return a playlist dictionary that maps playlists´ IDs and their respective playlist objects
     */
    public PlaylistDict getAllPlaylists(XMLReader reader) {
        List<Playlist> playlists = new LinkedList<>();
        int plIdx = 1;

        // skip system playlists
        Playlist currentPlaylist = null;
        while (currentPlaylist == null)
            currentPlaylist = getPlaylistFromIndex(reader, plIdx++);

        // points to first user-made playlist
        System.out.println("The first user-made playlist was found at index " + plIdx + ".");

        // retrieve every user-made playlist
        while (currentPlaylist != null) {
            playlists.add(currentPlaylist);  // start at first user-made playlist
            currentPlaylist = getPlaylistFromIndex(reader, ++plIdx);
        }

        return new PlaylistDict(playlists.toArray(new Playlist[0]));
    }
}
