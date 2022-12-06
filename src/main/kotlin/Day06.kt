object Day06 : DayXX() {
    override fun part1() {
        findMarkersIndex(readInput("day06")[0], 4)
    }

    override fun part2() {
        findMarkersIndex(readInput("day06")[0], 14)
    }

    private fun findMarkersIndex(input: String, length: Int) {
        val marker = input.windowed(size = length, step = 1)
            .map { it.toSet() }
            .first { it.size == length }
            .fold(StringBuilder(length)) { sb, c ->
                sb.append(c)
            }.toString()

        println(input.indexOf(marker) + length)
    }
}

fun main() {
    Day06.solve()
}