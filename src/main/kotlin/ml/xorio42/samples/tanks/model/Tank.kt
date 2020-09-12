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
	@Schema(description = "pet status in the store", defaultValue = "default", type = SchemaType.STRING, example = "example")
	var name: String
)
