data class Backpack(val firstRucksack : String, val secondRucksack: String)

data class Group(val firstBackpack: String, val secondBackpack: String, val thirdBackpacks: String)

fun part1() {
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
                    // println(value)
                    score += value
                    break
                }
            }
        }
    }

    println(score)
}

fun part2() {
    var score = 0
    val inputs = readInput("day03")
    val groups = ArrayList<Group>()

    for (i in 0..inputs.size - 3 step 3) {
        groups.add(Group(inputs[i], inputs[i + 1], inputs[i + 2]))
    }

    for (group in groups) {
        val firstBackpack = group.firstBackpack.toCharArray().toSet()
        val secondBackpack = group.secondBackpack.toCharArray().toSet()
        val thirdBackpacks = group.thirdBackpacks.toCharArray().toSet()

        val intersect = firstBackpack.intersect(secondBackpack).intersect(thirdBackpacks)
        val c = intersect.stream().findAny().get()
        val value : Int = if (c.isLowerCase()) {
            c.code - 96
        } else {
            c.code - 65 + 27
        }

        score += value
    }

    println(score)
}

fun main() {
    part2()
}