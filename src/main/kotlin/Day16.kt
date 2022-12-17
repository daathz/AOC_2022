object Day16 : DayXX() {

    data class Valve(val name: String, val rate: Int, val neighbours: List<String>)
    override fun part1() {
        val valves = getValves(readInput("day16"))
        val distances = getDistances(valves)

        val valvesWithPressure = listOf("XD", "FQ", "HH", "DW", "RP", "VM", "CS", "XC", "TE", "SU", "FL", "OL", "YP", "UK", "WV")
        val myValvesList = mutableListOf<MutableList<String>>()

        for (i in 0 until (1 shl valvesWithPressure.size)) {
            val newList = mutableListOf<String>()
            for (j in valvesWithPressure.indices) {
                if (i and (1 shl j) > 0) {
                    newList.add(valvesWithPressure[j])
                }
            }
            if (newList.size == 7) myValvesList.add(newList)
        }

        val elephantList = myValvesList.map { list ->
            val newList = mutableListOf<String>()
            valvesWithPressure.forEach { valve ->
                if (!list.contains(valve)) newList.add(valve)
            }

            newList
        }

        var maxPressure = 0
        for (i in myValvesList.indices) {
            val myPressureSet = mutableSetOf<Int>()
            val elephantsPressureSet = mutableSetOf<Int>()

            dfs(0, 26, valves.first { it.name == "AA" }, myValvesList[i].toSet(), distances, valves, myPressureSet)
            dfs(0, 26, valves.first { it.name == "AA" }, elephantList[i].toSet(), distances, valves, elephantsPressureSet)

            maxPressure = maxOf(myPressureSet.max() + elephantsPressureSet.max(), maxPressure)
        }
        println(maxPressure)

        // dfs(0, 30, valves.first { it.name == "AA" }, setOf(), distances, valves, pressureSet)

        // println(pressureSet.max())
        // distances.forEach(::println)
    }

    override fun part2() {
    }

    private fun getValves(input: List<String>) = input.map { it.split(" ") }
        .map { line ->
            val name = line[1]
            val rate = line[4].substring(5, line[4].length - 1).toInt()
            val neighbours = line.subList(9, line.size).map {
                if (it.contains(",")) it.substring(0, it.length - 1)
                else it
            }

            Valve(name, rate, neighbours)
        }

    private fun getDistances(valves: List<Valve>): MutableMap<String, MutableMap<String, Int>> {
        var distances = mutableMapOf<String, MutableMap<String, Int>>()

        // Init distance graph
        valves.forEach { valve1 ->
            distances[valve1.name] = mutableMapOf()
            valves.forEach { valve2 ->
                if (valve1.neighbours.contains(valve2.name)) {
                    distances[valve1.name]?.set(valve2.name, 1)
                } else {
                    distances[valve1.name]?.set(valve2.name, 99)
                }
            }
        }

        // Based on https://github.com/andrewass/kalgos/blob/master/src/main/kotlin/algorithms/graph/shortestpath/floydWarshall.kt
        distances.forEach { (k, _) ->
            distances.forEach { (i, _) ->
                distances.forEach { (j, _) ->
                    if (distances[i]!![j]!! > distances[i]!![k]!! + distances[k]!![j]!!) {
                        distances[i]!![j] = distances[i]!![k]!! + distances[k]!![j]!!
                    }
                }
            }
        }

        // Remove valves where the flow rate is 0
        val emptyValves = valves.filter { it.rate == 0 }.map { it.name }.toSet()

        distances = distances.filter { !emptyValves.contains(it.key) || it.key == "AA" }.toMutableMap()

        distances.forEach { (s, _) ->
            distances[s] = distances[s]?.filter { !emptyValves.contains(it.key) }?.toMutableMap()!!
        }

        return distances
    }

    private fun dfs(releasedPressure: Int, startTime: Int, valve: Valve, openedSet: Set<String>,
                    distances: MutableMap<String, MutableMap<String, Int>>, valves: List<Valve>, pressureSet: MutableSet<Int>) {
        for (neighbour in distances[valve.name]!!) {
            if (!openedSet.contains(neighbour.key) && startTime >= 0) {
                val currentPressure = releasedPressure + (valves.first { neighbour.key == it.name }.rate * (startTime - neighbour.value - 1))
                pressureSet.add(currentPressure)
                dfs(
                    currentPressure,
                    startTime - neighbour.value - 1,
                    valves.first { neighbour.key == it.name},
                    openedSet.union(listOf(neighbour.key)),
                    distances, valves, pressureSet
                )
            }
        }
    }
}

fun main() {
    Day16.solve()
}