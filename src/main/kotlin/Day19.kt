object Day19 : DayXX() {

    override fun part1() {
        val blueprints = getBlueprints(readInput("day19"))

        val sum = blueprints.sumOf { blueprint ->
            blueprint.id * bfs(blueprint.costs, 24)
        }

        println(sum)
    }

    override fun part2() {
        val blueprints = getBlueprints(readInput("day19")).take(3)

        val reduce = blueprints.map { blueprint ->
            bfs(blueprint.costs, 32)
        }.reduce { acc, i -> acc * i }

        println(reduce)
    }

    private fun getBlueprints(input: List<String>) = input.map { it.split(" ") }.map { line ->
        Blueprint(
            line[1].substring(0, line[1].length - 1).toInt(),
            listOf(
                Cost(line[6].toInt(), 0, 0, 0),
                Cost(line[12].toInt(), 0, 0, 0),
                Cost(line[18].toInt(), line[21].toInt(), 0, 0),
                Cost(line[27].toInt(), 0, line[30].toInt(), 0)
            )
        )
    }

    private fun bfs(costsPerRobot: List<Cost>, time: Int): Int {
        var maxVal = 0

        var deque = ArrayDeque<State>()
        deque.add(State(0, Robots(1, 0, 0, 0), Resources(0, 0, 0, 0), Resources(0, 0, 0, 0)))
        var depth = 0

        while (deque.isNotEmpty()) {
            val (currentTime, currentRobots, currentCollected, currentMined) = deque.removeFirst()

            if (currentTime > depth) {
                deque = pruneQueue(deque)
                depth = currentTime
            }

            if (currentTime == time) {
                maxVal = maxOf(maxVal, currentMined[3])
                continue
            }

            val newCollected = currentCollected + currentRobots
            val newMined = currentMined + currentRobots

            deque.add(State(currentTime + 1, currentRobots, newCollected, newMined))

            for ((idx, costOfRobot) in costsPerRobot.withIndex()) {

                if (currentCollected > costOfRobot) {
                    val newRobots = currentRobots.addRobot(idx)
                    val newCollectedAgain = newCollected - costOfRobot

                    deque.add(State(currentTime + 1, newRobots, newCollectedAgain, newMined))
                }
            }
        }

        return maxVal
    }

    private fun pruneQueue(queue: ArrayDeque<State>, n: Int = 30_000) = ArrayDeque(
        queue.sortedBy { (_, _, _, mined) ->
            10000 * mined[3] + 1000 * mined[2] + 10 * mined[1] + mined[0]
        }.reversed().take(n)
    )
}

fun main() {
    Day19.solve()
}

class Resources(i: Int, j: Int, k: Int, l: Int) {
    val list: List<Int> = listOf(i, j, k, l)

    operator fun get(idx: Int) = list[idx]

    operator fun plus(other: Resources) = list.mapIndexed { idx, value ->
        value + other[idx]
    }.toResource()

    operator fun minus(other: Resources) = list.mapIndexed { idx, value ->
        value - other[idx]
    }.toResource()

    operator fun compareTo(other: Resources) =
        when (list.filterIndexed { idx, value -> value >= other.list[idx] }.count()) {
            in 4..Int.MAX_VALUE -> 1
            in 1..3 -> 0
            else -> -1
        }

    fun addRobot(idx: Int): Resources {
        val tempList = list.toMutableList()
        tempList[idx]++

        return tempList.toResource()
    }
}

fun List<Int>.toResource() = Resources(this[0], this[1], this[2], this[3])

typealias Cost = Resources

typealias Robots = Resources

data class Blueprint(val id: Int, val costs: List<Cost>)

data class State(val time: Int, val robots: Robots, val collected: Resources, val mined: Resources)