import kotlin.math.pow

object Day25 : DayXX() {
    override fun part1() {
        val sum = readInput("day25").sumOf { snafu ->
            snafuToDecimal(snafu)
        }

        println(sum)
        println(decimalToSnafu(sum))
    }

    override fun part2() {
    }

    private fun snafuToDecimal(snafu: String): Long = snafu.mapIndexed { index, n ->
        val number = when (n) {
            '0' -> 0
            '1' -> 1
            '2' -> 2
            '-' -> -1
            '=' -> -2
            else -> throw RuntimeException()
        }

        number * (5.0).pow((snafu.length - index - 1.0))
    }.sum().toLong()

    private fun decimalToSnafu(decimal: Long): String {
        val numbers = mutableListOf<String>()
        var remaining = decimal

        while (remaining != 0L) {
            val divided = remaining % 5

            when {
                divided <= 2 -> {
                    numbers.add(divided.toString())
                }
                divided == 3L -> {
                    numbers.add("=")
                    remaining += 2
                }
                else -> {
                    numbers.add("-")
                    remaining += 1
                }
            }

            remaining /= 5
        }

        return numbers.reversed().joinToString("")
    }
}

fun main() {
    Day25.solve()
}