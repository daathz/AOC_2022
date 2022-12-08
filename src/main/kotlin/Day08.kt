object Day08 : DayXX() {
    override fun part1() {
        val forest = mutableListOf<MutableList<Int>>()
        readInput("day08").forEach {
            val arr = mutableListOf<Int>()
            it.forEach {c ->
                arr.add(Integer.parseInt(c.toString()))
            }
            forest.add(arr)
        }

        checkNTrees(forest, 32, 31, "" , forest.get(32).get(31))

        var visibleCount = 0
        for ((ri, row) in forest.withIndex()) {
            for ((ci, column) in row.withIndex()) {
                if (checkNTrees(forest, ri, ci, "" , forest.get(ri).get(ci))) {
                    print(forest.get(ri).get(ci))
                    visibleCount++
                } else {
                    print(" ")
                }
            }
            println()

        }

        println(visibleCount)
    }

    private fun checkNTrees(list: MutableList<MutableList<Int>>, i: Int, j: Int, direction: String, original: Int):
            Boolean {
        var result = false
        val current = list.get(i).get(j)

        if (i == 0 || j == 0 || i + 1 == list.size || j + 1 == list[0].size) return true

        if (list.get(i - 1).get(j) < original /*&& list.get(i - 1).get(j) <= current*/ && (direction == "top" ||
                    direction == "")) {
            result = checkNTrees(list, i - 1, j, "top", original)
            if (result) return true
        }
        if (list.get(i + 1).get(j) < original /*&& list.get(i + 1).get(j) <= current*/ && (direction == "bottom" ||
                    direction == "")) {
            result = checkNTrees(list, i + 1, j, "bottom", original)
            if (result) return true
        }
        if (list.get(i).get(j - 1) < original /*&& list.get(i).get(j - 1) <= current*/ && (direction == "left" ||
                    direction == "")) {
            result = checkNTrees(list, i, j - 1, "left", original)
            if (result) return true
        }
        if (list.get(i).get(j + 1) < original /*&& list.get(i).get(j + 1) <= current*/ && (direction == "right" ||
                    direction == "")) {
            result = checkNTrees(list, i, j + 1, "right", original)
            if (result) return true
        }

        return result
    }

    override fun part2() {
    }
}

fun main() {
    Day08.solve()
}