import kotlin.math.abs

object Day09 : DayXX() {

    override fun part2() {
    }
    override fun part1() {
        val moves = readInput("day09").map {
            it.split(" ")
        }.map {
            Pair(it[0], it[1].toInt())
        }

        val map = mutableListOf<MutableList<Boolean>>()
        for (i in 0 until 9000) {
        //for (i in 0 until 50) {
            map.add(mutableListOf())
            for (j in 0 until 9000) {
                //for (j in 0 until 50) {
                map[i].add(false)
            }
        }

        val rope = mutableListOf(
            Pair(2000, 2000),
            Pair(2000, 2000),
            Pair(2000, 2000),
            Pair(2000, 2000),
            Pair(2000, 2000),
            Pair(2000, 2000),
            Pair(2000, 2000),
            Pair(2000, 2000),
            Pair(2000, 2000),
            Pair(2000, 2000)
        )

        /*val rope = mutableListOf(
            Pair(15, 15),
            Pair(15, 15),
            Pair(15, 15),
            Pair(15, 15),
            Pair(15, 15),
            Pair(15, 15),
            Pair(15, 15),
            Pair(15, 15),
            Pair(15, 15),
            Pair(15, 15)
        )*/

        var prevHead = Pair(2000, 2000)
        for (move in moves) {
            for (i in 0 until move.second) {
                rope[0] = when (move.first) {
                    "R" -> Pair(rope[0].first + 1, rope[0].second)
                    "L" -> Pair(rope[0].first - 1, rope[0].second)
                    "U" -> Pair(rope[0].first, rope[0].second + 1)
                    "D" -> Pair(rope[0].first, rope[0].second - 1)
                    else -> rope[0]
                }

                for (j in 0 until 9) {
                    val headPos = rope[j]
                    var tailPos = rope[j + 1]

                    val firstDistance = calcFirstDistance(headPos, tailPos)
                    val secondDistance = calcSecondDistance(headPos, tailPos)

                    if (secondDistance == 0) {
                        if (firstDistance == 2) {
                            tailPos = Pair(tailPos.first + 1, tailPos.second)
                        } else if (firstDistance == -2) {
                            tailPos = Pair(tailPos.first - 1, tailPos.second)
                        }
                    } else if (firstDistance == 0) {
                        if (secondDistance == 2) {
                            tailPos = Pair(tailPos.first, tailPos.second + 1)
                        } else if (secondDistance == -2) {
                            tailPos = Pair(tailPos.first, tailPos.second - 1)
                        }
                    }

                    else if (firstDistance == 1) {
                        if (secondDistance == 2) {
                            tailPos = Pair(tailPos.first + 1, tailPos.second + 1)
                        } else if (secondDistance == -2) {
                            tailPos = Pair(tailPos.first + 1, tailPos.second - 1)
                        }
                    }

                    else if (firstDistance == -1) {
                        if (secondDistance == 2) {
                            tailPos = Pair(tailPos.first - 1, tailPos.second + 1)
                        } else if (secondDistance == -2) {
                            tailPos = Pair(tailPos.first - 1, tailPos.second - 1)
                        }
                    }

                     else if (secondDistance == 1) {
                        if (firstDistance == 2) {
                            tailPos = Pair(tailPos.first + 1, tailPos.second + 1)
                        } else if (firstDistance == -2) {
                            tailPos = Pair(tailPos.first - 1, tailPos.second + 1)
                        }
                    }

                    else if (secondDistance == -1) {
                        if (firstDistance == 2) {
                            tailPos = Pair(tailPos.first + 1, tailPos.second - 1)
                        } else if (firstDistance == -2) {
                            tailPos = Pair(tailPos.first - 1, tailPos.second - 1)
                        }
                    }

                    else if (firstDistance == 2 && secondDistance == 2) {
                        tailPos = Pair(tailPos.first + 1, tailPos.second + 1)
                    }

                    else if (firstDistance == -2 && secondDistance == -2) {
                        tailPos = Pair(tailPos.first - 1, tailPos.second - 1)
                    }

                    else if (firstDistance == 2 && secondDistance == -2) {
                        tailPos = Pair(tailPos.first + 1, tailPos.second - 1)
                    }

                    else if (firstDistance == -2 && secondDistance == 2) {
                        tailPos = Pair(tailPos.first - 1, tailPos.second + 1)
                    }

                    prevHead = rope[j + 1]
                    rope[j + 1] = tailPos
                }

                map[rope.last().first][rope.last().second] = true
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

    private fun calcFirstDistance(head: Pair<Int, Int>, tail: Pair<Int, Int>): Int = head.first - tail.first

    private fun calcSecondDistance(head: Pair<Int, Int>, tail: Pair<Int, Int>): Int = head.second - tail.second

}

fun main() {
    Day09.solve()
}