package com.anderscore.quarkus.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import jakarta.enterprise.context.ApplicationScoped;

@Readiness
@ApplicationScoped
public class ReadinessChecks implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("customReadiness").up().build();
    }
}
