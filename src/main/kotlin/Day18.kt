import kotlin.math.abs

object Day18 : DayXX() {

    data class Coordinate3D(val x: Int, val y: Int, val z: Int, var howManyN: Int = 0)
    override fun part1() {
        val cubeValues = readInput("day18")
            .map { line -> line.split(",")
                .map { value -> value.toInt() }
            }.map { Coordinate3D(it[0], it[1], it[2]) }

        cubeValues.forEach { cube1 ->
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
        }

        //cubeValues.forEach(::println)

        val sum = cubeValues.map { maxOf(0, 6 - it.howManyN) }.sum()
        println(sum)
    }

    override fun part2() {
    }
}

fun main() {
    Day18.solve()
}