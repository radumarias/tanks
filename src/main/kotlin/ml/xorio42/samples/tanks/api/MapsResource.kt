package ml.xorio42.samples.tanks.api

import io.smallrye.mutiny.Uni
import ml.xorio42.samples.tanks.model.Tank
import ml.xorio42.samples.tanks.model.map.Maps
import ml.xorio42.samples.tanks.repository.MapsRepository
import ml.xorio42.samples.tanks.utils.EntityUtils
import org.bson.types.ObjectId
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

@Path("/api/v1/map")
@Tag(name = "Maps")
class MapsResource(@Inject val repo: MapsRepository, @Inject val entityUtils: EntityUtils) {

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
	fun all() = repo.listAll()

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
}
