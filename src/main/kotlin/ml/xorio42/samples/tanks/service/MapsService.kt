package ml.xorio42.samples.tanks.service

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import ml.xorio42.samples.tanks.model.map.Cell
import ml.xorio42.samples.tanks.model.map.Maps
import ml.xorio42.samples.tanks.model.map.Row
import ml.xorio42.samples.tanks.repository.MapsRepository
import ml.xorio42.samples.tanks.utils.Point
import ml.xorio42.samples.tanks.utils.RandomGenerators
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject


/**
 * @author Radu Marias
 * 9/14/20 9:01 AM
 */
@ApplicationScoped
class MapsService(@Inject val repo: MapsRepository, @Inject val random: RandomGenerators) {
	companion object {
		val mapMinSize = 50
		val mapMaxSize = 256
		val regularCellChar = "_"
		val obstacleCellChar = "X"
	}

	// experimenting with Kotlin coroutines to compare with Reactive style
	fun randomize(count: Int, difficulty: Int) =
		runBlocking {
			// start `count` coroutines
			(1..count).map { n ->
				async { insert(_createRandomMap(difficulty)); n }
			}.map { it.await().toLong() } // wait for all to finish
		}


	suspend fun insert(t: Maps) = repo.persist(t).await().indefinitely()

	fun _createRandomMap(difficulty: Int): Maps {
		val width = random.nextInt(mapMinSize, mapMaxSize)
		val height = random.nextInt(mapMinSize, mapMaxSize)
		val numObstacles = (difficulty * width * height) / 100
		val points = mutableSetOf<Point>()

		// generate the number of obstacles
		lateinit var p: Point;
		repeat(numObstacles) {
			// keep generate new one until we have one unique
			while (points.contains({ p = Point(random.nextInt(0, height - 1), random.nextInt(0, width - 1)); p }())) {
				points.add(p);
			}
		}

		val rows = mutableListOf<Row>()
		repeat(height) { row ->
			val cells = mutableListOf<Cell>()
			repeat(width) {
				// add obstacle cell if in our generated obstacles, regular cell if not
				cells.add(Cell(points.contains(Point(row, it))))
			}
			rows.add(Row(cells))
		}

		return Maps(random.nextName(), rows)
	}

	fun addDisplayString(list: List<Maps>): List<Maps> {
		list.map { it.displayString = _generateDisplayString(it); it }

		return list
	}

	fun _generateDisplayString(map: Maps): String {
		val sb = StringBuilder("\n")
		map.r.forEach {
			it.c.forEach {
				sb.append(if (it.obstacle) obstacleCellChar else regularCellChar)
			}
			sb.append("\n")
		}

		return sb.toString()
	}
}