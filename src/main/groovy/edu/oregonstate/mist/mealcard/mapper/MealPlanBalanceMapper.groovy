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

    final String LAST_USED_DATE_COLUMN_NAME = "LAST_USED_DATE"

    private static DateTimeFormatter dbInputFormatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.of("UTC"))

    private static DateTimeFormatter iso8601outputFormatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .withZone(ZoneId.of("UTC"))

    @Override
    MealPlanBalance map(int index , ResultSet rs , StatementContext ctx)
            throws SQLException {
        new MealPlanBalance(
                    mealPlanID : rs.getInt("MEALPLAN_ID"),
                    balance : rs.getFloat("BALANCE"),
                    mealPlanType : rs.getString("MEALPLAN_DESC"),
                    lastUsedDate : getIso8601Date(LAST_USED_DATE_COLUMN_NAME, rs),
                    lastUsedPlace : rs.getString("LAST_USED_PLACE")
        )
    }

    static String getIso8601Date(String dateFieldColumnName , ResultSet rs) {
        String rawInputDate = rs.getString(dateFieldColumnName)
        ZonedDateTime dbDate = ZonedDateTime.parse(rawInputDate ,dbInputFormatter)
        dbDate.format(iso8601outputFormatter)
    }
}
