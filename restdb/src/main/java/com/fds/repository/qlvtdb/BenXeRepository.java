package com.fds.repository.qlvtdb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fds.repository.qlvtdb.BenXe;

@Repository
public interface BenXeRepository extends MongoRepository<BenXe, String> {
	// BenXe findByTen_bx(String ten_bx);
	BenXe findByShortName(String shortName);
}
