object Day02 : DayXX() {

    private data class Match(val opponent: Char, val me: Char) {
        fun getScore(): Int {
            when (opponent) {
                'A' -> {
                    return when (me) {
                        'X' -> { 3 + 1 }
                        'Y' -> { 2 + 6 }
                        else -> { 3 }
                    }
                }
                'B' -> {
                    return when (me) {
                        'X' -> { 1 }
                        'Y' -> { 2 + 3 }
                        else -> { 3 + 6 }
                    }
                }
                else -> {
                    return when (me) {
                        'X' -> { 6 + 1 }
                        'Y' -> { 2 }
                        else -> { 3 + 3 }
                    }
                }
            }
        }

        fun getScoreBasedOnOutcome(): Int {
            when (opponent) {
                'A' -> {
                    return when (me) {
                        'X' -> { 3 }
                        'Y' -> { 1 + 3 }
                        else -> { 6 + 2 }
                    }
                }
                'B' -> {
                    return when (me) {
                        'X' -> { 1 }
                        'Y' -> { 2 + 3 }
                        else -> { 3 + 6 }
                    }
                }
                else -> {
                    return when (me) {
                        'X' -> { 2 }
                        'Y' -> { 3 + 3 }
                        else -> { 6 + 1 }
                    }
                }
            }
        }
    }

    override fun part1() {
        val score = readInput("day02").map {
            val arr = it.split(" ")
            Match(arr[0].single(), arr[1].single())
        }.fold(0) { acc, match -> acc + match.getScore() }

        println(score)
    }

    override fun part2() {
        val score = readInput("day02").map {
            val arr = it.split(" ")
            Match(arr[0].single(), arr[1].single())
        }.fold(0) { acc, match -> acc + match.getScoreBasedOnOutcome() }

        println(score)
    }
}

fun main() {
    Day02.solve()
}