package ml.xorio42.samples.tanks.utils

class Point(val x: Int, val y: Int) {
	override fun equals(other: Any?) = other is Point && other.x == x && other.y == y

	override fun hashCode() = (x + y).hashCode()
}