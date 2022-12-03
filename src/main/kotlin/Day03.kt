object Day03 : DayXX() {
    private data class Backpack(val firstRucksack : String, val secondRucksack: String)

    private data class Group(val firstBackpack: String, val secondBackpack: String, val thirdBackpacks: String)

     override fun part1() {
        var score = 0
        val inputs = readInput("day03")
        val backpacks = ArrayList<Backpack>()

        for (inputLine in inputs) {
            val inputLength = inputLine.length
            backpacks.add(Backpack(inputLine.substring(0, inputLength/2), inputLine.substring(inputLength/2, inputLength)))
        }

        for (backpack in backpacks) {
            val firstRucksack = backpack.firstRucksack.toCharSet()
            val secondRucksack = backpack.secondRucksack.toCharSet()


            val intersect = firstRucksack.intersect(secondRucksack)

            score += intersect.first().getValue()
        }

        println(score)
    }

    override fun part2() {
        var score = 0
        val inputs = readInput("day03")
        val groups = ArrayList<Group>()

        for (i in 0..inputs.size - 3 step 3) {
            groups.add(Group(inputs[i], inputs[i + 1], inputs[i + 2]))
        }

        for (group in groups) {
            val firstBackpack = group.firstBackpack.toCharSet()
            val secondBackpack = group.secondBackpack.toCharSet()
            val thirdBackpacks = group.thirdBackpacks.toCharSet()

            val intersect = firstBackpack.intersect(secondBackpack).intersect(thirdBackpacks)

            score += intersect.first().getValue()
        }

        println(score)
    }

    private fun String.toCharSet() = toCharArray().toSet()

    private fun Char.getValue(): Int =
        let {
            if (it.isLowerCase()) {
                return it.code - 96
            } else {
                return it.code - 65 + 27
            }
        }
}

fun main() {
    Day03.solve()
}