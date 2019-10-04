package com.fds.repository.qlvtdb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fds.repository.qlvtdb.PhuongTien;

@Repository
public interface PhuongTienRepository extends MongoRepository<PhuongTien, String> {
    PhuongTien findByShortName(String shortName);
}
