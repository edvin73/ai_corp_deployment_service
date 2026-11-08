package com.edvin.deployment.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.edvin.deployment.controller.DeploymentController;
import com.edvin.deployment.model.Deployment;
import com.edvin.deployment.model.DeploymentEnvironment;
import com.edvin.deployment.model.DeploymentStatus;
import com.edvin.deployment.repository.DeploymentRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class DeploymentService {
	
	private final DeploymentRepository deploymentRepository;
	
	public List<Deployment> getAllDeployments() {
		return deploymentRepository.findAll();
	}
	
	public Deployment getDeploymentById(Long id) {
		return deploymentRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Deployment not found with id: " + id));
	}
	
	public Deployment createDeployment(String serviceName, 
										DeploymentEnvironment environment, 
										LocalDateTime scheduledTime,
										String owner) {
		
		Deployment deployment = Deployment.builder()
				.serviceName(serviceName)
				.environment(environment)
				.owner(owner)
				.scheduledTime(scheduledTime)
				.status(DeploymentStatus.SCHEDULED)
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.build();
		
		return deploymentRepository.save(deployment);		
	}
	
	public Deployment assignOwner(Long deploymentId, String owner) {
		
		Deployment deployment = getDeploymentById(deploymentId);
		deployment.setOwner(owner);
		deployment.setUpdatedAt(LocalDateTime.now());
		
		return deploymentRepository.save(deployment);
	}
	
	public Deployment rescheduleDeployment(Long deploymentId, LocalDateTime newScheduledTime) {
		
		Deployment deployment = getDeploymentById(deploymentId);
		deployment.setScheduledTime(newScheduledTime);
		deployment.setUpdatedAt(LocalDateTime.now());
		
		return deploymentRepository.save(deployment);
	}
	
	public Deployment cancelDeployment(Long deploymentId) {
		
		Deployment deployment = getDeploymentById(deploymentId);
		deployment.setStatus(DeploymentStatus.CANCELED);
		deployment.setUpdatedAt(LocalDateTime.now());
		
		return deploymentRepository.save(deployment);
	}
}
