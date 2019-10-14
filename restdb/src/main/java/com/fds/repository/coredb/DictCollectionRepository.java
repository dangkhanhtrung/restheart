package com.fds.repository.coredb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictCollectionRepository extends MongoRepository<DictCollection, String> {
	DictCollection findByShortName(String shortName);
}
