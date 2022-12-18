object Day18 : DayXX() {

    data class Coordinate3D(val x: Int, val y: Int, val z: Int)

    private val neighbourDeltas = listOf(
        Coordinate3D(0, 0, -1),
        Coordinate3D(0, 0, 1),
        Coordinate3D(0, -1, 0),
        Coordinate3D(0, 1, 0),
        Coordinate3D(-1, 0, 0),
        Coordinate3D(1, 0, 0),
    )
    override fun part1() {
        val cubes = getCubes(readInput("day18"))

        val neighbourSizeList = cubes.map { cube ->
            neighbourDeltas.count { delta ->
                val deltaCube = Coordinate3D(cube.x + delta.x, cube.y + delta.y, cube.z + delta.z)
                cubes.contains(deltaCube)
            }
        }

        val surfaceArea = neighbourSizeList.sumOf { 6 - it }
        println(surfaceArea)
    }

    override fun part2() {
        val cubes = getCubes(readInput("day18"))

        println(findSurfaceArea(cubes))
    }

    private fun findSurfaceArea(dropletCubes: List<Coordinate3D>): Int {
        val surfaceCubes = mutableSetOf<Coordinate3D>()
        var count = 0

        val minX = dropletCubes.map { it.x }.min() - 1
        val minY = dropletCubes.map { it.y }.min() - 1
        val minZ = dropletCubes.map { it.z }.min() - 1
        val maxX = dropletCubes.map { it.x }.max() + 1
        val maxY = dropletCubes.map { it.y }.max() + 1
        val maxZ = dropletCubes.map { it.z }.max() + 1


        val startCube = Coordinate3D(minX, minY, minZ)
        val queue = ArrayDeque<Coordinate3D>()
        queue.add(startCube)
        while (queue.isNotEmpty()) {
            val current = queue.removeLast()
            surfaceCubes.add(current)

            for (delta in neighbourDeltas) {
                val newX = current.x + delta.x
                val newY = current.y + delta.y
                val newZ = current.z + delta.z

                if (newX in minX..maxX && newY in minY..maxY && newZ in minZ..maxZ) {
                    val deltaCube = Coordinate3D(newX, newY, newZ)

                    if (dropletCubes.contains(deltaCube)) count++
                    else if (!surfaceCubes.contains(deltaCube) && !dropletCubes.contains(deltaCube)) {
                        queue.add(deltaCube)
                        surfaceCubes.add(deltaCube)
                    }
                }
            }
        }

        return count
    }

    private fun getCubes(input: List<String>): List<Coordinate3D> =
        input.map { line ->
            line.split(",")
                .map { value -> value.toInt() }
        }.map { Coordinate3D(it[0], it[1], it[2]) }
}

fun main() {
    Day18.solve()
}