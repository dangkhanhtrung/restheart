package com.coredb.qlvt.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.coredb.qlvt.repository.DoanhNghiep;

@Repository
public interface DoanhNghiepRepository extends MongoRepository<DoanhNghiep, String> {
    DoanhNghiep findByShortName(String shortName);
}
