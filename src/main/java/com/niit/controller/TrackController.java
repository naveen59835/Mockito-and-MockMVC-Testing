/*
 * Author : Naveen Kumar
 * Date : 30-01-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.niit.controller;

import com.niit.domain.Track;
import com.niit.exception.TrackAlreadyFound;
import com.niit.exception.TrackNotFound;
import com.niit.repo.TrackRepo;
import com.niit.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class TrackController {
    ResponseEntity responseEntity;
    TrackService trackService;
    @Autowired
    public TrackController (TrackService trackService){
        this.trackService = trackService;
    }
    @GetMapping("/demo")
     public ResponseEntity <String> get() {
        return new ResponseEntity<String>("Sample", HttpStatus.OK);
    }
    @PostMapping("/track")
    public ResponseEntity<?> saveTrackss(@RequestBody Track track) throws TrackAlreadyFound {
        try {
           Track track1= trackService.saveTrack(track);
            return new ResponseEntity<Track>(track1, HttpStatus.OK);

        } catch(TrackAlreadyFound e){
            throw new TrackAlreadyFound("NotFound");
        }
        catch (Exception e) {
            System.out.println("exception arised");
            return new ResponseEntity<String>("Error Occured", HttpStatus.INTERNAL_SERVER_ERROR);
        }
//        return responseEntity;
    }






    @DeleteMapping("/track/{trackId}")
    public ResponseEntity<?> deleteTrack(@PathVariable ("trackId") int trackId) throws TrackNotFound{
        try {
            trackService.deleteTrack(trackId);
            responseEntity=new ResponseEntity<String>("Successfully Deleted",HttpStatus.OK);
        }catch (TrackNotFound e) {
            throw new TrackNotFound();
        }

        catch (Exception e){
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @GetMapping("/tracks")
    public ResponseEntity<?> getAllTracks() throws TrackNotFound{
        try{
            responseEntity=new ResponseEntity(trackService.getTrackList(),HttpStatus.OK);
        }
        catch (Exception e) {
            responseEntity=new ResponseEntity (e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @GetMapping("/track/{artistName}")
    public ResponseEntity<?> getTrackByArtist(@PathVariable String artistName) throws TrackNotFound{
        try {
            responseEntity=new ResponseEntity(trackService.findByJustin(artistName),HttpStatus.OK);
        }catch(TrackNotFound exception){
            throw new TrackNotFound();
        }

        catch (Exception e) {
            responseEntity=new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @GetMapping("/track4")
    public ResponseEntity<?> getrack()throws TrackNotFound{
        try{
            responseEntity=new ResponseEntity<>(trackService.findByRating(),HttpStatus.OK);

        }catch (Exception e){
            responseEntity=new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


    }
