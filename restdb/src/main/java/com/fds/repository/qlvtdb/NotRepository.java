package com.fds.repository.qlvtdb;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotRepository extends MongoRepository<Not, String> {
	@Query("{ 'gio_bendi' : '?0', 'gio_benden': '?1' }")
	Not findByGio(Long gio_bendi, Long gio_benden);
	
	@Query("{ 'tuyen._source.shortName' : '?0' }")
	List<Not> findByTuyen(String maTuyen);
}
