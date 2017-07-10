package edu.oregonstate.mist.mealcard.health

import com.codahale.metrics.health.HealthCheck
import com.codahale.metrics.health.HealthCheck.Result
import edu.oregonstate.mist.mealcard.db.MealPlanDAO

class MealCardHealthCheck extends HealthCheck {
    private MealPlanDAO mealPlanDAO

    MealCardHealthCheck(MealPlanDAO mealPlanDAO) {
        this.mealPlanDAO = mealPlanDAO
    }

    @Override
    protected Result check() {
        try {
            String status = mealPlanDAO.checkHealth()

            if (status != null) {
                return Result.healthy()
            }
            Result.unhealthy("status: ${status}")
        } catch(Exception e) {
            Result.unhealthy(e.message)
        }
    }
}