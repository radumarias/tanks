package ml.xorio42.samples.tanks.model.map

import ml.xorio42.samples.tanks.utils.GenerateNoArgConstructor

/**
 * @author Radu Marias
 * 9/13/20 4:57 AM
 */
@GenerateNoArgConstructor
data class Row(
	var c: List<Cell>
)