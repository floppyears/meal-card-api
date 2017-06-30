package edu.oregonstate.mist.mealcard.db
import org.skife.jdbi.v2.sqlobject.SqlQuery

public interface MealPlanDAO {

    @SqlQuery("""

                """)
    public mealPlanByOSUID(int osuid)
}
