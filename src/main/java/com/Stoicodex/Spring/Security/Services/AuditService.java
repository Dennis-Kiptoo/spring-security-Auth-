package com.Stoicodex.Spring.Security.Services;

public interface AuditService {
    void logAction (String action, String username);
    void logSecurityIncident(String incidentDescription);
}
