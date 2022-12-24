object Day24 : DayXX() {

    override fun part1() {
        val (map, endPosition) = getMapAndEndPosition(readInput("day24"))

        val startPosition = 0 toY 1

        val (time, _) = moveFromStartToGoal(startPosition, endPosition, map)

        println(time)
    }

    override fun part2() {
        val (map, endPosition) = getMapAndEndPosition(readInput("day24"))

        val startPosition = 0 toY 1

        val (time1, map1) = moveFromStartToGoal(startPosition, endPosition, map)
        val (time2, map2) = moveFromStartToGoal(endPosition, startPosition, map1)
        val (time3, _) = moveFromStartToGoal(startPosition, endPosition, map2)

        println(time1 + time2 + time3)
    }

    private fun getMapAndEndPosition(input: List<String>): Pair<Set<Square>, Coordinate> {
        val mapWithAllElements = input.flatMapIndexed { lIndex, line ->
            line.mapIndexed { cIndex, c ->
                Square(lIndex toY cIndex, c)
            }
        }

        val map = mapWithAllElements.filter { it.type != '.' }.toSet()

        val goal = mapWithAllElements.last { it.type == '.' }.coordinate

        return map to goal
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

    private fun moveFromStartToGoal(start: Coordinate, goal: Coordinate, map: Set<Square>): Pair<Int, Set<Square>> {
        val maxX = map.maxOf { it.coordinate.x }
        val maxY = map.maxOf { it.coordinate.y }
        val directions = listOf(1 toY 0, 0 toY 1, -1 toY 0, 0 toY -1, 0 toY 0)

        var time = 0
        val currentPositions = mutableSetOf(start)
        var currentMap = map.map { it }.toSet()
        while (true) {
            if (currentPositions.contains(goal)) break

            time++

            currentMap = moveBlizzards(currentMap)
            val nextPositions = mutableSetOf<Coordinate>()
            for (position in currentPositions) {
                // println("$time $position")
                for (direction in directions) {
                    val newPos = position + direction
                    if (newPos.x >= 0 && newPos.y >= 0 && newPos.x <= maxX && newPos.y <= maxY) {
                        if (currentMap.find { it.coordinate == newPos } == null) {
                            nextPositions.add(newPos)
                        }
                    }
                }
            }
            currentPositions.clear()
            currentPositions.addAll(nextPositions)
        }

        return time to currentMap
    }

    private fun printMap(map: Set<Square>) {
        val maxX = map.maxOf { it.coordinate.x }
        val maxY = map.maxOf { it.coordinate.y }

        for (x in 0..maxX) {
            for (y in 0..maxY) {
                val squareCount = map.count { it.coordinate == x toY y }
                if (squareCount > 1) print(squareCount)
                else if (squareCount == 1) print(map.first { it.coordinate == x toY y }.type)
                else print('.')
            }
            println()
        }
        println()
    }
}

fun main() {
    Day24.solve()
}

data class Square(var coordinate: Coordinate, val type: Char)