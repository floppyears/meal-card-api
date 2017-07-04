package edu.oregonstate.mist.mealcard.resources

import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.api.jsonapi.ResourceObject
import edu.oregonstate.mist.mealcard.db.MealPlanDAO

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.UriInfo
import javax.xml.ws.Response

@Path("diningbalances")
@Produces(MediaType.APPLICATION_JSON)
class MealPlanResource extends Resource {

    private MealPlanDAO mealPlanDAO
    private URI endpointUri

    MealPlanResource(MealPlanDAO dao, URI endpointUri) {
        this.mealPlanDAO = dao
        this.endpointUri = endpointUri
    }

    @GET
    @Path('{id: [0-9]+}/')
    Response getByOSUID(@PathParam('id') String id) {
        System.out.println("***DEBUG***")
        ResourceObject balances = null

        def plans = mealPlanDAO.mealPlanByOSUID(id)

        balances
    }
}
