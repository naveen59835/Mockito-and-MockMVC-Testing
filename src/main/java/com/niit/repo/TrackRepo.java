package com.niit.repo;

import com.niit.domain.Track;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepo extends MongoRepository<Track,Integer> {
    @Query("{'trackArtist.artistName':{$in:[?0]}}")
    List<Track> findByJustin (String artistName);

    @Query("{'trackRating':{$gt:4}}")
    List<Track> findByRating ();

}
