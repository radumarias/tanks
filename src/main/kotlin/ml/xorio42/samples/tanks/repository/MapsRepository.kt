package ml.xorio42.samples.tanks.repository

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepositoryBase
import ml.xorio42.samples.tanks.model.map.Maps
import org.bson.types.ObjectId
import javax.enterprise.context.ApplicationScoped

/**
 * @author Radu Marias
 * 9/12/20 5:41 PM
 */
@ApplicationScoped
object MapsRepository : ReactivePanacheMongoRepositoryBase<Maps, ObjectId> {
}