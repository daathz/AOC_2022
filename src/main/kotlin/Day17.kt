data class Shape(val height: Int, val type: Char) {
    private var memberCoordinates = mutableListOf<Coordinate>()

    init {
        when (type) {
            '-' -> {
                memberCoordinates.add(Coordinate(height, 2))
                memberCoordinates.add(Coordinate(height, 3))
                memberCoordinates.add(Coordinate(height, 4))
                memberCoordinates.add(Coordinate(height, 5))
            }

            '+' -> {
                memberCoordinates.add(Coordinate(height, 3))
                memberCoordinates.add(Coordinate(height + 1, 2))
                memberCoordinates.add(Coordinate(height + 1, 3))
                memberCoordinates.add(Coordinate(height + 1, 4))
                memberCoordinates.add(Coordinate(height + 2, 3))
            }

            'L' -> {
                memberCoordinates.add(Coordinate(height, 2))
                memberCoordinates.add(Coordinate(height, 3))
                memberCoordinates.add(Coordinate(height, 4))
                memberCoordinates.add(Coordinate(height + 1, 4))
                memberCoordinates.add(Coordinate(height + 2, 4))
            }
            'I' -> {
                memberCoordinates.add(Coordinate(height, 2))
                memberCoordinates.add(Coordinate(height + 1, 2))
                memberCoordinates.add(Coordinate(height + 2, 2))
                memberCoordinates.add(Coordinate(height + 3, 2))
            }
            '█' -> {
                memberCoordinates.add(Coordinate(height, 2))
                memberCoordinates.add(Coordinate(height, 3))
                memberCoordinates.add(Coordinate(height + 1, 2))
                memberCoordinates.add(Coordinate(height + 1, 3))
            }
        }
    }

    fun move(direction: Char, fallenRockCoordinates: Set<Coordinate>) {
        when (direction) {
            '<' -> {
                if (!memberCoordinates.map { it.y }.contains(0)) {
                    val tempCoordinates = memberCoordinates.map { Coordinate(it.x, it.y - 1) }.toMutableList()
                    tempCoordinates.forEach { if (fallenRockCoordinates.contains(it)) return }
                    memberCoordinates = tempCoordinates
                }
            }
            '>' -> {
                if (!memberCoordinates.map { it.y }.contains(6)) {
                    val tempCoordinates = memberCoordinates.map { Coordinate(it.x, it.y + 1) }.toMutableList()
                    tempCoordinates.forEach { if (fallenRockCoordinates.contains(it)) return }
                    memberCoordinates = tempCoordinates
                }
            }
        }
    }

    fun fall(fallenRockCoordinates: Set<Coordinate>): Boolean {
        memberCoordinates.forEach { member ->
            val oneBelow = Coordinate(member.x - 1, member.y)
            if (fallenRockCoordinates.contains(oneBelow) || member.x - 1 < 0) {
                return false
            }
        }

        memberCoordinates = memberCoordinates.map { Coordinate(it.x - 1, it.y) }.toMutableList()
        return true
    }

    fun addToFallenRockCoordinates(fallenRockCoordinates: MutableSet<Coordinate>) {
        memberCoordinates.forEach { fallenRockCoordinates.add(it) }
    }
}

object Day17 : DayXX() {
    override fun part1() {
        val input = readInput("day17").first()
        var inputIterator = input.iterator()
        val shapes = listOf('-', '+', 'L', 'I', '█')
        val fallenRockCoordinates = mutableSetOf<Coordinate>()
        var currentShapeNumber = 0
        var height = -1
        var rockCount = 0

        while (rockCount < 2022) {
            // println(rockCount)
            // println(height)
            val currentRock = Shape(height + 4, shapes[currentShapeNumber])
            while (true) {
                val direction =
                    if (inputIterator.hasNext()) inputIterator.next()
                    else {
                        inputIterator = input.iterator()
                        inputIterator.next()
                    }
                currentRock.move(direction, fallenRockCoordinates)
                val canFall = currentRock.fall(fallenRockCoordinates)
                if (!canFall) {
                    currentRock.addToFallenRockCoordinates(fallenRockCoordinates)
                    currentShapeNumber = (currentShapeNumber + 1) % 5
                    height = fallenRockCoordinates.map { it.x }.max()
                    rockCount++
                    break
                }
            }
        }
        println(fallenRockCoordinates.map { it.x }.max())
    }

    override fun part2() {
    }
}

fun main() {
    Day17.solve()
}