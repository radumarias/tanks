package ml.xorio42.samples.tanks.utils

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepositoryBase
import io.smallrye.mutiny.Uni
import org.bson.types.ObjectId
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo
import kotlin.reflect.KClass

/**
 * @author Radu Marias
 * 9/13/20 3:33 AM
 */
@ApplicationScoped
class EntityUtils {
	fun entityUri(t: Any, id: Any?, kClass: KClass<out Any>, method: String = "read",
	              uriInfo: UriInfo) = Response.created(uriInfo.absolutePathBuilder
		.path(kClass.java, method)
		.build(id))
		.entity(t)
		.build()

	fun read(t: Any?) =
		if (t != null) Response.ok(t).build() else Response.status(Response.Status.NOT_FOUND).build()

	fun readRepo(repo: ReactivePanacheMongoRepositoryBase<out Any, ObjectId>, id: String?) =
		Uni.createFrom()
			.item(id)
			.onItem().transform { ObjectId(it) }
			.chain { repo.findById(it) }
			.onItem().transform { read(it) }
			.onFailure(IllegalArgumentException::class.java)
			.recoverWithItem(Response.status(Response.Status.BAD_REQUEST).build())

	fun delete(repo: ReactivePanacheMongoRepositoryBase<out Any, ObjectId>, id: String?) =
		Uni.createFrom()
			.item(id)
			.onItem().transform { ObjectId(it) }
			.chain { repo.deleteById(it) }
			.onItem().transform { if (it) Response.ok().build() else Response.status(Response.Status.NOT_FOUND).build() }
			.onFailure(IllegalArgumentException::class.java)
			.recoverWithItem(Response.status(Response.Status.BAD_REQUEST).build())
}