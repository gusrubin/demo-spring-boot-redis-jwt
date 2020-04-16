package com.gusrubin.springbootredisjwt.model.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemUserRepository extends CrudRepository<SystemUser, String> {

	SystemUser findByUsername(String username);

}
