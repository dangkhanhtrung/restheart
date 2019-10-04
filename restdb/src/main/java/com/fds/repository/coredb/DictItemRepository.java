package com.fds.repository.coredb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DictItemRepository extends MongoRepository<DictItem, String> {
	DictItem findByShortName(String shortName);
	
	@Query("{ 'shortName' : '?0', 'dictCollection._source.shortName': '?1' }")
	DictItem findByShortNameAndCollection(String shortName, String collectionCode);
}
