package edu.oregonstate.mist.mealcard.resources

import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.mealcard.db.MealPlanDAO

class MealPlanResource extends Resource {

    private MealPlanDAO mealPlanDAO
    private URI endpointUri

    MealPlanResource(MealPlanDAO dao, URI endpointUri) {
        this.mealPlanDAO = dao
        this.endpointUri = endpointUri
    }
}
