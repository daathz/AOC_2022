object Day14 : DayXX() {
    override fun part1() {
        val rockScans = getRockScans(readInput("day14"))

        val rockCoordinates = getRockCoordinates(rockScans)

        val highestY = rockCoordinates.map { it.y }.max()
        var count = -1 // need to start from -1 cause last append is not valid
        do {
            val moveSand = moveSand(rockCoordinates, highestY)
            count++
        } while (moveSand)
        println(count)
    }

    override fun part2() {
        val rockScans = getRockScans(readInput("day14"))

        val rockCoordinates = getRockCoordinates(rockScans)

        val highestY = rockCoordinates.map { it.y }.max()
        var count = 0
        do {
            val moveSand = moveSandUntilStartIsFilled(rockCoordinates, highestY + 2)
            count++
        } while (moveSand)
        println(count)
    }

    private fun getRockScans(input: List<String>): List<List<Coordinate>> = input.map { line -> line.split(" -> ") }
        .map { moves ->
            moves.map { move -> move.split(",") }
                .map { move -> Coordinate(move[0].toInt(), move[1].toInt()) }
        }

    private fun getRockCoordinates(rockScans: List<List<Coordinate>>): MutableSet<Coordinate> {
        val rockCoordinates = mutableSetOf<Coordinate>()

        rockScans.forEach { rockScan ->
            for (i in rockScan.indices) {
                if (i == 0) {
                    rockCoordinates.add(Coordinate(rockScan[i].x, rockScan[i].y))
                } else {
                    val currentMove = rockScan[i]
                    val prevMove = rockScan[i - 1]
                    when {
                        prevMove.x < currentMove.x -> {
                            (prevMove.x .. currentMove.x).forEach { xIndex ->
                                rockCoordinates.add(Coordinate(xIndex, currentMove.y)) }
                        }
                        prevMove.x > currentMove.x -> {
                            (prevMove.x downTo currentMove.x).forEach { xIndex ->
                                rockCoordinates.add(Coordinate(xIndex, currentMove.y))
                            }
                        }
                        prevMove.y < currentMove.y -> {
                            (prevMove.y..currentMove.y).forEach { yIndex ->
                                rockCoordinates.add(Coordinate(currentMove.x, yIndex))
                            }
                        }
                        prevMove.y > currentMove.y -> {
                            (prevMove.y downTo currentMove.y).forEach { yIndex ->
                                rockCoordinates.add(Coordinate(currentMove.x, yIndex))
                            }
                        }
                    }
                }
            }
        }

        return rockCoordinates
    }

    private fun moveSand(rockCoordinates: MutableSet<Coordinate>, highestY: Int): Boolean {
        var sandCoordinate = Coordinate(500, 0)

        while (true) {
            val downCoordinate = Coordinate(sandCoordinate.x, sandCoordinate.y + 1)
            val downLeftCoordinate = Coordinate(sandCoordinate.x - 1, sandCoordinate.y + 1)
            val downRightCoordinate = Coordinate(sandCoordinate.x + 1, sandCoordinate.y + 1)

            sandCoordinate = when {
                !rockCoordinates.contains(downCoordinate) -> downCoordinate
                !rockCoordinates.contains(downLeftCoordinate) -> downLeftCoordinate
                !rockCoordinates.contains(downRightCoordinate) -> downRightCoordinate
                else -> {
                    rockCoordinates.add(sandCoordinate)
                    return true
                }
            }

            if (sandCoordinate.y >= highestY) return false
        }
    }

    private fun moveSandUntilStartIsFilled(rockCoordinates: MutableSet<Coordinate>, highestY: Int): Boolean {
        var sandCoordinate = Coordinate(500, 0)

        while (true) {
            val downCoordinate = Coordinate(sandCoordinate.x, sandCoordinate.y + 1)
            val downLeftCoordinate = Coordinate(sandCoordinate.x - 1, sandCoordinate.y + 1)
            val downRightCoordinate = Coordinate(sandCoordinate.x + 1, sandCoordinate.y + 1)

            sandCoordinate = when (downCoordinate.y < highestY) {
                !rockCoordinates.contains(downCoordinate) -> { downCoordinate
                }
                !rockCoordinates.contains(downLeftCoordinate) -> { downLeftCoordinate
                }
                !rockCoordinates.contains(downRightCoordinate) -> { downRightCoordinate
                }
                else -> {
                    rockCoordinates.add(sandCoordinate)
                    return sandCoordinate != Coordinate(500, 0)
                }
            }
        }
    }
}

fun main() {
    Day14.solve()
}