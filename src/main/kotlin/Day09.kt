import kotlin.math.abs

object Day09 : DayXX() {
    override fun part1() {
        val moves = readInput("day09").map {
            it.split(" ")
        }.map {
            Pair(it[0], it[1].toInt())
        }

        val map = mutableListOf<MutableList<Boolean>>()
        for (i in 0 until 9000) {
            map.add(mutableListOf())
            for (j in 0 until 9000) {
                map[i].add(false)
            }
        }

        var headPos = Pair(1000, 1000)
        var tailPos = Pair(1000, 1000)

        for (move in moves) {
            for (i in 0 until move.second) {
                headPos = when (move.first) {
                    "R" -> Pair(headPos.first + 1, headPos.second)
                    "L" -> Pair(headPos.first - 1, headPos.second)
                    "U" -> Pair(headPos.first, headPos.second + 1)
                    "D" -> Pair(headPos.first, headPos.second - 1)
                    else -> headPos
                }

                // Linear moves
                if (headPos.first - tailPos.first == 2 && headPos.second == tailPos.second) {
                    tailPos = Pair(headPos.first - 1, headPos.second)
                } else if (tailPos.first - headPos.first == 2 && headPos.second == tailPos.second) {
                    tailPos = Pair(headPos.first + 1, headPos.second)
                } else if (headPos.first == tailPos.first && headPos.second - tailPos.second == 2) {
                    tailPos = Pair(headPos.first, headPos.second - 1)
                } else if (headPos.first == tailPos.first && tailPos.second - headPos.second == 2) {
                    tailPos = Pair(headPos.first, headPos.second + 1)
                } else
                if (headPos.first - tailPos.first == 1 && headPos.second - tailPos.second == 2) { // Diagonal shit
                    tailPos = Pair(headPos.first, headPos.second - 1)
                } else if (tailPos.first - headPos.first == 1 && headPos.second - tailPos.second == 2) {
                    tailPos = Pair(headPos.first, headPos.second - 1)
                } else if (headPos.first - tailPos.first == 2 && headPos.second - tailPos.second == 1) {
                    tailPos = Pair(headPos.first - 1, headPos.second)
                } else if (headPos.first - tailPos.first == 2 && tailPos.second - headPos.second == 1) {
                    tailPos = Pair(headPos.first - 1, headPos.second)
                } else if (tailPos.first - headPos.first == 2 && tailPos.second - headPos.second == 1) {
                    tailPos = Pair(headPos.first + 1, headPos.second)
                } else if (headPos.first - tailPos.first == 1 && tailPos.second - headPos.second == 2) {
                    tailPos = Pair(headPos.first, headPos.second + 1)
                } else if (tailPos.first - headPos.first == 1 && tailPos.second - headPos.second == 2) {
                    tailPos = Pair(headPos.first, headPos.second + 1)
                } else if (tailPos.first - headPos.first == 2 && headPos.second - tailPos.second == 1) {
                    tailPos = Pair(headPos.first + 1 ,headPos.second)
                }

                map[tailPos.first][tailPos.second] = true
            }
        }

        /*for (i in 0 until map.size) {
            for (j in 0 until map[i].size) {
                if (map[i][j]) {
                    print("#")
                } else print(" ")
            }
            println()
        }*/

        val count = map.flatten().count { it }
        println(count)
    }

    private fun calcDistance(head: Pair<Int, Int>, tail: Pair<Int, Int>): Int {
        return abs(head.first - tail.first) + abs(head.second - tail.second)
    }

    private fun moveTail(head: Pair<Int, Int>, tail: Pair<Int, Int>): Pair<Int, Int> {
        val calcDistance = calcDistance(head, tail)

        if (calcDistance > 1) {
            if (head.first > tail.first && head.second == tail.second) {
                return Pair(tail.first + 1, tail.second)
            } else if (head.first < tail.first && head.second == tail.second) {
                return Pair(tail.first - 1, tail.second)
            } else if (head.first == tail.first && head.second > tail.second) {
                return Pair(tail.first, tail.second + 1)
            } else if (head.first == tail.first && head.second < tail.second) {
                return Pair(tail.first, tail.second - 1)
            } else if (head.first > tail.first && head.second > tail.second) {
                return Pair(head.first, head.second - 1)
            } else if (head.first > tail.first && head.second < tail.second) {
                return Pair(head.first, head.second - 1)
            } else if (head.first < tail.first && head.second > tail.second) {
                return Pair(head.first - 1, head.second)
            } else {
                return Pair(head.first, head.second + 1)
            }
        }

        return tail
    }


    override fun part2() {
    }
}

fun main() {
    Day09.solve()
}