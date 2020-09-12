package ml.xorio42.samples.tanks.repository

import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase
import ml.xorio42.samples.tanks.model.map.Maps
import org.bson.types.ObjectId
import javax.enterprise.context.ApplicationScoped

/**
 * @author Radu Marias
 * 9/12/20 5:41 PM
 */
@ApplicationScoped
object MapsRepository : PanacheMongoRepositoryBase<Maps, ObjectId> {
}