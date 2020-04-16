package com.gusrubin.springbootredisjwt.model.globalsettings;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GlobalSettingsService {

	private final GlobalSettingsRepository globalSettingsRepository;
	@Value("${spring-boot-redis-jwt.default-jwt-secret}")
	private String defaultJwtSecret;
	@Value("${spring-boot-redis-jwt.default-jwt-expiration-seconds}")
	private Long defaultJwtExpirationSeconds;

	@Autowired
	public GlobalSettingsService(GlobalSettingsRepository globalSettingsRepository) {
		this.globalSettingsRepository = globalSettingsRepository;
	}

	public GlobalSettings createDefaultGlobalSettings() {
		return globalSettingsRepository.save(GlobalSettings.builder().id("1").jwtSecret(defaultJwtSecret)
				.jwtExpirationSeconds(defaultJwtExpirationSeconds).build());
	}

	public GlobalSettings updateGlobalSettings(String jwtSecret, Long secondsJwtTokenValidity) {
		GlobalSettings globalSettings = findGlobalSettings();
		// Update jwtSecret if it has changed
		if (jwtSecret != null && !jwtSecret.equals(globalSettings.getJwtSecret())) {
			globalSettings.setJwtSecret(jwtSecret);
		}
		// Update secondsJwtTokenValidity if it has changed
		if (secondsJwtTokenValidity != null
				&& !secondsJwtTokenValidity.equals(globalSettings.getJwtExpirationSeconds())) {
			globalSettings.setJwtExpirationSeconds(secondsJwtTokenValidity);
		}
		return globalSettingsRepository.save(globalSettings);
	}

	public GlobalSettings findGlobalSettings() {
		Optional<GlobalSettings> globalSettingsResult = globalSettingsRepository.findById("1");
		if (globalSettingsResult.isPresent()) {
			return globalSettingsResult.get();
		} else {
			return createDefaultGlobalSettings();
		}

	}

}
