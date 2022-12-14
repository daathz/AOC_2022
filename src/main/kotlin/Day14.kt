data class Coordinate(val x: Int, val y: Int)
object Day14 : DayXX() {
    override fun part1() {
        val rockScans = readInput("day14").map { line -> line.split(" -> ") }
            .map { pairs -> pairs.map { pair -> pair.split(",").map { s -> s.toInt() } } }

        val rockCoordinates = mutableListOf<Coordinate>()

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
        var count = 1
        do {
            val moveSand = moveSand(rockCoordinates, highestY)
            println(count)
            count++
        } while (moveSand)
//        rockCoordinates.forEach(::println)


    }

    override fun part2() {
    }

    private fun moveSand(rockCoordinates: MutableList<Coordinate>, highestY: Int): Boolean {
        var sandCoordinate = Coordinate(500, 0)

        while (true) {
            val downCoordinate = Coordinate(sandCoordinate.x, sandCoordinate.y + 1)
            val downLeftCoordinate = Coordinate(sandCoordinate.x - 1, sandCoordinate.y + 1)
            val downRightCoordinate = Coordinate(sandCoordinate.x + 1, sandCoordinate.y + 1)

            if (!rockCoordinates.contains(downCoordinate)) {
                sandCoordinate = Coordinate(downCoordinate.x, downCoordinate.y)
            } else if (!rockCoordinates.contains(downLeftCoordinate)) {
                sandCoordinate = downLeftCoordinate
            } else if (!rockCoordinates.contains(downRightCoordinate)) {
                sandCoordinate = downRightCoordinate
            } else {
                rockCoordinates.add(sandCoordinate)
                return sandCoordinate.y <= highestY
            }
        }

        return true
    }
}

fun main() {
    Day14.solve()
}