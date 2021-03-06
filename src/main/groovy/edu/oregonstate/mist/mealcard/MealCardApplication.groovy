package edu.oregonstate.mist.mealcard

import edu.oregonstate.mist.api.Application
import edu.oregonstate.mist.mealcard.db.AbstractMealPlanDAO
import edu.oregonstate.mist.mealcard.db.TestMealPlanDAO
import edu.oregonstate.mist.mealcard.health.MealCardHealthCheck
import edu.oregonstate.mist.mealcard.resources.MealPlanResource
import io.dropwizard.jdbi.DBIFactory
import io.dropwizard.setup.Environment
import org.skife.jdbi.v2.DBI

import edu.oregonstate.mist.contrib.MealPlanDAO

/**
 * Main application class.
 */
class MealCardApplication extends Application<MealCardConfiguration> {
    /**
     * Parses command-line arguments and runs the application.
     *
     * @param configuration
     * @param environment
     */
    @Override
    public void run(MealCardConfiguration configuration, Environment environment) {
        this.setup(configuration, environment)

        DBIFactory factory = new DBIFactory()
        DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "jdbi")

        AbstractMealPlanDAO mealPlanDAO

        if(configuration.useTestDAO) {
            mealPlanDAO = (AbstractMealPlanDAO) jdbi.onDemand(TestMealPlanDAO.class)
        } else {
            mealPlanDAO = (AbstractMealPlanDAO) jdbi.onDemand(MealPlanDAO.class)
        }

        environment.jersey().register(new MealPlanResource(mealPlanDAO,
                configuration.api.endpointUri))

        MealCardHealthCheck healthCheck = new MealCardHealthCheck(mealPlanDAO)
        environment.healthChecks().register("mealCardHealthCheck", healthCheck)
    }

    /**
     * Instantiates the application class with command-line arguments.
     *
     * @param arguments
     * @throws Exception
     */
    public static void main(String[] arguments) throws Exception {
        new MealCardApplication().run(arguments)
    }
}
