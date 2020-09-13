package ml.xorio42.samples.tanks.api

import ml.xorio42.samples.tanks.model.map.Maps
import ml.xorio42.samples.tanks.repository.MapsRepository
import ml.xorio42.samples.tanks.utils.EntityUtils
import ml.xorio42.samples.tanks.utils.EntityUtils.Companion.delete
import ml.xorio42.samples.tanks.utils.EntityUtils.Companion.readRepo
import org.bson.types.ObjectId
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

@Path("/api/v1/map")
@Tag(name = "Maps")
class MapsResource(@Inject val repo: MapsRepository) {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	fun read(@PathParam("id") id: String) = readRepo(repo, id)

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	fun all() = repo.listAll()

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	fun insert(t: Maps, @Context uriInfo: UriInfo): Response {
		repo.persist(t)

		return EntityUtils.entityUri(t, t.id, this::class, uriInfo = uriInfo)
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	fun update(@PathParam("id") id: String, t: Maps): Maps {
		t.id = ObjectId(id);
		repo.update(t)

		return t;
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	fun delete(@PathParam("id") id: String) = delete(repo, id)

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	@APIResponse(responseCode = "200", description = "The number of entities deleted.")
	fun deleteAll() = repo.deleteAll()
}
