package ml.xorio42.samples.tanks.api

import io.smallrye.mutiny.Uni
import ml.xorio42.samples.tanks.model.map.Maps
import ml.xorio42.samples.tanks.repository.MapsRepository
import ml.xorio42.samples.tanks.service.MapsService
import ml.xorio42.samples.tanks.utils.EntityUtils
import org.bson.types.ObjectId
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.UriInfo

@Path("/api/v1/map")
@Tag(name = "Maps")
class MapsResource(@Inject val repo: MapsRepository, @Inject val entityUtils: EntityUtils,
				   @Inject val service: MapsService) {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@APIResponses(value = [
		APIResponse(responseCode = "200", content = [Content(schema = Schema(implementation = Maps::class))]),
		APIResponse(responseCode = "400", description = "If provided id format is wrong."),
		APIResponse(responseCode = "404", description = "If entity is not found.")
	])
	fun read(@PathParam("id") id: String) = entityUtils.readRepo(repo, id)

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	fun all() = repo.listAll().map { service.addDisplayString(it) }

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponse(responseCode = "204", description = "(Created) and contain an entity which describes the status of the request " +
		"and refers to the new resource, and a Location header.")
	fun insert(t: Maps, @Context uriInfo: UriInfo) =
		Uni.createFrom().item(t)
			.onItem().invoke { it.id = null }
			.invokeUni { repo.persist(it) }
			.onItem().transform { entityUtils.entityUri(it, it.id, this::class, uriInfo = uriInfo) }

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	fun update(@PathParam("id") id: String, t: Maps) =
		Uni.createFrom().item(id to t)
			.onItem().transform { ObjectId(it.first) to it.second }
			.invoke { it.second.id = it.first }
			.chain { repo.update(it.second) }

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	@APIResponses(value = [
		APIResponse(responseCode = "200", content = []),
		APIResponse(responseCode = "400", description = "If provided id format is wrong."),
		APIResponse(responseCode = "404", description = "If entity is not found.")
	])
	fun delete(@PathParam("id") id: String) = entityUtils.delete(repo, id)

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	@APIResponse(responseCode = "200", description = "The number of entities deleted.")
	fun deleteAll() = repo.deleteAll()

	@POST
	@Path("/randomize")
	@APIResponse(responseCode = "200", content = [])
	fun randomize(@QueryParam("count") count: Int,
				  @QueryParam("difficulty")
				  @Parameter(name = "difficulty", description = "Percentage of map covered with obstacles.")
				  difficulty: Int) {
		// service.randomize would block the main thread until it process but the Uni ensures we don't block calling thread
		// most likely that thread will be from REST caller so it will be free to process other calls
		Uni.createFrom().item(service.randomize(count, difficulty))
	}

}
