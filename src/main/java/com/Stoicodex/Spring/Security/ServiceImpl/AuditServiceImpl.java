package com.Stoicodex.Spring.Security.ServiceImpl;

import com.Stoicodex.Spring.Security.Services.AuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuditServiceImpl implements AuditService {
    private static final Logger logger = LoggerFactory.getLogger(AuditServiceImpl.class);

    @Override
    public void logAction(String action, String username) {
        logger.info("User: {} performed action: {}", username, action);
    }
    @Override
    public void logSecurityIncident(String incidentDescription) {
        logger.warn("SECURITY INCIDENT: {}", incidentDescription);
    }
}
