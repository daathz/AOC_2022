import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.int
import kotlin.math.min

object Day13 : DayXX() {

    private const val DIVIDER_PACKET_1 = "[[2]]"
    private const val DIVIDER_PACKET_2 = "[[6]]"

    override fun part1() {
        val sum = readInput("day13").filter { it != "" }
            .map { Json.decodeFromString<JsonArray>(it) }
            .chunked(2).map { compareTo(it[0], it[1]) }
            .mapIndexed { index, b ->
                if (b!!) index + 1
                else 0
            }.sum()

        println(sum)
    }

    override fun part2() {
        val packets = readInput("day13").filter { it != "" }
            .map { Pair(it, Json.decodeFromString<JsonArray>(it)) }
            .toMutableList()

        packets.add(Pair(DIVIDER_PACKET_1, Json.decodeFromString(DIVIDER_PACKET_1)))
        packets.add(Pair(DIVIDER_PACKET_2, Json.decodeFromString(DIVIDER_PACKET_2)))

        packets.sortWith { pair1, pair2 ->
            when (compareTo(pair1.second, pair2.second)!!) {
                true -> -1
                false -> 1
            }
        }

        val decoderKey = packets
            .mapIndexed { index, packet -> Triple(index + 1, packet.first, packet.second) }
            .filter { packet -> packet.second == DIVIDER_PACKET_1 || packet.second == DIVIDER_PACKET_2 }
            .fold(1) { acc, triple -> acc * triple.first }

        println(decoderKey)
    }

    private fun compareTo(left: JsonArray, right: JsonArray): Boolean? {
        for (i in 0 until min(left.size, right.size)) {
            val leftItem = left[i]
            val rightItem = right[i]

            when {
                leftItem is JsonPrimitive && rightItem is JsonPrimitive -> {
                    if (leftItem == rightItem) continue

                    return leftItem.int < rightItem.int
                }
                else -> {
                    val leftList = if (leftItem is JsonArray) leftItem else JsonArray(listOf(leftItem))
                    val rightList = if (rightItem is JsonArray) rightItem else JsonArray(listOf(rightItem))

                    val compareToResult = compareTo(leftList, rightList)

                    if (compareToResult != null) return compareToResult
                }
            }

        }

        if (left.size == right.size) return null

        return  left.size < right.size
    }
}

fun main() {
    Day13.solve()
}