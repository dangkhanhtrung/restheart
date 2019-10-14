package com.coredb.qlvt.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.coredb.qlvt.repository.CuaKhau;

@Repository
public interface CuaKhauRepository extends MongoRepository<CuaKhau, String> {
    CuaKhau findByShortName(String shortName);
}
