package com.powerreviews.project.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BlockedUserRepository extends CrudRepository<BlockedUserEntity, Integer> {

}
