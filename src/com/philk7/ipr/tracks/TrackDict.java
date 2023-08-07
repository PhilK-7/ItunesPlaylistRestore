package com.philk7.ipr.tracks;

import java.util.Hashtable;

public class TrackDict {

    // models the mapping from track IDs to TrackInfos

    public Hashtable<Integer, TrackInfo> getTrackMapping() {
        return trackMapping;
    }

    private Hashtable<Integer, TrackInfo> trackMapping;

    /**
     * Creates a mapping of track IDs to track infos.
     * @param tracks an array with TrackInfos
     */
    private void createMappingFromTrackInfos(TrackInfo[] tracks) {
       this.trackMapping = new Hashtable<>();  // create new hash-table

        for (TrackInfo t : tracks) {  // put ID as key, entire TrackInfo as value
           int tid = t.getTrackId();
           trackMapping.put(tid, t);
       }
    }
}
