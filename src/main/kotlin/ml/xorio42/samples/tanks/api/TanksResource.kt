package ml.xorio42.samples.tanks.api

import ml.xorio42.samples.tanks.model.Tank
import ml.xorio42.samples.tanks.repository.TankRepository
import ml.xorio42.samples.tanks.utils.EntityUtils
import ml.xorio42.samples.tanks.utils.EntityUtils.Companion.delete
import ml.xorio42.samples.tanks.utils.EntityUtils.Companion.entityUri
import org.bson.types.ObjectId
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

@Path("/api/v1/tanks")
@Tag(name = "Tanks")
class TanksResource(@Inject val repo: TankRepository) {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	fun read(@PathParam("id") id: String) = EntityUtils.readRepo(repo, id)

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	fun all() = repo.listAll()

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponse(responseCode = "204", description = "(Created) and contain an entity which describes the status of the request " +
		"and refers to the new resource, and a Location header.")
	fun insert(t: Tank, @Context uriInfo: UriInfo): Response {
		repo.persist(t)

		return entityUri(t, t.id, this::class, uriInfo = uriInfo)
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	fun update(@PathParam("id") id: String, t: Tank): Tank {
		t.id = ObjectId(id);
		repo.update(t)

		return t;
	}

	@DELETE
	@Path("{id}")
	fun delete(@PathParam("id") id: String) = delete(repo, id)

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	@APIResponse(responseCode = "200", description = "The number of entities deleted.")
	fun deleteAll() = repo.deleteAll()
}
