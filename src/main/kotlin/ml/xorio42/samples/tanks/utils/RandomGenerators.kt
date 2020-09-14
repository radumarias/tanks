package ml.xorio42.samples.tanks.utils

import ml.xorio42.samples.tanks.utils.randomname.RandomNameGenerator
import javax.enterprise.context.ApplicationScoped
import kotlin.random.Random

/**
 * @author Radu Marias
 * 9/14/20 9:32 AM
 */
@ApplicationScoped
object RandomGenerators {
	val randomName = RandomNameGenerator()
	val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')


	fun nextName() = randomName.next()

	fun nextString(count: Int) = (1..count)
		.map { i -> Random.nextInt(0, charPool.size) }
		.map(charPool::get)
		.joinToString("");

	fun nextInt(from: Int, until: Int) = Random.nextInt(from, until)

	fun nextFloat(from: Float, until: Float) = Random.nextDouble(from.toDouble(), until.toDouble()).toFloat()

}