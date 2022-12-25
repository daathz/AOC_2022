object Day25 : DayXX() {
    override fun part1() {
        val inputs = readInput("day25")

        val sum = inputs.sumOf {
            SNAFUToDecimal(it)
        }.toLong()

        println(sum)
        println(DecimalToSNAFU(sum))
    }

    override fun part2() {
    }

    private fun SNAFUToDecimal(number: String): Double {
        val size = number.length

        var result = 0.0
        for ((idx, n) in number.withIndex()) {
            if (n.isDigit()) {
                result += n.toString().toDouble() * Math.pow((5).toDouble(), (size - idx - 1).toDouble())
            } else if (n == '-') {
                result += (-1).toDouble() * Math.pow((5).toDouble(), (size - idx - 1).toDouble())
            } else {
                result += (-2).toDouble() * Math.pow((5).toDouble(), (size - idx - 1).toDouble())
            }
        }

        return result
    }

    private fun DecimalToBase5(number: Int): String {
        val numbers = mutableListOf<Int>()
        var value = number

        while (value != 0) {
            numbers.add(value % 5)
            value /= 5
        }

        return numbers.reversed().joinToString("")
    }

    private fun DecimalToSNAFU(number: Long): String {
        val numbers = mutableListOf<String>()
        var value = number

        while (value != 0L) {
            val divided = value % 5
            if (divided <= 2) {
                numbers.add(divided.toString())
            } else if (divided == 3L) {
                numbers.add("=")
                value += 2
            } else {
                numbers.add("-")
                value += 1
            }

            value /= 5
        }

        return numbers.reversed().joinToString("")
    }
}

fun main() {
    Day25.solve()
}