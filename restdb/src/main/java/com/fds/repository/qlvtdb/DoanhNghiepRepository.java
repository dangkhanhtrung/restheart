package com.fds.repository.qlvtdb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fds.repository.qlvtdb.DoanhNghiep;

@Repository
public interface DoanhNghiepRepository extends MongoRepository<DoanhNghiep, String> {
    DoanhNghiep findByShortName(String shortName);
}
