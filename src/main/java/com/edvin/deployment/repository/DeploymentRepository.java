package com.edvin.deployment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edvin.deployment.model.Deployment;


public interface DeploymentRepository extends JpaRepository<Deployment, Long> {

}
