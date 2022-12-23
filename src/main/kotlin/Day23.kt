object Day23 : DayXX() {
    override fun part1() {
        val elfCoordinates = getElfCoordinates(readInput("day23"))

        var startDirection = 0

        repeat(10) {
            val moves = mutableSetOf<Pair<Coordinate, Coordinate>>()
            elfCoordinates.forEach { elf ->
                if (!elf.checkSurrounding(elfCoordinates)) {
                    val possibleCoordinate = elf.propose(startDirection, elfCoordinates)

                    if (possibleCoordinate is Coordinate) moves.add(elf to possibleCoordinate)
                }
            }

            removeSamePossiblePositions(moves).forEach { move ->
                elfCoordinates.remove(move.first)
                elfCoordinates.add(move.second)
            }

            startDirection++

             // printMap(elfCoordinates)
        }

        val minX = elfCoordinates.map { it.x }.min()
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

        println(count)
    }

    override fun part2() {
        val elfCoordinates = getElfCoordinates(readInput("day23"))

        var startDirection = 0

        var rounds = 1
        while(true) {
            var notMoved = 0
            var moves = mutableSetOf<Pair<Coordinate, Coordinate>>()
            elfCoordinates.forEach { elf ->
                if (!elf.checkSurrounding(elfCoordinates)) {
                    val possibleCoordinate = elf.propose(startDirection, elfCoordinates)

                    if (possibleCoordinate is Coordinate) moves.add(elf to possibleCoordinate)
                } else notMoved++
            }

            if (notMoved == elfCoordinates.size) {
                println(rounds)
                break
            }

            moves = removeSamePossiblePositions(moves)

            moves.forEach { move ->
                elfCoordinates.remove(move.first)
                elfCoordinates.add(move.second)
            }

            startDirection = (startDirection + 1) % 4
            rounds++
        }
    }

    private fun getElfCoordinates(input: List<String>): MutableSet<Coordinate> = input.flatMapIndexed { lIndex, line ->
        line.mapIndexed { cIndex, c ->
            if (c == '#') lIndex toY cIndex else null
        }
    }.filterNotNull().toMutableSet()

    private fun removeSamePossiblePositions(moves: MutableSet<Pair<Coordinate, Coordinate>>): MutableSet<Pair<Coordinate, Coordinate>> {
        val inspected = mutableListOf<Coordinate>()
        val duplicate = mutableListOf<Coordinate>()

        moves.forEach { move ->
            if (!inspected.contains(move.second)) {
                inspected.add(move.second)
            } else duplicate.add(move.second)
        }

        return moves.filter { !duplicate.contains(it.second) }.toMutableSet()
    }

    private fun printMap(elfCoordinates: MutableSet<Coordinate>) {
        val minX = elfCoordinates.map { it.x }.min()
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
        println()
    }
}

fun main() {
    Day23.solve()
}