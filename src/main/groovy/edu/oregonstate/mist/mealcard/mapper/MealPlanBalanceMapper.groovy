package edu.oregonstate.mist.mealcard.mapper

import edu.oregonstate.mist.mealcard.core.MealPlanBalance
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper

import java.sql.ResultSet
import java.sql.SQLException
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class MealPlanBalanceMapper implements ResultSetMapper<MealPlanBalance> {

    private static DateTimeFormatter dbFormatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.of("UTC"))

    private static DateTimeFormatter outputFormatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .withZone(ZoneId.of("UTC"))

    @Override
    MealPlanBalance map(int index , ResultSet rs , StatementContext ctx)
            throws SQLException {
        def dbDate = ZonedDateTime.parse(rs.getString("LAST_USED_DATE") ,dbFormatter)
        String nowAsISO = dbDate.format(outputFormatter)

        new MealPlanBalance(
                    mealPlanID : rs.getInt("MEALPLAN_ID"),
                    balance : rs.getFloat("BALANCE"),
                    mealPlanType : rs.getString("MEALPLAN_DESC"),
                    lastUsedDate : nowAsISO,
                    lastUsedPlace : rs.getString("LAST_USED_PLACE")
        )
    }
}
