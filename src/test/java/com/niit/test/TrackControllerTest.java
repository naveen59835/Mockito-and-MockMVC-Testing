/*
 * Author : Naveen Kumar
 * Date : 01-02-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.niit.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.niit.domain.Artist;
import com.niit.domain.Track;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.controller.TrackController;
import com.niit.domain.Artist;
import com.niit.exception.TrackAlreadyFound;
import com.niit.exception.TrackNotFound;
import com.niit.service.TrackServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

//import javax.sound.midi.Track;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@ExtendWith(MockitoExtension.class)
public class TrackControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Mock
    TrackServiceImpl trackService;

    @InjectMocks
    TrackController trackController;

    Track track1,track2;
    Artist artist1,artist2;
    List<Track> trackList;
    @BeforeEach
    void setup(){
        artist1 = new Artist(2,"rehman");
        track1 = new Track(10103,"Johny",44,artist1);
        artist2 = new Artist(2,"rehman");
        track2 = new Track(13405,"Harry",12,artist2);
        mockMvc= MockMvcBuilders.standaloneSetup(trackController).build();
    }
    @AfterEach
    void tearDown() {
        track1=null;
        track2 = null;
    }

    @Test
    public void testForPost()throws Exception{
        when(trackService.saveTrack(any())).thenReturn(track1);
        mockMvc.perform(post("/api/v1/track")
                        .contentType(MediaType.APPLICATION_JSON).content(objectTojson(track1)))
                .andExpect(status().isOk());
        verify(trackService,times(1)).saveTrack(any());
    }
    @Test
    public void testForPostFail()throws Exception{
        when(trackService.saveTrack(any())).thenThrow(TrackAlreadyFound.class);
        mockMvc.perform(post("/api/v1/track")
                        .contentType(MediaType.APPLICATION_JSON).content(objectTojson(track1)))
                .andExpect(status().isNotFound());
        verify(trackService,times(1)).saveTrack(any());
    }
    @Test
    public void deletetrack() throws Exception {
        when(trackService.deleteTrack(anyInt())).thenReturn(true);
        mockMvc.perform(delete("/api/v1/track/150").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(trackService,times(1)).deleteTrack(anyInt());
    }
    @Test
    public void deletetrackFail() throws Exception {
        when(trackService.deleteTrack(anyInt())).thenThrow(TrackNotFound.class);
        mockMvc.perform(delete("/api/v1/track/150").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
        verify(trackService,times(1)).deleteTrack(anyInt());
    }
    @Test
    public void testForGetByArtist() throws Exception {
        List<Track> tracks = new ArrayList<>();
        tracks.add(track1);
        when(trackService.findByJustin(anyString())).thenReturn(tracks);

        mockMvc.perform(get ("/api/v1/track/Justin").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(trackService,times(1)).findByJustin(anyString());

    }

    @Test
    public void testGetByArtistFailure() throws Exception {
        when(trackService.findByJustin(any())).thenThrow(new RuntimeException());
        mockMvc.perform(get("/api/v1/track/Justin").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
    @Test
    public void testForGetAllCustomers() throws Exception {
        List<Track> tracks = new ArrayList<>();
        tracks.add(track1);
        tracks.add(track2);
        when(trackService.getTrackList()).thenReturn(tracks);

        mockMvc.perform(get("/api/v1/tracks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(trackService, times(1)).getTrackList();
    }
    @Test
    public void testForGetAllCustomersFailure() throws Exception {
        List<Track> tracks2 = new ArrayList<>();
        tracks2.add(track1);
        tracks2.add(track2);
        when(trackService.getTrackList()).thenThrow(TrackNotFound.class);

        mockMvc.perform(get("/api/v1/tracks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andDo(MockMvcResultHandlers.print());
        verify(trackService, times(1)).getTrackList();
    }

    @Test
    public void testGetTracksByRating() throws Exception {
        List<Track> tracks = new ArrayList<>();
        tracks.add(track1);
        when(trackService.findByRating()).thenReturn(tracks);

        mockMvc.perform(get("/api/v1/track4").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(trackService, times(1)).findByRating();
    }
    @Test
    public void testGetTracksByRatingFailure() throws Exception {
        List<Track> tracks = new ArrayList<>();
        tracks.add(track1);
        when(trackService.findByRating()).thenReturn(tracks);

        mockMvc.perform(get("/api/v1/track4").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(trackService, times(1)).findByRating();
    }
    public static String objectTojson(final Track obj)throws JsonProcessingException {
        String result = "";
        try{
            ObjectMapper mapper=new ObjectMapper();
            String jsoncontent=mapper.writeValueAsString(obj);
            result=jsoncontent;
            System.out.println(jsoncontent);

        }catch (JsonProcessingException js){
            System.out.println("exception"+js);
        }
        return result;
    }






}
