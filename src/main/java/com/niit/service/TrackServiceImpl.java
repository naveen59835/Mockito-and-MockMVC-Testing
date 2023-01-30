/*
 * Author : Naveen Kumar
 * Date : 30-01-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.niit.service;

import com.niit.domain.Track;
import com.niit.exception.TrackAlreadyFound;
import com.niit.exception.TrackNotFound;
import com.niit.repo.TrackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackServiceImpl implements TrackService{

    TrackRepo trackRepo;
    @Autowired
    public TrackServiceImpl(TrackRepo trackRepo){
        this.trackRepo=trackRepo;
    }
    @Override
    public Track saveTrack(Track track) throws TrackAlreadyFound {
        if(trackRepo.findById(track.getTrackId()).isPresent()){
            System.out.println("hello");
            throw new TrackAlreadyFound("Track Already Found");
        }
        return trackRepo.save(track);
    }

    @Override
    public boolean deleteTrack(int trackId) throws TrackNotFound {
        if(trackRepo.findById(trackId).isPresent()){
            Track track=trackRepo.findById(trackId).get();
            trackRepo.delete(track);
            return true;

        }
        return false;
//        boolean flag=false;
//        if(trackRepo.findById(trackId).isEmpty()){
//            throw new TrackNotFound();
//        }else{
//            trackRepo.deleteById(trackId);
//            flag=true;
//        }
//        return flag;

    }

    @Override
    public List<Track> getTrackList() throws TrackNotFound {
        return trackRepo.findAll();
    }

    @Override
    public List<Track> findByJustin(String artistName) throws TrackNotFound {
        if(trackRepo.findByJustin(artistName).isEmpty()){
            throw new TrackNotFound();

        }
        return trackRepo.findByJustin(artistName);
    }

    @Override
    public List<Track> findByRating() {
        return trackRepo.findByRating();
    }
}
