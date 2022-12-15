import java.math.BigInteger
import kotlin.math.abs

data class LCoordinate(val x: Long, val y: Long) {
    fun distance(other: LCoordinate): Long = abs(x - other.x) + abs(y - other.y)
}

data class SignalPair(val sensor: LCoordinate, val beacon: LCoordinate)

object Day15 : DayXX() {

    private const val Y_LINE = 2_000_000L
    private const val AREA_MAX = 4_000_000
    private val freq = BigInteger(AREA_MAX.toString())

    override fun part1() {

        val signalPairs = getSignalPairs(readInput("day15"))

        val signalMap = mutableSetOf<LCoordinate>()
        signalPairs.forEach { (sensor, beacon) ->
            val distanceToY = abs(sensor.y - Y_LINE)

            val range = sensor.distance(beacon) - distanceToY

            (sensor.x - range..sensor.x + range).forEach { idx ->
                val current = LCoordinate(idx, Y_LINE)
                if (current != beacon) {
                    signalMap.add(current)
                }
            }
        }

        val signalsInRow = signalMap.count { it.y == Y_LINE }
        println(signalsInRow)
    }

    override fun part2() {
        val signalPairs = getSignalPairs(readInput("day15"))

        val rangesList = mutableListOf(mutableListOf<LongRange>())

        repeat(AREA_MAX + 1) { rangesList.add(mutableListOf()) }

        signalPairs.forEach { (sensor, beacon) ->
            (0..AREA_MAX).forEach { lineY ->
                val distanceToY = abs(sensor.y - lineY)

                val range = sensor.distance(beacon) - distanceToY

                if (range > 0) {
                    val rangeFirst = maxOf(0, sensor.x - range)
                    val rangeLast = minOf(AREA_MAX.toLong(), sensor.x + range)

                    rangesList[lineY].add(rangeFirst..rangeLast)
                }
            }
        }

        rangesList.map { it.sortedBy { ranges -> ranges.first } }
            .forEachIndexed { index, ranges ->
                var endOfLastRange = ranges.first().last

                (1 until ranges.size).forEach { idx ->
                    val firstLongOfNewRange = ranges[idx].first

                    when {
                        firstLongOfNewRange > endOfLastRange -> {
                            return println(getTuningFrequency(index, endOfLastRange + 1))
                        }

                        ranges[idx].last > endOfLastRange -> endOfLastRange = ranges[idx].last
                    }
                }
            }
    }

    private fun getSignalPairs(input: List<String>): MutableSet<SignalPair> {
        val signalPairs = mutableSetOf<SignalPair>()

        input.map { it.split(" ") }
            .forEach {
                val sensorX = it[2].substring(2, it[2].length - 1).toLong()
                val sensorY = it[3].substring(2, it[3].length - 1).toLong()

                val beaconX = it[8].substring(2, it[8].length - 1).toLong()
                val beaconY = it[9].substring(2, it[9].length).toLong()

                signalPairs.add(SignalPair(LCoordinate(sensorX, sensorY), LCoordinate(beaconX, beaconY)))
            }

        return signalPairs
    }

    private fun getTuningFrequency(index: Int, firstLongOfNewRange: Long): BigInteger {
        return firstLongOfNewRange.toBigInteger() * freq + index.toBigInteger()
    }
}

fun main() {
    Day15.solve()
}