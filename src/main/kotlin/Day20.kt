object Day20 : DayXX() {
    const val DECRYPTION_KEY = 811589153

    override fun part1() {
        val encrypted = readInput("day20").map { it.toLong() * DECRYPTION_KEY }
            .mapIndexed { idx, value ->
                Seq(idx, value)
            }

        var decrypted = encrypted.toMutableList()

        repeat(10) {
            encrypted.forEach { value ->
                val idx = decrypted.indexOf(value)
                decrypted.removeAt(idx)
                decrypted.add((idx + value.value).mod(decrypted.size), value) // mod equals abs + %

                // println(decrypted.map { it.value }.joinToString())
            }
        }

        // println(decrypted.map { it.value }.joinToString())

        val sum = decrypted.map { it.value }.let {
            val idx = it.indexOf(0)
            it[(1000 + idx) % decrypted.size] + it[(2000 + idx) % decrypted.size] + it[(3000 + idx) % decrypted.size]
        }

        println(sum)
    }

    override fun part2() {
    }
}

fun main() {
    Day20.solve()
}

data class Seq(val index: Int, val value: Long)