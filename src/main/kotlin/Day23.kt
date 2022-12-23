object Day23 : DayXX() {
    override fun part1() {
        val elfCoordinates = mutableSetOf<Coordinate>()
        readInput("day23").forEachIndexed { lIndex, line ->
            line.forEachIndexed { cIndex, c ->
                if (c == '#') elfCoordinates.add(Coordinate(lIndex, cIndex))
            }
        }
        var direction = 0

        var round = 1
        while (true) {
            var moves = mutableSetOf<Pair<Coordinate, Coordinate>>()
            var notMoved = 0
            elfCoordinates.forEach { elf ->
                if (!elf.checkSurrounding(elfCoordinates)) {
                    when (direction) {
                        0 -> {
                            if (elf.proposeNorth(elfCoordinates)) {
                                moves.add(elf to Coordinate(elf.x - 1, elf.y))
                            } else if (elf.proposeSouth(elfCoordinates)) {
                                moves.add(elf to Coordinate(elf.x + 1, elf.y))
                            } else if (elf.proposeWest(elfCoordinates)) {
                                moves.add(elf to Coordinate(elf.x, elf.y - 1))
                            } else if (elf.proposeEast(elfCoordinates)) {
                                moves.add(elf to Coordinate(elf.x, elf.y + 1))
                            }
                        }
                        1 -> {
                            if (elf.proposeSouth(elfCoordinates)) {
                                moves.add(elf to Coordinate(elf.x + 1, elf.y))
                            } else if (elf.proposeWest(elfCoordinates)) {
                                moves.add(elf to Coordinate(elf.x, elf.y - 1))
                            } else if (elf.proposeEast(elfCoordinates)) {
                                moves.add(elf to Coordinate(elf.x, elf.y + 1))
                            } else if (elf.proposeNorth(elfCoordinates)) {
                                moves.add(elf to Coordinate(elf.x - 1, elf.y))
                            }
                        }
                        2 -> {
                            if (elf.proposeWest(elfCoordinates)) {
                                moves.add(elf to Coordinate(elf.x, elf.y - 1))
                            } else if (elf.proposeEast(elfCoordinates)) {
                                moves.add(elf to Coordinate(elf.x, elf.y + 1))
                            } else if (elf.proposeNorth(elfCoordinates)) {
                                moves.add(elf to Coordinate(elf.x - 1, elf.y))
                            } else if (elf.proposeSouth(elfCoordinates)) {
                                moves.add(elf to Coordinate(elf.x + 1, elf.y))
                            }
                        }
                        else -> {
                            if (elf.proposeEast(elfCoordinates)) {
                                moves.add(elf to Coordinate(elf.x, elf.y + 1))
                            } else if (elf.proposeNorth(elfCoordinates)) {
                                moves.add(elf to Coordinate(elf.x - 1, elf.y))
                            } else if (elf.proposeSouth(elfCoordinates)) {
                                moves.add(elf to Coordinate(elf.x + 1, elf.y))
                            } else if (elf.proposeWest(elfCoordinates)) {
                                moves.add(elf to Coordinate(elf.x, elf.y - 1))
                            }
                        }
                    }
                } else notMoved++
            }

            if (notMoved == elfCoordinates.size) {
                println(round)
                break
            }

            val inspected = mutableListOf<Coordinate>()
            val duplicate = mutableListOf<Coordinate>()
            moves.forEach { move ->
                if (!inspected.contains(move.second)) {
                    inspected.add(move.second)
                } else  duplicate.add(move.second)
            }

            moves = moves.filter { !duplicate.contains(it.second) }.toMutableSet()

            moves.forEach { move ->
                elfCoordinates.remove(move.first)
                elfCoordinates.add(move.second)
            }

            direction = (direction + 1) % 4
            round += 1

            /*val minX = elfCoordinates.map { it.x }.min()
            val maxX = elfCoordinates.map { it.x  }.max()
            val minY = elfCoordinates.map { it.y }.min()
            val maxY = elfCoordinates.map { it.y  }.max()

            println("=== MOVE ===")
            for (x in minX..maxX) {
                for (y in minY..maxY) {
                    if (elfCoordinates.contains(Coordinate(x, y))) print('#') else print('.')
                }
                println()
            }
            println()*/
        }

        /*val minX = elfCoordinates.map { it.x }.min()
        val maxX = elfCoordinates.map { it.x  }.max()
        val minY = elfCoordinates.map { it.y }.min()
        val maxY = elfCoordinates.map { it.y  }.max()

        var count = 0
        for (x in minX..maxX) {
            for (y in minY..maxY) {
                if (!elfCoordinates.contains(Coordinate(x, y))) {
                    count++
                }
            }
        }

        println(count)*/
    }

    override fun part2() {
    }
}

fun Coordinate.checkSurrounding(elfCoordinates: Set<Coordinate>): Boolean {
    val northWest = Coordinate(this.x - 1, this.y - 1)
    val north = Coordinate(this.x - 1, this.y)
    val northEast = Coordinate(this.x - 1, this.y + 1)
    val west = Coordinate(this.x, this.y - 1)
    val east = Coordinate(this.x, this.y + 1)
    val southWest = Coordinate(this.x + 1, this.y - 1)
    val south = Coordinate(this.x + 1, this.y)
    val southEast = Coordinate(this.x + 1, this.y + 1)

    val surrounding = setOf(
        northWest, northEast, north, west, east, southWest, south, southEast
    )

    return surrounding.subtract(elfCoordinates).size == 8
}

fun Coordinate.proposeNorth(elfCoordinates: Set<Coordinate>): Boolean {
    val northCoordinates = setOf(
        Coordinate(this.x - 1, this.y - 1),
        Coordinate(this.x - 1, this.y),
        Coordinate(this.x - 1, this.y + 1)
    )

    return northCoordinates.subtract(elfCoordinates).size == 3
}

fun Coordinate.proposeSouth(elfCoordinates: Set<Coordinate>): Boolean {
    val southCoordinates = setOf(
        Coordinate(this.x + 1, this.y - 1),
        Coordinate(this.x + 1, this.y),
        Coordinate(this.x + 1, this.y + 1)
    )

    return southCoordinates.subtract(elfCoordinates).size == 3
}

fun Coordinate.proposeWest(elfCoordinates: Set<Coordinate>): Boolean {
    val westCoordinates = setOf(
        Coordinate(this.x, this.y - 1),
        Coordinate(this.x - 1, this.y - 1),
        Coordinate(this.x + 1, this.y - 1)
    )

    return westCoordinates.subtract(elfCoordinates).size == 3
}

fun Coordinate.proposeEast(elfCoordinates: Set<Coordinate>): Boolean {
    val eastCoordinates = setOf(
        Coordinate(this.x, this.y + 1),
        Coordinate(this.x - 1, this.y + 1),
        Coordinate(this.x + 1, this.y + 1)
    )

    return eastCoordinates.subtract(elfCoordinates).size == 3
}



fun main() {
    Day23.solve()
}