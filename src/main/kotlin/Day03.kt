object Day03 : DayXX() {
    private data class Backpack(val firstRucksack: String, val secondRucksack: String)

    override fun part1() {
        val score = readInput("day03").map {
            Backpack(it.substring(0, it.length / 2), it.substring(it.length / 2, it.length))
        }
            .map {
                it.firstRucksack.toCharSet()
                    .intersect(it.secondRucksack.toCharSet())
                    .first()
            }
            .sumOf { it.getValue() }

        println(score)
    }

    override fun part2() {
        val score = readInput("day03").windowed(size = 3, step = 3)
            .map {
                it[0].toCharSet()
                    .intersect(it[1].toCharSet())
                    .intersect(it[2].toCharSet())
                    .first()
            }.sumOf { it.getValue() }

        println(score)
    }

    private fun String.toCharSet() = toCharArray().toSet()

    private fun Char.getValue(): Int =
        let {
            if (it.isLowerCase()) {
                return it.code - 96
            } else {
                return it.code - 65 + 27
            }
        }
}

fun main() {
    Day03.solve()
}