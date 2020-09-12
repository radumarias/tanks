package ml.xorio42.samples.tanks.utils

import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase
import org.bson.types.ObjectId
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo
import kotlin.reflect.KClass

/**
 * @author Radu Marias
 * 9/13/20 3:33 AM
 */
class EntityUtils {
	companion object {
		@JvmStatic
		fun entityUri(t: Any, id: Any?, kClass: KClass<out Any>, method: String = "read", uriInfo: UriInfo) = Response.created(uriInfo.absolutePathBuilder
			.path(kClass.java, method)
			.build(id))
			.entity(t)
			.build()

		@JvmStatic
		fun read(t: Any?) =
			if (t != null) Response.ok(t).build() else Response.status(Response.Status.NOT_FOUND).build()

		@JvmStatic
		fun readRepo(repo: PanacheMongoRepositoryBase<out Any, ObjectId>, id: String?) =
			try {
				read(repo.findById(ObjectId(id)))
			} catch (e: IllegalArgumentException) {
				Response.status(Response.Status.BAD_REQUEST).build()
			}

		@JvmStatic
		fun delete(repo: PanacheMongoRepositoryBase<out Any, ObjectId>, id: String?) =
			try {
				if (repo.deleteById(ObjectId(id))) {
					Response.ok().build();
				} else {
					read(repo.findById(ObjectId(id)))
				}
			} catch (e: IllegalArgumentException) {
				Response.status(Response.Status.BAD_REQUEST).build()
			}
	}
}