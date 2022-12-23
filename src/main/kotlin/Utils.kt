import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

const val FOREGROUND_CHAR = '█'
const val BACKGROUND_CHAR = '░'

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/main/resources", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun Char.toNumber() = toString().toInt()

data class Coordinate(val x: Int, val y: Int) {

    operator fun plus(other: Coordinate): Coordinate {
        return this.x + other.x toY this.y + other.y
    }
    fun checkSurrounding(elfCoordinates: Set<Coordinate>): Boolean {
        val surrounding = allSurrounding.map { this + it }.toSet()

        return surrounding.subtract(elfCoordinates).size == 8
    }

    fun propose(startDirection: Int, elfCoordinates: Set<Coordinate>): Coordinate? {
        for (i in startDirection until startDirection + 4) {
            val directionIdx = i % 4
            val adjacentCoordinates = directions[directionIdx].map { it + this }.toSet()

            if (adjacentCoordinates.subtract(elfCoordinates).size == 3) {
                val direction = when (i % 4) {
                    0 -> north
                    1 -> south
                    2 -> west
                    3 -> east
                    else -> null
                }

                return this + direction!!
            }
        }

        return null
    }

    companion object {
        private val northWest = -1 toY -1
        private val north = -1 toY 0
        private val northEast = -1 toY 1
        private val west = 0 toY -1
        private val east = 0 toY 1
        private val southWest = 1 toY -1
        private val south = 1 toY 0
        private val southEast = 1 toY 1

        private val allSurrounding = setOf(
            northWest, north, northEast, west, east, southWest, south, southEast
        )

        private val norths = setOf(northWest, north, northEast)
        private val souths = setOf(southWest, south, southEast)
        private val wests = setOf(northWest, west, southWest)
        private val easts = setOf(northEast, east, southEast)

        private val directions = listOf(norths, souths, wests, easts)
    }
}

infix fun Int.toY(other: Int): Coordinate {
    return Coordinate(this, other)
}