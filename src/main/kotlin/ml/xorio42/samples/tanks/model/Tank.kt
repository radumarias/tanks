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
data class Tank(
	@BsonId var id: ObjectId?,
	var name: String,
	var armor: Int,
	var speed: Float, // 1 would mean it cam move each turn, 0.5 stays 1 turn, 2 makes 2 moves per turn
	var hitPower: Int, // how much it will hit other on shoot
	var reloadDelay: Int, // how long it needs to reload, 0 means it can shoot each turn, 1 it stays 1 turn to reload
	var viewRange: Int // radar
)
