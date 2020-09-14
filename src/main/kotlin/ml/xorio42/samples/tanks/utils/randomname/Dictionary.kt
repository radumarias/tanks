package ml.xorio42.samples.tanks.utils.randomname

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

/**
 * Dictionary of adjectives and nouns.
 *
 * @author Kohsuke Kawaguchi
 */
class Dictionary {
	private val nouns: MutableList<String> = ArrayList()
	private val adjectives: MutableList<String> = ArrayList()

	/**
	 * Sufficiently big prime that's bigger than [.size]
	 */
	val prime: Int

	/**
	 * Total size of the combined words.
	 */
	fun size(): Int {
		return nouns.size * adjectives.size
	}

	fun word(i: Int): String {
		val a = i % adjectives.size
		val n = i / adjectives.size
		return adjectives[a] + "_" + nouns[n]
	}

	@Throws(IOException::class)
	private fun load(name: String, col: MutableList<String>) {
		val r = BufferedReader(InputStreamReader(this::class.java.getResourceAsStream(name)))
		try {
			var line: String? = null
			while (r.readLine()?.also { line = it } != null) line?.let { col.add(it) }
		} finally {
			r.close()
		}
	}

	companion object {
		@JvmField
		val INSTANCE = Dictionary()
	}

	init {
		try {
			load("/ml.xorio42.samples.tanks.utils.randomname/a.txt", adjectives)
			load("/ml.xorio42.samples.tanks.utils.randomname/n.txt", nouns)
		} catch (e: IOException) {
			throw Error(e)
		}
		val combo = size()
		var primeCombo = 2
		while (primeCombo <= combo) {
			val nextPrime = primeCombo + 1
			primeCombo *= nextPrime
		}
		prime = primeCombo + 1
	}
}