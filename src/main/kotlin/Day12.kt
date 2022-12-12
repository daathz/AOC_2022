typealias ElevationMap = List<List<Elevation>>

data class Elevation(val c: Char, val value: Int, val rowIndex: Int, val columnIndex: Int, var distance: Int = -1)
object Day12 : DayXX() {
    override fun part1() {
        val map = getMap(readInput("day12"))

        map.flatten()
            .first { step -> step.c == 'S' }
            .let { step -> setDistanceFrom(map, step.rowIndex, step.columnIndex) }

        println(map.getDistanceOfE())
    }

    override fun part2() {
        val map = getMap(readInput("day12"))

        val minDistanceToE = map.flatten()
            .filter { step -> step.c == 'a' || step.c == 'S' }
            .map { step ->
                val newMap = getMap(readInput("day12"))
                setDistanceFrom(newMap, step.rowIndex, step.columnIndex)

                newMap.getDistanceOfE()
            }
            .filter { distance -> distance != -1 }
            .min()

        println(minDistanceToE)
    }

    private fun setDistanceFrom(map: ElevationMap, row: Int, col: Int, init: Int = 0) {
        val currentVal = map[row][col].value + 1

        var steps = init

        if (map[row][col].distance > steps || map[row][col].distance == -1) {
            map[row][col].distance = steps
        }

        steps++

        if (col + 1 != map[row].size) {
            if (canStep(map[row][col + 1], currentVal, steps)) {
                setDistanceFrom(map, row, col + 1, steps)
            }
        }
        if (row + 1 != map.size) {
            if (canStep(map[row + 1][col], currentVal, steps)) {
                setDistanceFrom(map, row + 1, col, steps)
            }
        }
        if (col >= 1) {
            if (canStep(map[row][col - 1], currentVal, steps)) {
                setDistanceFrom(map, row, col - 1, steps)
            }
        }
        if (row >= 1) {
            if (canStep(map[row - 1][col], currentVal, steps)) {
                setDistanceFrom(map, row - 1, col, steps)
            }
        }
    }

    private fun canStep(elevation: Elevation, currentVal: Int, steps: Int): Boolean =
        (elevation.value <= currentVal) && (elevation.distance > steps || elevation.distance == -1)

    private fun getMap(input: List<String>): ElevationMap = input.mapIndexed { lineIndex, line ->
        line.toCharArray().toList().mapIndexed { charIndex, char ->
            when (char) {
                'S' -> Elevation(char, 'a'.code, lineIndex, charIndex)
                'E' -> Elevation(char, 'z'.code, lineIndex, charIndex)
                else -> Elevation(char, char.code, lineIndex, charIndex)
            }
        }
    }

    private fun ElevationMap.getDistanceOfE() = flatten().first { step -> step.c == 'E' }.distance
}

fun main() {
    Day12.solve()
}