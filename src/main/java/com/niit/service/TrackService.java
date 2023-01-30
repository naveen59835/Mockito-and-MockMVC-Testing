package com.niit.service;

import com.niit.domain.Track;
import com.niit.exception.TrackAlreadyFound;
import com.niit.exception.TrackNotFound;

import java.util.List;

public interface TrackService {
    Track saveTrack(Track track) throws TrackAlreadyFound;
    boolean deleteTrack(int trackId) throws TrackNotFound;
    List<Track> getTrackList() throws TrackNotFound;
    List<Track> findByJustin(String artistName) throws TrackNotFound;

    List <Track> findByRating();
}
