/*
 * Author : Naveen Kumar
 * Date : 31-01-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.niit.test;

import com.niit.domain.Artist;
import com.niit.domain.Track;
import com.niit.repo.TrackRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest

public class CustomerRepositoryTest {

    @Autowired
    private TrackRepo trackRepo;
    private Artist artist;
    private Track track;
    @BeforeEach
    void setUp() {
        artist = new Artist(67,"naveen");
        track = new Track(1004, "Jonny",6, artist);
    }
    @AfterEach
    void tearDown() {
        artist = null;
        track = null;
    }
    @Test
    @DisplayName("Test Case for saving customer Objec")
    public  void testSaveCustomer(){
        System.out.println("Inside the save customer method");
        trackRepo.save(track);
        Track track1=trackRepo.findById(track.getTrackId()).get();
        assertEquals(track1.getTrackId(),track.getTrackId());
    }
    @Test
    @DisplayName("Test Case for saving customer Objec")
    public  void testSaveCustomerFailed(){
        System.out.println("Inside the save customer method");
        trackRepo.save(track);
        Track track1=trackRepo.findById(track.getTrackId()).get();
        assertNotEquals(10, track1.getTrackId());
    }
    @Test
    @DisplayName("This is test for deleting customer")
    public void getAllCustomer(){
        System.out.println("Inside the get all customer method");
        trackRepo.insert(track);
        Track track2 = trackRepo.findById(track.getTrackId()).get();
        trackRepo.delete(track);
        assertEquals(Optional.empty(),trackRepo.findById(track.getTrackId()));

    }

    @Test
    @DisplayName("This is test for failing scenario of deleting customer")
    public void getAllCustomerFailure() {
        System.out.println("Inside the get all customer method");
        trackRepo.insert(track);
        Track customer1 = trackRepo.findById(track.getTrackId()).get();
        trackRepo.delete(track);
        assertNotEquals(Optional.of(customer1), trackRepo.findById(track.getTrackId()));  //Expecting customer object to still be present

    }


    @Test
    @DisplayName("Test case for retrieving all customer objects")
    public void returnAllCustomerObjects(){
        trackRepo.insert(track);
        Artist product1=new Artist(2,"dfdfd");
        Track customer2=new Track(14,"dfdf",34,product1);
        trackRepo.insert(customer2);
        List<Track> list1 = trackRepo.findAll();
        assertEquals(49, list1.size());
        assertNotEquals(19,list1.size());
        assertEquals("ABdfdfC", list1.get(1).getTrackName());
        assertNotEquals("Naveena", list1.get(1).getTrackName());
    }

    @Test
    @DisplayName("test case for retrieving the customers by brand name")
    public void returnAllCustomerObjectsByBrand() {
        trackRepo.insert(track);
        Artist product3 = new Artist(3, "dfdfd");
        Track customer3 = new Track(15, "dfdf", 4, product3);
        trackRepo.insert(customer3);
        List<Track> customers=trackRepo.findByJustin(track.getTrackArtist().getArtistName());
        assertEquals(12,customers.size());
        assertEquals(track.getTrackArtist().getArtistName(),customers.get(0).getTrackArtist().getArtistName());


    }
    @Test
    @DisplayName("test case for retrieving the customers by brand name")
    public void returnAllCustomerObjectsByBrandFail() {
        trackRepo.insert(track);
        Artist product3 = new Artist(3, "dfdfd");
        Track customer3 = new Track(15, "dfdf", 4, product3);
        trackRepo.insert(customer3);
        List<Track> customers=trackRepo.findByJustin(track.getTrackArtist().getArtistName());
        assertNotEquals(20, customers.size());  //Expecting different size of list than actual value
        assertNotEquals("apple", customers.get(0).getTrackArtist().getArtistName());  //Expecting different product name than actual value



    }









}
