package com.philk7.ipr.tracks;

public class TrackInfo {

    // models the track information (mapped as dict to a track ID (key) in the XML file)

    public int getTrackId() {
        return trackId;
    }

    private int trackId;  // track ID given by iTunes
    private short trackNum;  // track number on disk
    private short diskTracks;  // how many tracks on given disk
    private short trackYear;  // track release year acc. to iTunes
    private String trackName;  // the name of the track
    private String trackArtist;  // the album´s artist/group
    private String trackAlbum;  // the name of the album

}
