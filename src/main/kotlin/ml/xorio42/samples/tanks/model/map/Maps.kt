package ml.xorio42.samples.tanks.model.map

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
data class Maps(
	var name: String,
	var r: List<Row>
) {
	@BsonId
	@Schema(required = true, example = "1a2b3c", type = SchemaType.STRING, implementation = String::class)
	var id: ObjectId? = null

	@Transient
	@Schema(description = "String display representation with _ and X chars, is just for view only, it's not persisted.")
	var displayString: String? = null
}