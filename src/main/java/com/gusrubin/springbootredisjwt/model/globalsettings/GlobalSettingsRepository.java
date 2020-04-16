package com.gusrubin.springbootredisjwt.model.globalsettings;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalSettingsRepository extends CrudRepository<GlobalSettings, String> {

	Optional<GlobalSettings> findById(String id);
}
