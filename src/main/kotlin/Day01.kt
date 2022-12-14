object Day01 : DayXX() {
    override fun part1() {
        val sortedSums = getSortedSums(readInput("day01"))
        println(sortedSums.max())
    }

    override fun part2() {
        val sortedSums = getSortedSums(readInput("day01"))
        println(sortedSums.takeLast(3).sum())
    }

    private fun getSortedSums(input: List<String>): List<Int> {
        val sums = ArrayList<Int>()

        var temp = 0
        input.map(String::toInt).forEach {
            if (it > 0) {
                temp += it
            } else {
                sums.add(temp)
                temp = 0
            }
        }

        return sums.sorted()
    }
}

fun main() {
    Day01.solve()
}