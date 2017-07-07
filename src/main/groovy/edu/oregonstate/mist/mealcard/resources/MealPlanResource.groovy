package edu.oregonstate.mist.mealcard.resources

import com.codahale.metrics.annotation.Timed
import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.api.jsonapi.ResourceObject
import edu.oregonstate.mist.api.jsonapi.ResultObject
import edu.oregonstate.mist.mealcard.db.MealPlanDAO

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.UriInfo
import javax.ws.rs.core.Response

@Path("diningbalances")
@Produces(MediaType.APPLICATION_JSON)
class MealPlanResource extends Resource {

    private MealPlanDAO mealPlanDAO
    private URI endpointUri

    @Context
    UriInfo uriInfo

    MealPlanResource(MealPlanDAO dao, URI endpointUri) {
        this.mealPlanDAO = dao
        this.endpointUri = endpointUri
    }

    @Timed
    @GET
    @Path('{id: [0-9]+}')
    Response getByOSUID(@PathParam('id') String id) {
        ResourceObject balances = new ResourceObject(id : id, type : "balances")

        balances.attributes = mealPlanDAO.mealPlanByOSUID(id)

        if(balances.attributes.size == 0) {
            return notFound().build()
        }

        ok(new ResultObject(
                data : balances
        )).build()
    }
}
