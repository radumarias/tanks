package ml.xorio42.samples.tanks.utils.randomname

/**
 * Generates pseudo random unique names that combines one adjective and one noun,
 * like "friendly tiger" or "good apple".
 *
 * There's about 1.5 million unique combinations, and if you keep requesting a new word
 * it will start to loop (but this code will generate all unique combinations before it starts
 * to loop.)
 *
 * @author Kohsuke Kawaguchi
 */
class RandomNameGenerator @JvmOverloads constructor(private var pos: Int = System.currentTimeMillis().toInt()) {
	@Synchronized
	operator fun next(): String {
		val d = Dictionary.INSTANCE
		pos = Math.abs(pos + d.prime) % d.size()
		return d.word(pos)
	}
}