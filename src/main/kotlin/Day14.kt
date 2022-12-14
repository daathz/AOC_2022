data class Coordinate(val x: Int, val y: Int)
object Day14 : DayXX() {
    override fun part1() {
    }

    override fun part2() {
        val rockScans = readInput("day14").map { line -> line.split(" -> ") }
            .map { pairs -> pairs.map { pair -> pair.split(",").map { s -> s.toInt() } } }

        val rockCoordinates = mutableSetOf<Coordinate>()

        rockScans.forEach { rockScan ->
            for (i in rockScan.indices) {
                if (i == 0) {
                    rockCoordinates.add(Coordinate(rockScan[i][0], rockScan[i][1]))
                } else {
                    val currentMove = rockScan[i]
                    val prevMove = rockScan[i - 1]
                    if (prevMove[0] < currentMove[0]) {
                        for (j in prevMove[0] .. currentMove[0]) {
                            rockCoordinates.add(Coordinate(j, currentMove[1]))
                        }
                    } else if (prevMove[0] > currentMove[0]) {
                        for (j in prevMove[0]downTo currentMove[0]) {
                            rockCoordinates.add(Coordinate(j, currentMove[1]))
                        }
                    } else if (prevMove[1] < currentMove[1]) {
                        for (j in prevMove[1]..currentMove[1]) {
                            rockCoordinates.add(Coordinate(currentMove[0], j))
                        }
                    } else if (prevMove[1] > currentMove[1]) {
                        for (j in prevMove[1] downTo currentMove[1]) {
                            rockCoordinates.add(Coordinate(currentMove[0], j))
                        }
                    }
                }
            }
        }

        val highestY = rockCoordinates.map { it.y }.max()
        val lowestX = rockCoordinates.map { it.x }.min()
        val highestX = rockCoordinates.map { it.x }.max()
        for (i in lowestX..highestX) {
            rockCoordinates.add(Coordinate(i, highestY + 2))
        }
        var count = 0
        do {
            val moveSand = moveSand(rockCoordinates, highestY + 2)
            count++
        } while (moveSand)
        println(count)
    }

    private fun moveSand(rockCoordinates: MutableSet<Coordinate>, highestY: Int): Boolean {
        var sandCoordinate = Coordinate(500, 0)

        while (true) {
            val downCoordinate = Coordinate(sandCoordinate.x, sandCoordinate.y + 1)
            val downLeftCoordinate = Coordinate(sandCoordinate.x - 1, sandCoordinate.y + 1)
            val downRightCoordinate = Coordinate(sandCoordinate.x + 1, sandCoordinate.y + 1)

            if (!rockCoordinates.contains(downCoordinate) && downCoordinate.y < highestY) {
                sandCoordinate = Coordinate(downCoordinate.x, downCoordinate.y)
            } else if (!rockCoordinates.contains(downLeftCoordinate) && downLeftCoordinate.y < highestY) {
                sandCoordinate = downLeftCoordinate
            } else if (!rockCoordinates.contains(downRightCoordinate) && downLeftCoordinate.y < highestY) {
                sandCoordinate = downRightCoordinate
            } else {
                rockCoordinates.add(sandCoordinate)
                return sandCoordinate != Coordinate(500, 0)
            }
        }
    }
}

fun main() {
    Day14.solve()
}