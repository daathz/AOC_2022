object Day11 : DayXX() {

    private data class Monkey(
        val id: Int,
        val items: MutableList<Long>,
        val operation: List<String>,
        val test: Int,
        val trueMonkeyNumber: Int,
        val falseMonkeyNumber: Int,
        var inspectionCount: Long = 0
    )
    override fun part1() {
        val monkeys = mutableListOf<Monkey>()

        readInput("day11").filter { it != "" }
            .chunked(6)
            .forEach {
                val monkeyId = it[0][7].toString().toInt()
                val items = it[1].split(": ")[1].split(", ").map { i -> i.toLong() }
                val operation = it[2].split("new = ")[1].split(" ")
                val test = it[3].split("by ")[1].toInt()
                val trueMonkeyNumber = it[4].last().toString().toInt()
                val falseMonkeyNumber = it[5].last().toString().toInt()

                val monkey = Monkey(monkeyId, items.toMutableList(), operation, test, trueMonkeyNumber, falseMonkeyNumber)
                monkeys.add(monkey)
            }

        val modulo = monkeys.map { it.test }.reduce { acc, i -> acc * i }

        for (i in 0 until 10000) {
            monkeys.forEach { monkey ->
                monkey.items.forEachIndexed { index, item ->
                    if (monkey.operation[1] == "*") {
                        if (monkey.operation[2] != "old") {
                            monkey.items[index] = item * monkey.operation[2].toLong()
                        } else {
                            monkey.items[index] *= item
                        }
                    } else if (monkey.operation[1] == "+") {
                        if (monkey.operation[2] != "old") {
                            monkey.items[index] += monkey.operation[2].toLong()
                        } else {
                            monkey.items[index] += item
                        }
                    }

                    monkey.items[index] = monkey.items[index] % modulo

                    monkey.inspectionCount++
                }

                monkey.items.forEach { item ->
                    if (item % monkey.test == 0L) {
                        monkeys[monkey.trueMonkeyNumber].items.add(item)
                    } else {
                        monkeys[monkey.falseMonkeyNumber].items.add(item)
                    }
                }

                monkey.items.clear()
            }

            if (i % 1000 == 0) {
                monkeys.forEach { monkey ->
                    println(monkey.inspectionCount)
                }
                println()
            }
        }

        val count = monkeys.map {
            it.inspectionCount
        }.sorted().reversed().take(2).fold(1L) { acc, i -> acc * i }

        println(count)
    }

    override fun part2() {
    }
}

fun main() {
    Day11.solve()
}