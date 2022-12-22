import kotlin.math.abs

object Day22 : DayXX() {
    override fun part1() {
        val (mapCoordinates, instructions) = parse(readInput("day22"))

        var currentCoordinate = mapCoordinates.first { it.type != ' ' }
        var facing = ">"

        for ((idx, instruction) in instructions.withIndex()) {
            val (newCoordinate, newFacing) = doInstruction(mapCoordinates, instruction, currentCoordinate, facing)
            currentCoordinate = newCoordinate
            facing = newFacing
        }

        val facingNumber = when (facing) {
            ">" -> 0
            "v" -> 1
            "<" -> 2
            "^" -> 3
            else -> -1
        }

        println(currentCoordinate.x)
        println(currentCoordinate.y)
        println(facing)
        println(1000 * (currentCoordinate.x + 1) + 4 * (currentCoordinate.y + 1) + facingNumber)
    }

    override fun part2() {
    }

    private fun parse(input: List<String>): Pair<Set<MapCoordinate>, List<String>> {
        val mapString = input.takeWhile { it != "" }
        val instructionsString = input.last()

        val map = mutableSetOf<MapCoordinate>()
        mapString.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { cIndex, c ->
                map.add(MapCoordinate(lineIndex, cIndex, c))
            }
        }

        val instructions = mutableListOf<String>()
        var numBuffer = ""
        instructionsString.forEachIndexed { idx, c ->
            if (idx == instructionsString.length - 1 && c.isDigit()) {
                numBuffer += c
                instructions.add(numBuffer)
            }
            if (c.isDigit()) numBuffer += c
            else {
                instructions.add(numBuffer)
                numBuffer = ""
                instructions.add(c.toString())
            }
        }

        return map.toSet() to instructions.toList()
    }

    private fun doInstruction(
        map: Set<MapCoordinate>,
        instruction: String,
        currentPosition: MapCoordinate,
        facing: String
    ): Pair<MapCoordinate, String> {
        if (!instruction[0].isDigit()) {
            var newFacing: String

            if (instruction == "R") {
                newFacing = when (facing) {
                    ">" -> "v"
                    "v" -> "<"
                    "<" -> "^"
                    "^" -> ">"
                    else -> facing
                }
            } else {
                newFacing = when (facing) {
                    ">" -> "^"
                    "v" -> ">"
                    "<" -> "v"
                    "^" -> "<"
                    else -> facing
                }
            }

            return currentPosition to newFacing
        }

        val stepCount = instruction.toInt()

        var newPosition = currentPosition
        var prevFacing = facing
        var newFacing = facing
        for (i in 0 until stepCount) {
            when (newFacing) {
                ">" -> {
                    var newPos = map.find { it.x == newPosition.x && it.y == newPosition.y + 1 }
                    if (newPos != null && newPos.type != ' ') {
                        if (newPos.type == '#') break
                        else if (newPos.type == '.') newPosition = newPos
                    } else {
                        if (newPosition.x < 50 && newPosition.y >= 100) { // 2 -> 5 right
                            newPos = map.first { it.x == abs(newPosition.x - 49) + 100 && it.y == 99 }
                            prevFacing = newFacing
                            newFacing = "<"
                        } else if (newPosition.x in 50..99 && newPosition.y in 50..99) { // 3 -> 2 down
                            newPos = map.first { it.x == 49 && it.y == newPosition.x + 50 }
                            prevFacing = newFacing
                            newFacing = "^"
                        } else if (newPosition.x in 100..149 && newPosition.y in 50..99) { // 5 -> 2 right
                            newPos = map.first { it.y == 149 && it.x == abs(49 - (newPosition.x - 100))}
                            prevFacing = newFacing
                            newFacing = "<"
                        } else if (newPosition.x >= 150 && newPosition.y < 50) { // 6 -> 5 down
                            newPos = map.first { it.x == 149 && it.y == newPosition.x - 100 }
                            prevFacing = newFacing
                            newFacing = "^"
                        } else {
                            println("baj van <")
                        }

                        if (newPos?.type == '#') {
                            newFacing = prevFacing
                            break
                        } else newPosition = newPos!!
                    }
                }

                "<" -> {
                    var newPos = map.find { it.x == newPosition.x && it.y == newPosition.y - 1 }
                    if (newPos != null && newPos.type != ' ') {
                        if (newPos.type == '#') break
                        else if (newPos.type == '.') newPosition = newPos
                    } else {
                        if (newPosition.x < 50 && newPosition.y in 50..99) { // 1 -> 4 left
                            newPos = map.first { it.y == 0 && it.x == abs(49 - newPosition.x) + 100 }
                            prevFacing = newFacing
                            newFacing = ">"
                        } else if (newPosition.x in 50..99 && newPosition.y in 50..99) { // 3 -> 4 up
                            newPos = map.first { it.y == newPosition.x - 50 && it.x == 100 }
                            prevFacing = newFacing
                            newFacing = "v"
                        } else if (newPosition.x in 100..149 && newPosition.y < 50) { // 4 -> 1 left
                            newPos = map.first { it.y == 50 && it.x == 49 - (newPosition.x  - 100) }
                            prevFacing = newFacing
                            newFacing = ">"
                        } else if (newPosition.x >= 150 && newPosition.y < 50) { // 6 -> 1 up
                            newPos = map.first { it.x == 0 && newPosition.x - 100 == it.y }
                            prevFacing = newFacing
                            newFacing = "v"
                        } else {
                            println("baj van <")
                        }

                        if (newPos?.type == '#') {
                            newFacing = prevFacing
                            break
                        } else newPosition = newPos!!
                    }
                }

                "^" -> {
                    var newPos = map.find { it.x == newPosition.x - 1 && it.y == newPosition.y }
                    if (newPos != null && newPos.type != ' ') {
                        if (newPos.type == '#') break
                        else if (newPos.type == '.') newPosition = newPos
                    } else {
                        if (newPosition.x < 50 && newPosition.y in 50..99) { // 1 -> 6 left
                            newPos = map.first { it.y == 0 && it.x == newPosition.y + 100 }
                            prevFacing = newFacing
                            newFacing = ">"
                        } else if (newPosition.x < 50 && newPosition.y >= 100) { // 2 -> 6 down
                            newPos = map.first { it.x == 199 && it.y == newPosition.y - 100 }
                            prevFacing = newFacing
                            newFacing = "^"
                        } else if (newPosition.x in 100..149 && newPosition.y < 50) { // 4 -> 3 left
                            newPos = map.first { it.y == 50 && it.x == newPosition.y + 50 }
                            prevFacing = newFacing
                            newFacing = ">"
                        }else {
                            println("baj van ^")
                        }

                        if (newPos?.type == '#') {
                            newFacing = prevFacing
                            break
                        } else newPosition = newPos!!
                    }
                }

                "v" -> {
                    var newPos = map.find { it.x == newPosition.x + 1 && it.y == newPosition.y }
                    if (newPos != null && newPos.type != ' ') {
                        if (newPos.type == '#') break
                        else if (newPos.type == '.') newPosition = newPos
                    } else {
                        if (newPosition.x < 50 && newPosition.y >= 100) { // 2 -> 3 right
                            newPos = map.first { it.y == 99 && it.x == newPosition.y - 50 }
                            newFacing = "<"
                        } else if (newPosition.x in 100..149 && newPosition.y in 50..99) { // 5 -> 6 right
                            newPos = map.first { it.y == 49 && it.x == newPosition.y + 100 }
                            newFacing = "<"
                        } else if (newPosition.x >= 150 && newPosition.y < 50) { // 6 -> 2 up
                            newPos = map.first { it.x == 0 && it.y == newPosition.y + 100 }
                            newFacing = "v"
                        }else {
                            println("baj van v")
                        }

                        if (newPos?.type == '#') {
                            newFacing = facing
                            break
                        } else newPosition = newPos!!
                    }
                }
            }
        }

        return newPosition to newFacing
    }
}

fun main() {
    Day22.solve()
}

data class MapCoordinate(val x: Int, val y: Int, val type: Char)