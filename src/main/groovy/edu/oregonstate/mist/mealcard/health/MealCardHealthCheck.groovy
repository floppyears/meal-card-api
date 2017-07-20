package edu.oregonstate.mist.mealcard.health

import com.codahale.metrics.health.HealthCheck
import com.codahale.metrics.health.HealthCheck.Result
import edu.oregonstate.mist.mealcard.db.AbstractMealPlanDAO

class MealCardHealthCheck extends HealthCheck {
    private AbstractMealPlanDAO mealPlanDAO

    MealCardHealthCheck(AbstractMealPlanDAO mealPlanDAO) {
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