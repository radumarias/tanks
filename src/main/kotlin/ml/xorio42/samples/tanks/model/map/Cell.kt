package ml.xorio42.samples.tanks.model.map

import ml.xorio42.samples.tanks.utils.GenerateNoArgConstructor

@GenerateNoArgConstructor
data class Cell(
	var obstacle: Boolean = false
)