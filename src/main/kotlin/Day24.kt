object Day24 : DayXX() {
    override fun part1() {
        var initMap = mutableSetOf<Square>()
        readInput("day24").forEachIndexed { lIndex,line ->
            line.forEachIndexed { cIndex, c ->
                initMap.add(Square(lIndex toY cIndex,  c))
            }
        }

        val goal = initMap.last { it.type == '.' }.coordinate

        initMap = initMap.filter { it.type != '.' }.toMutableSet()

        var time = 0
        // var deque = ArrayDeque<BlizzardState>()
        val maxX = initMap.maxOf { it.coordinate.x }
        val maxY = initMap.maxOf { it.coordinate.y }
        val start = 0 toY 1
        // deque.add(BlizzardState(initMap.toSet(), 0 toY 1, 0))

        /*for (i in 0 .. 18) {
            println("*** $i ***")
            for (x in 0..maxX) {
                for (y in 0..maxY) {
                    val squareCount = initMap.count { it.coordinate == x toY y }
                    if (squareCount > 1) print(squareCount)
                    else if (squareCount == 1) print(initMap.first { it.coordinate == x toY y }.type)
                    else print('.')
                }
                println()
            }
            initMap = moveBlizzards(initMap) as MutableSet<Square>
            println()
        }*/


        /*while (deque.isNotEmpty()) {
            val (map, myCoordinate, minute) = deque.removeFirst()
            println("$myCoordinate $minute")

            if (myCoordinate == goal) {
                time = minute
                break
            }

            val newMap = moveBlizzards(map)

            val directions = listOf(1 toY 0, 0 toY 1, -1 toY 0, 0 toY -1, 0 toY 0)

            for (direction in directions) {
                val nextPos = myCoordinate + direction
                if (nextPos.x >= 0 && nextPos.y >= 0 && nextPos.x <= maxX && nextPos.y <= maxY) {
                    if (newMap.find { it.coordinate == nextPos } == null) {
                        deque.add(BlizzardState(newMap, nextPos, minute + 1))
                    }
                }
            }
        }*/

        val currentPositions = mutableSetOf(start)
        val directions = listOf(1 toY 0, 0 toY 1, -1 toY 0, 0 toY -1, 0 toY 0)
        while (true) {
            if (currentPositions.contains(goal)) break

            time++

            val newMap = moveBlizzards(initMap)
            val nextPositions = mutableSetOf<Coordinate>()
            for (position in currentPositions) {
                for (direction in directions) {
                    val newPos = position + direction
                    if (newPos.x >= 0 && newPos.y >= 0 && newPos.x <= maxX && newPos.y <= maxY) {
                        if (newMap.find { it.coordinate == newPos } == null) {
                            nextPositions.add(newPos)
                        }
                    }
                }
            }
            currentPositions.clear()
            currentPositions.addAll(nextPositions)
        }

        println(time)
    }

    override fun part2() {
    }

    private fun moveBlizzards(map: Set<Square>): Set<Square> {
        val maxX = map.maxOf { it.coordinate.x }
        val maxY = map.maxOf { it.coordinate.y }
        val dirs = mapOf('>' to (0 toY 1), '<' to (0 toY -1), '^' to (-1 toY 0), 'v' to (1 toY 0))
        map.filter { it.type != '#' }.forEach { blizzard ->
            var nextPos = blizzard.coordinate + dirs[blizzard.type]!!
            if (nextPos.x <= 0 || nextPos.y <= 0 || nextPos.x >= maxX || nextPos.y >= maxY) {
                nextPos = when (blizzard.type) {
                    '>' -> map.first { it.type == '#' && it.coordinate.x == nextPos.x }.coordinate + (0 toY 1)
                    '<' -> map.last { it.type == '#' && it.coordinate.x == nextPos.x }.coordinate + (0 toY -1)
                    '^' -> map.last { it.type == '#' && it.coordinate.y == nextPos.y }.coordinate + (-1 toY 0)
                    'v' -> map.first { it.type == '#' && it.coordinate.y == nextPos.y }.coordinate + (1 toY 0)
                    else -> nextPos
                }
            }

            blizzard.coordinate = nextPos
        }

        val result = mutableSetOf<Square>()
        map.forEach { square -> result.add(square.copy()) }
        return result.toSet()
    }
}

fun main() {
    Day24.solve()
}

data class Square(var coordinate: Coordinate, val type: Char)

data class BlizzardState(val map: Set<Square>, val myCoordinate: Coordinate, val minute: Int)