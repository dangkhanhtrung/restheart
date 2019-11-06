package com.fds.repository.qlvtdb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.fds.repository.qlvtdb.BenXe;

@Repository
public interface BenXeRepository extends MongoRepository<BenXe, String> {
	// BenXe findByTen_bx(String ten_bx);
	BenXe findByShortName(String shortName);
	
	@Query("{ 'ma_bx' : '?0', 'so_gtvt': '?1' }")
	BenXe findByMaBXAndSo(String ma_bx, String soGtvt);
	
	@Query("{ 'shortName' : '?0', 'storage': '?1' }")
	BenXe findByShortNameAndStorage(String shortName, String storage);
}
