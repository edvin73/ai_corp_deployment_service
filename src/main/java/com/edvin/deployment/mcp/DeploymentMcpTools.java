package com.edvin.deployment.mcp;

import org.springframework.stereotype.Component;

import com.edvin.deployment.model.Deployment;
import com.edvin.deployment.model.DeploymentEnvironment;
import com.edvin.deployment.service.DeploymentService;

import java.time.LocalDateTime;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeploymentMcpTools {

    private final DeploymentService deploymentService;
    
    @McpTool(
    	name = "getAllDeployments", 
    	description = "Get all deployments"
    )
    public String getAllDeployments() {
		StringBuilder sb = new StringBuilder();
		for (Deployment deployment : deploymentService.getAllDeployments()) {
			sb.append("ID: ").append(deployment.getId())
			  .append(", Service: ").append(deployment.getServiceName())
			  .append(", Environment: ").append(deployment.getEnvironment())
			  .append(", Owner: ").append(deployment.getOwner())
			  .append(", Scheduled Time: ").append(deployment.getScheduledTime())
			  .append(", Status: ").append(deployment.getStatus())
			  .append("\n");
		}
		return sb.toString();
	}
    
    @McpTool(name = "getDeploymentById", description = "Get deployment details by ID")
    public String getDeploymentById(
    		@McpToolParam(description = "The deployment ID") Long id) {
		
    	String dep = deploymentService.getDeploymentById(id).toString();
    	
    	return "Deployment details for ID: " + id + "\nName: " + dep;
	}
    
    
    @McpTool(
    		name = "createDeployment", 
    		description = "Create a new deployment"
    )
    public String createDeployment(
			@McpToolParam(description = "The service name") String serviceName,
			@McpToolParam(description = "The deployment environment") String environment,
			@McpToolParam(description = "The scheduled time in ISO-8601 format (yyyy-MM-ddTHH:mm:ss)") String scheduledTime,
			@McpToolParam(description = "The owner of the deployment") String owner) {
		
		deploymentService.createDeployment(serviceName, 
				DeploymentEnvironment.valueOf(environment.toUpperCase()), 
				java.time.LocalDateTime.parse(scheduledTime), 
				owner);
		
		return "Deployment created for service: " + serviceName + " in environment: " + environment;
    }
    
    @McpTool(
			name = "assignOwner", 
			description = "Assign a new owner to an existing deployment"
	)
    public String assignOwner(
			@McpToolParam(description = "The deployment ID") Long deploymentId,
			@McpToolParam(description = "The new owner of the deployment") String owner) {
    	
    	deploymentService.assignOwner(deploymentId, owner);
		
		return "Owner assigned for deployment ID: " + deploymentId;
    }
    
    
    @McpTool(
		name = "rescheduleDeployment",
		description = "Reschedule an existing deployment to a new time"
	)
    public String rescheduleDeployment(
    					@McpToolParam(description = "The deployment ID") Long deploymentId,
    					@McpToolParam(description = "The new scheduled time in ISO-8601 format (yyyy-MM-ddTHH:mm:ss)") String newScheduledTime) {
    	
    	deploymentService.rescheduleDeployment(deploymentId, LocalDateTime.parse(newScheduledTime));
    	
    	return "Deployment ID: " + deploymentId + " rescheduled to: " + newScheduledTime;
    }
    
    @McpTool(
		name = "cancelDeployment",
		description = "Cancel an existing deployment"
	)
    public String cancelDeployment(
						@McpToolParam(description = "The deployment ID") Long deploymentId) {
    		
    	deploymentService.cancelDeployment(deploymentId); 
    	return "Deployment ID: " + deploymentId + " has been canceled."; 
    }
}
