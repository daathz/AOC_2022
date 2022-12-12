data class Step(val c: Char, var distance: Int = -1)
object Day12 : DayXX() {
    override fun part1() {
        val map = readInput("day12").map {
            it.toCharArray().toList().map { c -> Step(c) }
        }

        goToGoal(map, 20, 0)

        println()
    }

    override fun part2() {
    }

    private fun goToGoal(map: List<List<Step>>, row: Int, col: Int, init: Int = 0) {
        val currentVal = if (map[row][col].c == 'S') 'a'.code else map[row][col].c.code + 1

        var steps = init

        if (map[row][col].distance > steps || map[row][col].distance == -1) {
            map[row][col].distance = steps
        }

        steps++

        if (col + 1 != map[row].size) {
            if (map[row][col + 1].c.code <= currentVal && shouldGo(map[row][col + 1], steps)) {
                if ((map[row][col + 1].c == 'E' && 'z'.code <= currentVal) || map[row][col + 1].c != 'E')
                    goToGoal(map, row, col + 1, steps)
            }
        }
        if (row + 1 != map.size) {
            if (map[row + 1][col].c.code <= currentVal && shouldGo(map[row + 1][col], steps)) {
                if ((map[row + 1][col].c == 'E'&& 'z'.code <= currentVal) || map[row + 1][col].c != 'E')
                    goToGoal(map, row + 1, col, steps)
            }
        }
        if (col >= 1) {
            if (map[row][col - 1].c.code <= currentVal && shouldGo(map[row][col - 1], steps)) {
                if ((map[row][col - 1].c == 'E'&& 'z'.code <= currentVal) || map[row][col - 1].c != 'E')
                    goToGoal(map, row, col - 1, steps)
            }
        }
        if (row >= 1) {
            if (map[row - 1][col].c.code <= currentVal && shouldGo(map[row - 1][col], steps)) {
                if ((map[row - 1][col].c == 'E'&& 'z'.code <= currentVal) || map[row - 1][col].c != 'E')
                    goToGoal(map, row - 1, col, steps)
            }
        }

        println(map[row][col])
    }

    private fun shouldGo(step: Step, steps: Int): Boolean = (step.distance > steps || step.distance == -1)
}

fun main() {
    Day12.solve()
}