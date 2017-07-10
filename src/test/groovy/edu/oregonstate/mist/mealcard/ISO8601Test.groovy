package edu.oregonstate.mist.mealcard

import edu.oregonstate.mist.mealcard.mapper.MealPlanBalanceMapper
import org.junit.Test

import java.sql.ResultSet

import static org.junit.Assert.assertTrue

class ISO8601Test {
    @Test
    public void testDateConversion() {
        def rs = {"2017-06-28 07:06:00"} as ResultSet
        def date = MealPlanBalanceMapper.getIso8601Date("LAST_USED_DATE" , rs)

        assertTrue(date.contains("Z"))
        assertTrue(date.contains("T"))
        assertTrue(date.contains("2017-06-28"))
        assertTrue(date.contains("07:06:00"))
    }
}
