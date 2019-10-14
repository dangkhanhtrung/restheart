package com.coredb.qlvt.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.coredb.qlvt.repository.Tuyen;

@Repository
public interface TuyenRepository extends MongoRepository<Tuyen, String>  {
    Tuyen findByShortName(String shortName);
}
