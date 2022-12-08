object Day08 : DayXX() {
    override fun part1() {
        val forest = getForest(readInput("day08"))

        var visibleForests = 0
        for ((ri, row) in forest.withIndex()) {
            for ((ci, _) in row.withIndex()) {
                if (checkNTrees(forest, ri, ci, "", forest[ri][ci])) {
                    visibleForests++
                }
            }
        }

        println(visibleForests)
    }

    override fun part2() {
        val forest = getForest(readInput("day08"))

        var score = 0

        forest.withIndex().forEach { (ri, row) ->
            row.withIndex().forEach { (ci, _) ->
                val current = forest[ri][ci]

                val topScore = checkTopScore(forest, ri, ci, current)
                val bottomScore = checkBottomScore(forest, ri, ci, current)
                val leftScore = checkLeftScore(forest, ri, ci, current)
                val rightScore = checkRightScore(forest, ri, ci, current)

                val currentScore = topScore * bottomScore * leftScore * rightScore

                score = maxOf(score, currentScore)
            }
        }

        println(score)
    }

    private fun getForest(input: List<String>): MutableList<MutableList<Int>> {
        val forest =  mutableListOf<MutableList<Int>>()

        input.forEach {
            val arr = mutableListOf<Int>()
            it.forEach {c ->
                arr.add(Integer.parseInt(c.toString()))
            }
            forest.add(arr)
        }

        return forest
    }

    private fun checkNTrees(list: MutableList<MutableList<Int>>, i: Int, j: Int, direction: String, original: Int): Boolean {
        var result: Boolean

        if (i == 0 || j == 0 || i + 1 == list.size || j + 1 == list[0].size) return true

        if (list[i - 1][j] < original && (direction == "top" ||
                    direction == "")) {
            result = checkNTrees(list, i - 1, j, "top", original)
            if (result) return true
        }
        if (list[i + 1][j] < original && (direction == "bottom" ||
                    direction == "")) {
            result = checkNTrees(list, i + 1, j, "bottom", original)
            if (result) return true
        }
        if (list[i][j - 1] < original && (direction == "left" ||
                    direction == "")) {
            result = checkNTrees(list, i, j - 1, "left", original)
            if (result) return true
        }
        if (list[i][j + 1] < original && (direction == "right" ||
                    direction == "")) {
            result = checkNTrees(list, i, j + 1, "right", original)
            if (result) return true
        }

        return false
    }

    private fun checkTopScore(list: MutableList<MutableList<Int>>, i: Int, j: Int, original: Int): Int {
        if (i == 0) return 0

        val previousHeights = mutableListOf<Int>()

        for (idx in i - 1 downTo 0) {
            val current = list[idx][j]

            previousHeights.add(current)

            if (current >= original) {
                break
            }
        }

        return previousHeights.size
    }

    private fun checkBottomScore(list: MutableList<MutableList<Int>>, i: Int, j: Int, original: Int): Int {
        if (i + 1 == list.size) return 0

        val previousHeights = mutableListOf<Int>()

        for (idx in i + 1 until list.size) {
            val current = list[idx][j]

            previousHeights.add(current)

            if (current >= original) {
                break
            }
        }

        return previousHeights.size
    }

    private fun checkLeftScore(list: MutableList<MutableList<Int>>, i: Int, j: Int, original: Int): Int {
        if (j == 0) return 0

        val previousHeights = mutableListOf<Int>()

        for (idx in j - 1 downTo 0) {
            val current = list[i][idx]

            previousHeights.add(current)

            if (current >= original) {
                break
            }
        }

        return previousHeights.size
    }

    private fun checkRightScore(list: MutableList<MutableList<Int>>, i: Int, j: Int, original: Int): Int {
        if (j + 1 == list[i].size) return 0

        val previousHeights = mutableListOf<Int>()

        for (idx in j + 1 until list[i].size) {
            val current = list[i][idx]

            previousHeights.add(current)

            if (current >= original) {
                break
            }
        }

        return previousHeights.size
    }
}

fun main() {
    Day08.solve()
}