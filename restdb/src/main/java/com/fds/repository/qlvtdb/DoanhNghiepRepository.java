package com.fds.repository.qlvtdb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.fds.repository.qlvtdb.DoanhNghiep;

@Repository
public interface DoanhNghiepRepository extends MongoRepository<DoanhNghiep, String> {
    DoanhNghiep findByShortName(String shortName);
    
    @Query("{'ma_dn': '?0'}")
    DoanhNghiep findByMa_dn(String maDN);
}
