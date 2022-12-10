object Day10 : DayXX() {

    private data class Command(val name: String, val value: Int = 0)

    private  data class Cycle(val start: Int, val end: Int = 0)

    override fun part1() {
        val commands = readInput("day10").map { it.split(" ") }
            .map {
                if (it[0] == "noop") Command(it[0], 0)
                else Command(it[0], it[1].toInt())
            }

        val xValues = mutableListOf<Cycle>()
        var xValue = 1

        for (command in commands) {
            if (command.name == "noop") {
                xValues.add(Cycle(xValue, xValue))
            } else {
                xValues.add(Cycle(xValue, xValue))
                val oldValue = xValue
                xValue += command.value
                xValues.add(Cycle(oldValue, xValue))
            }
        }

        val filteredValues = xValues.withIndex().filter {
            val myIndex = it.index + 1
            myIndex == 20 || myIndex == 60 || myIndex == 100 || myIndex == 140 || myIndex == 180 || myIndex == 220
        }

        filteredValues.forEach {
            println(it.index.toString() + ": " + it.value)
        }

        val sum = filteredValues.sumOf { (it.index + 1) * it.value.start}

        println(sum)
    }

    override fun part2() {
    }
}

fun main() {
    Day10.solve()
}