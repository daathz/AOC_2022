object Day09 : DayXX() {

    private data class Move(val direction: Char, val amount: Int)

    private data class Knot(val row: Int, val column: Int)

    override fun part1() {
        val moves = getMoves(readInput("day09"))

        val visitedCoordinates = HashSet<Knot>()

        var headPos = Knot(0, 0)
        var tailPos = Knot(0, 0)

        for (move in moves) {
            for (i in 0 until move.amount) {
                headPos = moveHead(headPos, move.direction)

                tailPos = moveTail(headPos, tailPos)

                visitedCoordinates.add(tailPos)
            }
        }

        println(visitedCoordinates.count())
    }

    override fun part2() {
        val moves = getMoves(readInput("day09"))

        val visitedCoordinates = HashSet<Knot>()

        val rope = mutableListOf<Knot>()

        (0 until 10).forEach { _ ->
            rope.add(Knot(0, 0))
        }

        for (move in moves) {
            for (i in 0 until move.amount) {
                rope[0] = moveHead(rope[0], move.direction)

                for (j in 0 until 9) {
                    val headPos = rope[j]
                    var tailPos = rope[j + 1]

                    tailPos = moveTail(headPos, tailPos)

                    rope[j + 1] = tailPos
                }

                visitedCoordinates.add(rope.last())
            }
        }

        println(visitedCoordinates.count())
    }


    private fun calcRowDistance(head: Knot, tail: Knot): Int = head.row - tail.row

    private fun calcColumnDistance(head: Knot, tail: Knot): Int = head.column - tail.column

    private fun getMoves(input: List<String>) = input
        .map { it.split(" ") }
        .map { Move(it[0].single(), it[1].toInt()) }

    private fun moveHead(headPos: Knot, move: Char): Knot {
        return when (move) {
            'R' -> Knot(headPos.row + 1, headPos.column)
            'L' -> Knot(headPos.row - 1, headPos.column)
            'U' -> Knot(headPos.row, headPos.column + 1)
            'D' -> Knot(headPos.row, headPos.column - 1)
            else -> headPos
        }
    }

    private fun moveTail(headPos: Knot, tailPos: Knot): Knot {
        val rowDistance = calcRowDistance(headPos, tailPos)
        val columnDistance = calcColumnDistance(headPos, tailPos)

        var tail = tailPos

        if (columnDistance == 0) {
            if (rowDistance == 2) {
                tail = Knot(tail.row + 1, tail.column)
            } else if (rowDistance == -2) {
                tail = Knot(tail.row - 1, tail.column)
            }
        } else if (rowDistance == 0) {
            if (columnDistance == 2) {
                tail = Knot(tail.row, tail.column + 1)
            } else if (columnDistance == -2) {
                tail = Knot(tail.row, tail.column - 1)
            }
        }

        else if (rowDistance == 1) {
            if (columnDistance == 2) {
                tail = Knot(tail.row + 1, tail.column + 1)
            } else if (columnDistance == -2) {
                tail = Knot(tail.row + 1, tail.column - 1)
            }
        }

        else if (rowDistance == -1) {
            if (columnDistance == 2) {
                tail = Knot(tail.row - 1, tail.column + 1)
            } else if (columnDistance == -2) {
                tail = Knot(tail.row - 1, tail.column - 1)
            }
        }

        else if (columnDistance == 1) {
            if (rowDistance == 2) {
                tail = Knot(tail.row + 1, tail.column + 1)
            } else if (rowDistance == -2) {
                tail = Knot(tail.row - 1, tail.column + 1)
            }
        }

        else if (columnDistance == -1) {
            if (rowDistance == 2) {
                tail = Knot(tail.row + 1, tail.column - 1)
            } else if (rowDistance == -2) {
                tail = Knot(tail.row - 1, tail.column - 1)
            }
        }

        else if (rowDistance == 2 && columnDistance == 2) {
            tail = Knot(tail.row + 1, tail.column + 1)
        }

        else if (rowDistance == -2 && columnDistance == -2) {
            tail = Knot(tail.row - 1, tail.column - 1)
        }

        else if (rowDistance == 2 && columnDistance == -2) {
            tail = Knot(tail.row + 1, tail.column - 1)
        }

        else if (rowDistance == -2 && columnDistance == 2) {
            tail = Knot(tail.row - 1, tail.column + 1)
        }

        return tail
    }
}

fun main() {
    Day09.solve()
}