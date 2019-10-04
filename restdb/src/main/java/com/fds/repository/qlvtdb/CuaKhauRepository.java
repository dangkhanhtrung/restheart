package com.fds.repository.qlvtdb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fds.repository.qlvtdb.CuaKhau;

@Repository
public interface CuaKhauRepository extends MongoRepository<CuaKhau, String> {
    CuaKhau findByShortName(String shortName);
}
