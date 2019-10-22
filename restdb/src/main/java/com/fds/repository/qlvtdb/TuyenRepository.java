package com.fds.repository.qlvtdb;

import java.util.List;

import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.fds.repository.qlvtdb.Tuyen;

@Repository
public interface TuyenRepository extends MongoRepository<Tuyen, String>  {
    Tuyen findByShortName(String shortName);
    
    @Query(value = "?0")
    List<Tuyen> findByQuery(Document queryDoc, Sort sortBy);
}
