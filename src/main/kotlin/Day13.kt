import kotlin.math.min

object Day13 : DayXX() {
    override fun part1() {
        val sum = readInput("day13").chunked(3)
            .map { Pair(parseLine(it[0].iterator()), parseLine(it[1].iterator())) }
            .map { compareTo(it.first, it.second) }
            .mapIndexed { index, b ->
                if (b!!) index + 1
                else 0
            }.sum()

        println(sum)
    }

    override fun part2() {
        val packets = readInput("day13").filter { it != "" }
            .map { Pair(it, parseLine(it.iterator())) }.toMutableList()

        packets.add(Pair("[[2]]", parseLine("[[2]]".iterator())))
        packets.add(Pair("[[6]]", parseLine("[[6]]".iterator())))

        packets.sortWith { pair1, pair2 ->
            when (compareTo(pair1.second, pair2.second)!!) {
                true -> -1
                false -> 1
            }
        }

        val decoderKey = packets
            .mapIndexed { index, packet -> Triple(index + 1, packet.first, packet.second) }
            .filter { packet -> packet.second == "[[2]]" || packet.second == "[[6]]" }
            .fold(1) { acc, triple -> acc * triple.first }

        println(decoderKey)
    }

    private fun parseLine(lineIterator: CharIterator): List<Any> {
        val list = mutableListOf<Any>()

        while (lineIterator.hasNext()) {
            var num = ""
            var char = lineIterator.next()

            when (char) {
                '[' -> list.add(parseLine(lineIterator))
                ']' -> return list
                in '0'..'9' -> {
                    while (char.isDigit()) {
                        num += char
                        char = lineIterator.next()
                    }
                    list.add(num.toInt())
                }
            }
        }

        return list
    }

    private fun compareTo(left: List<Any>, right: List<Any>): Boolean? {
        for (i in 0 until min(left.size, right.size)) {
            val leftItem = left[i]
            val rightItem = right[i]

            if (leftItem is Int && rightItem is Int) {
                if (leftItem == rightItem) continue
                else return leftItem < rightItem
            }

            val leftList = if (leftItem is List<*>) leftItem else listOf(leftItem)
            val rightList = if (rightItem is List<*>) rightItem else listOf(rightItem)

            val compareToResult = compareTo(leftList as List<Any>, rightList as List<Any>)

            if (compareToResult != null) return compareToResult
        }

        if (left.size == right.size) return null

        return  left.size < right.size
    }
}

fun main() {
    Day13.solve()
}