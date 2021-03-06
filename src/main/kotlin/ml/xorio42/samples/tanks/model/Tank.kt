package ml.xorio42.samples.tanks.model

import io.quarkus.mongodb.panache.MongoEntity
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType
import org.eclipse.microprofile.openapi.annotations.media.Schema


/**
 * @author Radu Marias
 * 9/12/20 3:12 PM
 */
@MongoEntity
class Tank(
	@Schema(required = true, example = "1a2b3c", type = SchemaType.INTEGER)
	var name: String,
	var armor: Int,
	var hitPower: Int, // how much it will hit other on shoot
	var reloadDelay: Int, // how long it needs to reload, 0 means it can shoot each turn, 1 it stays 1 turn to reload
	var speed: Int, // how many cells can move each turn
	var viewRange: Int // radar
) {
	@BsonId
	@Schema(required = true, example = "1a2b3c", type = SchemaType.STRING, implementation = String::class)
	var id: ObjectId? = null
}
