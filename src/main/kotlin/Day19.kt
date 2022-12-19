import java.util.Locale

object Day19 : DayXX() {
    override fun part1() {
        val blueprints = readInput("day19test").map { it.split(" ") }.map { line ->
            listOf(
                line[1].substring(0, line[1].length - 1).toInt(),
                line[6].toInt(),
                line[12].toInt(),
                line[18].toInt(),
                line[21].toInt(),
                line[27].toInt(),
                line[30].toInt()
            )
        }

        val quality = mutableListOf<Int>()
        for (blueprint in blueprints) {
            val costsPerRobot = listOf(
                listOf(blueprint[1], 0, 0, 0),
                listOf(blueprint[2], 0, 0, 0),
                listOf(blueprint[3], blueprint[4], 0, 0),
                listOf(blueprint[5], 0, blueprint[6], 0)
            )

            val robots = listOf(1, 0, 0, 0)

            val maxVal = bfs(costsPerRobot, robots, 24)

            println(blueprint[0].toString() + "\t" + maxVal)
            quality.add(blueprint[0] * maxVal)
        }

        println(quality.sum())
    }

    private fun bfs(costsPerRobot: List<List<Int>>, robots: List<Int>, time: Int): Int {
        var maxVal = 0

        var deque = ArrayDeque<State>()
        deque.add(State(0, robots, listOf(0, 0, 0, 0), listOf(0, 0, 0, 0)))
        var depth = 0

        while (deque.isNotEmpty()) {
            val (currentTime, currentRobots, currentCollected, currentMined) = deque.removeFirst()

            if (currentTime > depth) {
                val sortedQueue = deque.sortedBy {
                    10000 * it.mined[3] + 1000 * it.mined[2] + 10 * it.mined[1] + it.mined[0]
                }.reversed()
                deque = ArrayDeque(sortedQueue.take(30_000))
                depth = currentTime
            }

            if (currentTime == time) {
                maxVal = maxOf(maxVal, currentMined[3])
                continue
            }

            val newCollected = listOf(
                currentCollected[0] + currentRobots[0],
                currentCollected[1] + currentRobots[1],
                currentCollected[2] + currentRobots[2],
                currentCollected[3] + currentRobots[3]
            )

            val newMined = listOf(
                currentMined[0] + currentRobots[0],
                currentMined[1] + currentRobots[1],
                currentMined[2] + currentRobots[2],
                currentMined[3] + currentRobots[3]
            )

            deque.add(State(currentTime + 1, currentRobots, newCollected, newMined))

            for (i in 0 until 4) {
                val costOfRobot = costsPerRobot[i]

                var canBuild = true
                for (j in 0 until 4) {
                    if (currentCollected[j] < costOfRobot[j]) canBuild = false
                }
                if (canBuild) {
                    val newRobots = currentRobots.toMutableList()
                    newRobots[i] += 1


                    val newCollectedAgain = listOf(
                        newCollected[0] - costOfRobot[0],
                        newCollected[1] - costOfRobot[1],
                        newCollected[2] - costOfRobot[2],
                        newCollected[3] - costOfRobot[3]
                    )

                    deque.add(State(currentTime + 1, newRobots.toList(), newCollectedAgain, newMined))
                }
            }
        }

        return maxVal
    }

    override fun part2() {
    }
}

fun main() {
    Day19.solve()
}

data class State(val time: Int, val robots: List<Int>, val collected: List<Int>, val mined: List<Int>)