package edu.oregonstate.mist.mealcard.db

import edu.oregonstate.mist.mealcard.core.MealPlanBalance
import edu.oregonstate.mist.mealcard.mapper.MealPlanBalanceMapper
import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper

@RegisterMapper(MealPlanBalanceMapper)
public interface MealPlanDAO extends Closeable {

    @SqlQuery("SELECT 1 FROM dual")
    Integer checkHealth()

    @SqlQuery("""
                SELECT  OSU_ID,
                        MEALPLAN_ID,
                        MEALPLAN_DESC,
                        BALANCE,
                        to_char(CAST(sys_extract_utc(CAST(last_used_date AS TIMESTAMP)) AS DATE),
                        'YYYY-MM-DD HH24:MM:SS') as last_used_date,
                        LAST_USED_PLACE
                FROM mealplans
                WHERE OSU_ID = :osuid
                """)
    public List<MealPlanBalance> mealPlanByOSUID(@Bind("osuid") String osuid)

    @Override
    void close()
}
