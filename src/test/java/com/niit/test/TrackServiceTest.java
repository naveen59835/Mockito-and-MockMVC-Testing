package com.niit.test;/*
 * Author : Naveen Kumar
 * Date : 31-01-2023
 * Created With : IntelliJ IDEA Community Edition
 */




import com.niit.domain.Artist;
import com.niit.domain.Track;
import com.niit.exception.TrackAlreadyFound;
import com.niit.exception.TrackNotFound;
import com.niit.repo.TrackRepo;
import com.niit.service.TrackServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class TrackServiceTest {
    @Mock
    TrackRepo trackRepo;
    @InjectMocks
    TrackServiceImpl trackService;
    private Track track1, track2;
    List<Track> trackList;
    Artist artist1,artist2;


    @BeforeEach
    void setUp() {
        artist1 = new Artist(2,"rehman");
        track1 = new Track(1008,"Johny",44,artist1);
        artist2 = new Artist(2,"rehman");
        track2 = new Track(1005,"Harry",12,artist2);
        trackList = Arrays.asList(track1,track2);
    }

    @AfterEach
    void tearDown() {
        track1=null;
       track2 = null;
    }
    @Test
    public void givenCustomerToSaveReturnSavedCustomerSuccess() throws TrackAlreadyFound {

        when(trackRepo.save(any())).thenReturn(track1);
        assertEquals(track1,trackService.saveTrack(track1));
        verify(trackRepo,times(1)).save(any());
        verify(trackRepo,times(1)).findById(any());
    }

    @Test
    public void givenDuplicateTrackToSave_whenSaveTrack_thenThrowExceptionFailure() throws TrackAlreadyFound {
        when(trackRepo.findById(track1.getTrackId())).thenReturn(Optional.of(track1));
        assertThrows(TrackAlreadyFound.class, () -> trackService.saveTrack(track1));
    }
    @Test
    public void givenTrackToDeleteFailure() throws TrackNotFound {
        when(trackRepo.findById(track1.getTrackId())).thenReturn(Optional.ofNullable(null));
        boolean flag = trackService.deleteTrack(track1.getTrackId());
        assertNotEquals(true, flag);
        verify(trackRepo, times(1)).findById(any());
        verify(trackRepo, never()).deleteById(any());
    }
    @Test
    public void givenTrackToDeletedSuccess() throws TrackNotFound {
        when(trackRepo.findById(track1.getTrackId())).thenReturn(Optional.ofNullable(null));
        boolean flag = trackService.deleteTrack(track1.getTrackId());
        assertEquals(false,flag);
        verify(trackRepo,times(1)).findById(any());
        verify(trackRepo,never()).deleteById(any());
    }

    @Test
    public void givenTracksShouldReturnAllTracks() throws TrackAlreadyFound, TrackNotFound {
        when(trackRepo.findAll()).thenReturn(Arrays.asList(track1, track2));
        List<Track> allTracks = trackService.getTrackList();
        assertEquals(2, allTracks.size());
        verify(trackRepo, times(2)).findAll();
    }
    @Test
    public void givenTracksShouldReturnAllTracksFailure() throws TrackAlreadyFound, TrackNotFound {
        when(trackRepo.findAll()).thenReturn(Arrays.asList(track1, track2));
        List<Track> allTracks = trackService.getTrackList();
        assertNotEquals(0, allTracks.size());
        verify(trackRepo, times(2)).findAll();
    }
    @Test
    public void givenArtistShouldReturnTrackByArtist() throws TrackNotFound {
        when(trackRepo.findByJustin(track1.getTrackArtist().getArtistName())).thenReturn(Arrays.asList(track1));
        List<Track> tracksByArtist = trackService.findByJustin(track1.getTrackArtist().getArtistName());
        assertEquals(1, tracksByArtist.size());
        verify(trackRepo, times(2)).findByJustin(any());
    }

    @Test
    public void givenArtistShouldReturnTrackByArtistFailure() throws TrackNotFound {
        when(trackRepo.findByJustin(track1.getTrackArtist().getArtistName())).thenReturn(Arrays.asList(track1));
        List<Track> tracksByArtist = trackService.findByJustin(track1.getTrackArtist().getArtistName());
        assertNotEquals(0, tracksByArtist.size());
        verify(trackRepo, times(2)).findByJustin(any());
    }









}
