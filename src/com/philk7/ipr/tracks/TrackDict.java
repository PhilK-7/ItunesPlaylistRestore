package com.philk7.ipr.tracks;

import java.util.Collection;
import java.util.Hashtable;

public class TrackDict {

    // models the mapping from track IDs to TrackInfos

    public Hashtable<Integer, TrackInfo> getTrackMapping() {
        return trackMapping;
    }

    private Hashtable<Integer, TrackInfo> trackMapping;

    public TrackDict(TrackInfo[] tracks) {
        this.createMappingFromTrackInfos(tracks);
    }

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

    /**
     * Removes all tracks from this track dict except those given by a list of their IDs.
     * @param trackRefIds a collection with track IDs
     * @return this class instance, updated
     */
    public TrackDict filterTrackDictWithIds(Collection<Integer> trackRefIds) {
        // construct new white-list track mapping
        Hashtable<Integer, TrackInfo> newTrackMapping = new Hashtable<>();
        for(Integer trackId: trackRefIds)
            newTrackMapping.put(trackId, this.trackMapping.get(trackId));

        // swap old for new filtered track mapping
        this.trackMapping = newTrackMapping;

        return this;
    }
}
