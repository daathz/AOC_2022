object Day20 : DayXX() {
    private const val DECRYPTION_KEY = 811589153

    override fun part1() {
        val encrypted = getEncrypted(readInput("day20"))

        val decrypted = encrypted.toMutableList()

        mix(encrypted, decrypted)

        // println(decrypted.map { it.value }.joinToString())

        println(getCoordinates(decrypted))
    }

    override fun part2() {
        val encrypted = getEncrypted(readInput("day20"), DECRYPTION_KEY)

        val decrypted = encrypted.toMutableList()

        mix(encrypted, decrypted, 10)

        // println(decrypted.map { it.value }.joinToString())

        println(getCoordinates(decrypted))
    }

    private fun getEncrypted(input: List<String>, encryptionKey: Int = 1): List<Seq> = input
        .map { it.toLong() * encryptionKey }
        .mapIndexed { idx, value ->
            Seq(idx, value)
        }

    private fun mix(encrypted: List<Seq>, decrypted: MutableList<Seq>, n: Int = 1): List<Seq> {
        repeat(n) {
            encrypted.forEach { seq ->
                val idx = decrypted.indexOf(seq)
                decrypted.removeAt(idx)
                decrypted.add((idx + seq.value).mod(decrypted.size), seq) // mod equals abs + %

                // println(decrypted.map { it.value }.joinToString())
            }
        }

        return decrypted
    }

    private fun getCoordinates(decrypted: List<Seq>): Long {
        val decryptedValues = decrypted.map { it.value }

        val indices = getIndices(decryptedValues.indexOf(0), decrypted.size)

        return decryptedValues.filterIndexed { idx, _ -> indices.contains(idx) }.sum()
    }

    private fun getIndices(indexOf0: Int, decryptedSize: Int) = listOf(
        (indexOf0 + 1000) % decryptedSize,
        (indexOf0 + 2000) % decryptedSize,
        (indexOf0 + 3000) % decryptedSize
    )
}

fun main() {
    Day20.solve()
}

data class Seq(val index: Int, val value: Long)