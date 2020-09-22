package ml.xorio42.samples.tanks.service

import io.smallrye.mutiny.Multi
import ml.xorio42.samples.tanks.model.Tank
import ml.xorio42.samples.tanks.repository.TanksRepository
import ml.xorio42.samples.tanks.utils.RandomGenerators
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject


/**
 * @author Radu Marias
 * 9/14/20 9:01 AM
 */
@ApplicationScoped
class TanksService(@Inject val repo: TanksRepository, @Inject val random: RandomGenerators) {
	// experimenting with Reactive Vert.x mutiny
	fun randomize(count: Int) =
		Multi.createFrom().range(0, count)
			.onItem().invokeUni { repo.persist(_createRandomTank()) }
			.collectItems().asList()
			.onItem().transform { null }

	fun _createRandomTank() =
		Tank(
			random.nextName(),
			random.nextInt(1, 100),
			random.nextInt(1, 100),
			random.nextInt(0, 3),
			random.nextInt(1, 3),
			random.nextInt(5, 20)
		)

}