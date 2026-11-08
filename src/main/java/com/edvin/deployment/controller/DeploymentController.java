package com.edvin.deployment.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.edvin.deployment.model.Deployment;
import com.edvin.deployment.model.DeploymentEnvironment;
import com.edvin.deployment.service.DeploymentService;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/deployments")
@AllArgsConstructor
public class DeploymentController {
	
	private final DeploymentService deploymentService;
	
	@GetMapping("/")
	public List<Deployment> getAllDeployments() {
		return deploymentService.getAllDeployments();
	}
	
	@GetMapping("/{id}")
	public Deployment getDeploymentById(@PathVariable Long id) {
		return deploymentService.getDeploymentById(id);
	}
	
	@PostMapping
	public Deployment createDeployment(@RequestParam String serviceName, 
										@RequestParam String environment, 
										@RequestParam String scheduledTimeStr,
										@RequestParam String owner) {
		
		LocalDateTime scheduledTime = LocalDateTime.parse(scheduledTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		return deploymentService.createDeployment(serviceName, 
													DeploymentEnvironment.valueOf(environment), 
													scheduledTime, 
													owner);
	}
	
	@PutMapping("/{id}/assign-owner")
	public Deployment assignOwner(@PathVariable Long id, 
									@RequestParam String owner) {
		
		return deploymentService.assignOwner(id, owner);
	}
	
	@PutMapping("/{id}/reschedule")
	public Deployment rescheduleDeployment(@PathVariable Long id, 
											@RequestParam String newTimeStr) {
		
		LocalDateTime newTime = LocalDateTime.parse(newTimeStr.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		return deploymentService.rescheduleDeployment(id, newTime);
	}
	
	@PutMapping("/{id}/cancel")
	public Deployment cancelDeployment(@PathVariable Long id) {
		
		return deploymentService.cancelDeployment(id);
	}
}
