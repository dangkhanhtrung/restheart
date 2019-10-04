package com.coredb.qlvt.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.coredb.qlvt.repository.PhuongTien;

@Repository
public interface PhuongTienRepository extends MongoRepository<PhuongTien, String> {
    PhuongTien findByShortName(String shortName);
}
