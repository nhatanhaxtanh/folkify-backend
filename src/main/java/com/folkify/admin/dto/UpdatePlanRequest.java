package com.folkify.admin.dto;

import com.folkify.auth.entity.Plan;
import jakarta.validation.constraints.NotNull;

public record UpdatePlanRequest(
        @NotNull(message = "Plan không được để trống")
        Plan plan
) {}
