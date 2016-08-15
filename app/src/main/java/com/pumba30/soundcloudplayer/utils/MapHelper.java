package com.pumba30.soundcloudplayer.utils;


import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapHelper {

    private static final String LIMIT = "limit";
    private static final String STATION = "q";
    private static final String LINKED_PARTITIONING = "linked_partitioning";
    private static final String OFFSET = "offset";
    private static final String DEFAULT_LIMIT_PAGES = String.valueOf(20);
    private static final String KEY_SHARING = "sharing";
    private static final String TITLE_PLAYLIST = "title";
    private static final String KEY_PLAYLIST = "playlist";
    private static final String KEY_TRACK_ID = "id";
    private static final String KEY_TRACKS = "tracks";


    public static Map<String, String> createMapRequestUrlNextPage(String nextPageUrlStations) {
        // example -- https://api.soundcloud.com/tracks?q=Station&linked_partitioning=1&limit=10&offset=10
        String stationQuery = Uri.parse(nextPageUrlStations).getQueryParameter(STATION);
        String linkedPartitioning = Uri.parse(nextPageUrlStations).getQueryParameter(LINKED_PARTITIONING);
        String offset = Uri.parse(nextPageUrlStations).getQueryParameter(OFFSET);

        Map<String, String> map = new HashMap<>();
        map.put(STATION, stationQuery);
        map.put(LINKED_PARTITIONING, linkedPartitioning);
        map.put(LIMIT, DEFAULT_LIMIT_PAGES);
        map.put(OFFSET, offset);

        return map;
    }

    public static Map<String, String> getQueryToMap(String key, String value) {
        Map<String, String> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    public static Map<String, Map<String, String>> getMapToCreatePlaylist(String titlePlaylist, String sharing) {
        Map<String, String> map = new HashMap<>();
        map.put(TITLE_PLAYLIST, titlePlaylist);

        //sharing may be public or private
        map.put(KEY_SHARING, sharing);

        Map<String, Map<String, String>> mapPlaylist = new HashMap<>();
        mapPlaylist.put(KEY_PLAYLIST, map);

        return mapPlaylist;
    }

    public static Map<String, Map<String, List<Map<String, String>>>> getMapToAddPlayList(List<String> trackIdList) {

        List<Map<String, String>> listTracksIds = new ArrayList<>();
        for (int i = 0; i < trackIdList.size(); i++) {
            Map<String, String> mapTrackId = new HashMap<>();
            mapTrackId.put(KEY_TRACK_ID, trackIdList.get(i));
            listTracksIds.add(mapTrackId);
        }

        Map<String, List<Map<String, String>>> listMapTracksIds = new HashMap<>();
        listMapTracksIds.put(KEY_TRACKS, listTracksIds);

        Map<String, Map<String, List<Map<String, String>>>> mapPlaylist = new HashMap<>();
        mapPlaylist.put(KEY_PLAYLIST, listMapTracksIds);

        return mapPlaylist;
    }
}
