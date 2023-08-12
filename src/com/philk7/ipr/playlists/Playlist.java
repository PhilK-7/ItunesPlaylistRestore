package com.philk7.ipr.playlists;

import com.philk7.ipr.tracks.TrackDict;

public class Playlist {

    // models a playlist, which contains several tracks

    public int getId() {
        return id;
    }

    private int id;
    private String name;
    private TrackDict playlistTracks;
}
