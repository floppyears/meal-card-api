package edu.oregonstate.mist.mealcard.db

import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.SqlQuery

public interface MealPlanDAO extends Closeable {

    @SqlQuery("SELECT 1 FROM dual")
    Integer checkHealth()

    @SqlQuery("""
                SELECT *
                FROM mealplans
                WHERE OSU_ID = :osuid
                """)
    public mealPlanByOSUID(@Bind("osuid") String osuid)

    @Override
    void close()
}
