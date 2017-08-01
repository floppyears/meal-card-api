package edu.oregonstate.mist.mealcard.resources

import com.codahale.metrics.annotation.Timed
import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.api.jsonapi.ResourceObject
import edu.oregonstate.mist.api.jsonapi.ResultObject
import edu.oregonstate.mist.mealcard.core.MealPlanBalance
import edu.oregonstate.mist.mealcard.db.AbstractMealPlanDAO
import groovy.transform.TypeChecked

import javax.annotation.security.PermitAll
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.UriBuilder
import javax.ws.rs.core.UriInfo
import javax.ws.rs.core.Response

@PermitAll
@TypeChecked
@Path("diningbalances")
@Produces(MediaType.APPLICATION_JSON)
class MealPlanResource extends Resource {

    private AbstractMealPlanDAO mealPlanDAO
    private URI endpointUri

    @Context
    UriInfo uriInfo

    MealPlanResource(AbstractMealPlanDAO dao, URI endpointUri) {
        this.mealPlanDAO = dao
        this.endpointUri = endpointUri
    }

    @Timed
    @GET
    @Path('{id: [0-9]+}')
    Response getByOSUID(@PathParam('id') String id) {
        ResourceObject balances = new ResourceObject(id : id, type : "balances")

        List<MealPlanBalance> mealPlans = mealPlanDAO.mealPlanByOSUID(id)

        if(mealPlans.size() == 0) {
            return notFound().build()
        }
        balances.attributes = mealPlans
        def res = new ResultObject(
                data : balances
        )
        balances.links = addSelfLink(res)

        ok(res).build()
    }

    private def addSelfLink(ResultObject resultObject) {

        UriBuilder builder = UriBuilder.fromUri(endpointUri).path(this.class).path("{id}")
        [
                'self': builder.build(resultObject.data['id'])
        ]
    }
}
