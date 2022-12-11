typealias Monkeys = List<Monkey>

data class Monkey(
    val id: Int,
    val items: MutableList<Long>,
    val operation: List<String>,
    val test: Int,
    val trueMonkeyNumber: Int,
    val falseMonkeyNumber: Int,
    var inspectionCount: Long = 0
)

object Day11 : DayXX() {
    override fun part1() {
        val monkeys = getMonkeys(readInput("day11"))

        monkeys.doRounds(20) { it / 3 }

        println(monkeys.getMonkeyBusiness())
    }

    override fun part2() {
        val monkeys = getMonkeys(readInput("day11"))

        val modulo = monkeys.map { it.test }.reduce { acc, i -> acc * i }

        monkeys.doRounds(10000) { it % modulo }

        println(monkeys.getMonkeyBusiness())
    }

    private fun getMonkeys(input: List<String>): Monkeys = input.filter { it != "" }
        .chunked(6)
        .map {
            val monkeyId = it[0][7].toNumber()
            val items = it[1].split(": ")[1].split(", ").map { i -> i.toLong() }
            val operation = it[2].split("new = ")[1].split(" ")
            val test = it[3].split("by ")[1].toInt()
            val trueMonkeyNumber = it[4].last().toNumber()
            val falseMonkeyNumber = it[5].last().toNumber()

            Monkey(monkeyId, items.toMutableList(), operation, test, trueMonkeyNumber, falseMonkeyNumber)
        }

    private fun Monkeys.doRounds(n: Int, divisor: (l: Long) -> Long) = repeat(n) {
        forEach { monkey ->
            monkey.items.forEachIndexed { index, item ->
                when {
                    monkey.operation[1] == "*" -> {
                        monkey.items[index] *=
                            if (monkey.operation[2] == "old") item
                            else monkey.operation[2].toLong()
                    }
                    monkey.operation[1] == "+" -> {
                        monkey.items[index] +=
                            if (monkey.operation[2] == "old") item
                            else monkey.operation[2].toLong()
                    }
                }

                monkey.items[index] = divisor(monkey.items[index]) // Reducing stress levels

                monkey.inspectionCount++
            }

            monkey.items.forEach { item ->
                if (item % monkey.test == 0L) {
                    this[monkey.trueMonkeyNumber].items.add(item)
                } else {
                    this[monkey.falseMonkeyNumber].items.add(item)
                }
            }

            monkey.items.clear()
        }

    }
    private fun Monkeys.getMonkeyBusiness(): Long = map { it.inspectionCount }
        .sorted()
        .takeLast(2)
        .reduce { acc, i -> acc * i }
}

fun main() {
    Day11.solve()
}