object Day18 : DayXX() {

    data class Coordinate3D(val x: Int, val y: Int, val z: Int, var howManyN: Int = 0)
    override fun part1() {
        val cubeValues = readInput("day18")
            .map { line -> line.split(",")
                .map { value -> value.toInt() }
            }.map { Coordinate3D(it[0], it[1], it[2]) }

        val count = findSurfaceArea(cubeValues)

        /*cubeValues.forEach { cube1 ->
            cubeValues.forEach { cube2 ->
                if (cube1 != cube2) {
                    if (cube1.x == cube2.x && cube1.y == cube2.y && abs(cube1.z - cube2.z) == 1) {
                        cube1.howManyN++
                    } else if (cube1.x == cube2.x && cube1.z == cube2.z && abs(cube1.y - cube2.y) == 1) {
                        cube1.howManyN++
                    } else if (cube1.y == cube2.y && cube1.z == cube2.z && abs(cube1.x - cube2.x) == 1) {
                        cube1.howManyN++
                    }
                }
            }
        }*/

        //cubeValues.forEach(::println)

        val sum = cubeValues.sumOf { maxOf(0, 6 - it.howManyN) }

        println(count)
    }

    override fun part2() {
    }

    private fun findSurfaceArea(cubeValues: List<Coordinate3D>): Int {
        val surfaceCubes = mutableSetOf<Coordinate3D>()
        var count = 0

        val neighbourDeltas = listOf(
            Triple(0, 0, -1),
            Triple(0, 0, 1),
            Triple(0, -1, 0),
            Triple(0, 1, 0),
            Triple(-1, 0, 0),
            Triple(1, 0, 0),
        )

        val minX = cubeValues.map { it.x }.min() - 1
        val minY = cubeValues.map { it.y }.min() - 1
        val minZ = cubeValues.map { it.z }.min() - 1
        val maxX = cubeValues.map { it.x }.max() + 1
        val maxY = cubeValues.map { it.y }.max() + 1
        val maxZ = cubeValues.map { it.z }.max() + 1


        val startCube = Coordinate3D(minX, minY, minZ)
        val queue = ArrayDeque<Coordinate3D>()
        queue.add(startCube)
        while (queue.isNotEmpty()) {
            val current = queue.removeLast()
            surfaceCubes.add(current)

            for (delta in neighbourDeltas) {
                val newX = current.x + delta.first
                val newY = current.y + delta.second
                val newZ = current.z + delta.third

                if (newX in minX..maxX && newY in minY..maxY && newZ in minZ..maxZ) {
                    val newCoord = Coordinate3D(newX, newY, newZ)

                    if (!surfaceCubes.contains(newCoord) && !cubeValues.contains(newCoord)) {
                        queue.add(newCoord)
                        surfaceCubes.add(newCoord)
                    }
                }
            }
        }

        for (surfaceCube in surfaceCubes) {
            for (delta in neighbourDeltas) {
                val currentCube =
                    Coordinate3D(surfaceCube.x + delta.first, surfaceCube.y + delta.second, surfaceCube.z + delta.third)
                if (cubeValues.contains(currentCube)) count++
            }
        }

        return count
    }
}

fun main() {
    Day18.solve()
}