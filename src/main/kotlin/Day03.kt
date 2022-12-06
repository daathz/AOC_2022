object Day03 : DayXX() {

    override fun part1() {
        val score = readInput("day03")
            .map { Pair(it.substring(0, it.length / 2), it.substring(it.length / 2, it.length)) }
            .map { Pair(it.first.toCharSet(), it.second.toCharSet()) }
            .map { it.first.intersect(it.second).first() }
            .sumOf { it.getValue() }

        println(score)
    }

    override fun part2() {
        val score = readInput("day03")
            .chunked(3)
            .map { it.map { s -> s.toCharSet() } }
            .map { it[0].intersect(it[1]).intersect(it[2]).first() }
            .sumOf { it.getValue() }

        println(score)
    }

    private fun String.toCharSet() = toCharArray().toSet()

    private fun Char.getValue(): Int =
        let {
            return if (it.isLowerCase()) it.code - 96 else it.code - 65 + 27
        }
}

fun main() {
    Day03.solve()
}