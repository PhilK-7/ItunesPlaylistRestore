package com.philk7.ipr.playlists;

import java.util.Hashtable;

public class PlaylistDict {

    // models a collection of playlists

    private Hashtable<Integer, Playlist> playlistMapping;

    public PlaylistDict(Playlist[] playlists) {
        this.playlistMapping = new Hashtable<>();

        for(Playlist p: playlists) {
            this.playlistMapping.put(p.getId(), p);
        }
    }
}
