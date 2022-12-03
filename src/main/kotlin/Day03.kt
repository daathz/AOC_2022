data class Backpack(val firstRucksack : String, val secondRucksack: String)
fun main() {
    var score = 0
    val inputs = readInput("day03")
    val backpacks = ArrayList<Backpack>()

    for (inputLine in inputs) {
        val inputLength = inputLine.length
        backpacks.add(Backpack(inputLine.substring(0, inputLength/2), inputLine.substring(inputLength/2, inputLength)))
    }

    for (backpack in backpacks) {
        val firstRucksack = backpack.firstRucksack.toCharArray().toSet()
        val secondRucksack = backpack.secondRucksack.toCharArray().toSet()

        for (c1 in firstRucksack) {
            for (c2 in secondRucksack) {
                if (c1 == c2) {
                    var value = c1.code
                    if (c1.isLowerCase()) {
                        value -= 96
                    } else {
                        value = value - 65 + 27
                    }
                    println(value)
                    score += value
                    break
                }
            }
        }
    }

    println(score)
}