package com.fds.repository.qlvtdb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fds.repository.qlvtdb.Tuyen;

@Repository
public interface TuyenRepository extends MongoRepository<Tuyen, String>  {
    Tuyen findByShortName(String shortName);
}
