package com.fds.repository.qlvtdb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhuHieuBienHieuRepository extends MongoRepository<PhuHieuBienHieu, String> {
	PhuHieuBienHieu findByShortName(String shortName);
}
