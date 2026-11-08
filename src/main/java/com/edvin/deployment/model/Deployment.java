package com.edvin.deployment.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DEPLOYMENT")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Deployment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "SERVICE_NAME", nullable = false)
	private String serviceName;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ENVIRONMENT", nullable = false)
	private DeploymentEnvironment environment;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "SCHEDULED_TIME", nullable = false)
	private LocalDateTime scheduledTime;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false)
	private DeploymentStatus status;
	
	@Column(name = "OWNER", nullable = false)
	private String owner;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATED_AT", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "UPDATED_AT", nullable = false)
	private LocalDateTime updatedAt;
}
