object Day04 : DayXX() {

    private data class Room(val first: HashSet<Int>, val second: HashSet<Int>)

    override fun part1() {
        val count = createRoomList(readInput("day04")).count {
            val intersectSize = it.first.intersect(it.second).size

            intersectSize == it.first.size || intersectSize == it.second.size
        }

        println(count)
    }

    override fun part2() {
        val count = createRoomList(readInput("day04")).count {
            it.first.intersect(it.second).isNotEmpty()
        }

        println(count)
    }

    private fun createRoomList(input: List<String>): List<Room> {
        return input.map { it.split(",") }
            .map { Pair(it[0], it[1]) }
            .map {
                val firstRangeArray = it.first.split("-")
                val secondRangeArray = it.second.split("-")

                val firstRange = firstRangeArray[0].toInt()..firstRangeArray[1].toInt()
                val secondRange = secondRangeArray[0].toInt()..secondRangeArray[1].toInt()

                Pair(firstRange, secondRange)
            }
            .map {
                val firstSet = HashSet<Int>()
                val secondSet = HashSet<Int>()

                it.first.forEach(firstSet::add)
                it.second.forEach(secondSet::add)

                Room(firstSet, secondSet)
            }
    }
}

fun main() {
    Day04.solve()
}