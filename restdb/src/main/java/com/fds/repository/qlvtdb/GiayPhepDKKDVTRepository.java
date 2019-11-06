package com.fds.repository.qlvtdb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GiayPhepDKKDVTRepository extends MongoRepository<GiayPhepDKKDVT, String> {
	GiayPhepDKKDVT findByShortName(String shortName);
}
