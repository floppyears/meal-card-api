package edu.oregonstate.mist.mealcard.db

import edu.oregonstate.mist.mealcard.core.MealPlanBalance
import org.skife.jdbi.v2.sqlobject.Bind

public interface AbstractMealPlanDAO {
    Integer checkHealth()
    public List<MealPlanBalance> mealPlanByOSUID(@Bind("osuid") String osuid)
}
