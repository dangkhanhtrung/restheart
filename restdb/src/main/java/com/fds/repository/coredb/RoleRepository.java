package com.fds.repository.coredb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
	Role findByShortName(String shortName);
}
