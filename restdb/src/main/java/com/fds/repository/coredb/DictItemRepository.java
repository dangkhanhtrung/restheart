package com.fds.repository.coredb;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DictItemRepository extends MongoRepository<DictItem, String> {
	DictItem findByShortName(String shortName);
	
	@Query("{ 'shortName' : '?0', 'dictCollection._source.shortName': '?1' }")
	DictItem findByShortNameAndCollection(String shortName, String collectionCode);
	
	@Query("{'shortName': { $regex: ?0, $options: 'i' }, 'dictCollection._source.shortName': '?1' })")
	List<DictItem> findLikeShortName(String shortName, String collectionCode);

	@Query("{'title': { $regex: ?0, $options: 'i' }, 'dictCollection._source.shortName': '?1' })")
	List<DictItem> findLikeTitle(String title, String collectionCode);
	
	@Query("{'title': '?0', 'dictCollection._source.shortName': '?1' })")
	List<DictItem> findByTitle(String title, String collectionCode);	
}
