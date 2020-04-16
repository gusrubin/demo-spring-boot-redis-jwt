package com.gusrubin.springbootredisjwt.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.gusrubin.springbootredisjwt.model.globalsettings.GlobalSettingsService;
import com.gusrubin.springbootredisjwt.model.user.SystemUserService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StartUpTasks implements ApplicationListener<ContextRefreshedEvent> {

	private final GlobalSettingsService globalSettingsService;
	private final SystemUserService systemUserService;

	@Autowired
	public StartUpTasks(GlobalSettingsService globalSettingsService, SystemUserService systemUserService) {
		this.globalSettingsService = globalSettingsService;
		this.systemUserService = systemUserService;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.info("Verifying SpringBootJwt global settings");
		globalSettingsService.findGlobalSettings();
		log.info("Verifying SpringBootJwt admin user");
		systemUserService.checkAdminUser();
	}
}
