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
	@Schema(type = SchemaType.STRING) @BsonId var id: ObjectId?,
	var name: String,
	var r: List<Row>
)