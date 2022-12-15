import kotlin.math.abs

data class LCoordinate(val x: Long, val y: Long)

data class SignalPair(val sensor: LCoordinate, val beacon: LCoordinate) {
    fun distance(): Long = abs(sensor.x - beacon.x) + abs(sensor.y - beacon.y)
}
object Day15 : DayXX() {

    private const val Y_LINE = 2000000L
    override fun part1() {
        val sensorCoordinates = mutableSetOf<LCoordinate>()
        val beaconCoordinates = mutableSetOf<LCoordinate>()
        val signalPairs = mutableSetOf<SignalPair>()
        val map = mutableSetOf<LCoordinate>()

        readInput("day15").map { it.split(" ") }
            .forEach {
                val sensorX = it[2].substring(2, it[2].length - 1).toLong()
                val sensorY = it[3].substring(2, it[3].length - 1).toLong()

                val beaconX = it[8].substring(2, it[8].length - 1).toLong()
                val beaconY = it[9].substring(2, it[9].length).toLong()

                map.add(LCoordinate(sensorX, sensorY))
                map.add(LCoordinate(beaconX, beaconY))
                signalPairs.add(SignalPair(LCoordinate(sensorX, sensorY), LCoordinate(beaconX, beaconY)))
            }

        val minX = minOf(map.map{ it.x }.min(), map.map{ it.x }.min())
        val maxX = maxOf(map.map{ it.x }.max(), map.map{ it.x }.max())

        val signalMap = mutableSetOf<LCoordinate>()
        signalPairs.forEach {
            val distanceToY = abs(it.sensor.y - Y_LINE)

            val width = it.distance() - distanceToY

            for (i in it.sensor.x - width..it.sensor.x + width) {
                val c = LCoordinate(i, Y_LINE)
                if (c != it.beacon) {
                    signalMap.add(c)
                }
            }
        }

        val signalsInRow = signalMap.count { it.y == Y_LINE }
        println(signalsInRow)

    }

    override fun part2() {
    }
}

fun main() {
    Day15.solve()
}