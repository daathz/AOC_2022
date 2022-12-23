object Day21 : DayXX() {
    override fun part1() {
        var jobs = getJobs(readInput("day21"))

        // jobs.forEach(::println)
        val root = jobs.first { it.name == "root" }

        while (root.job != "") {
            val (newJobs, _) = calc(jobs)
            jobs = newJobs
        }
        println(root.number)
    }

    override fun part2() {
        var low = 0L
        var high = 1_000_000_000_000_000L
        while (true) {
            var tempJobs =  getJobs(readInput("day21"))
            var isSolved = false
            val humn = tempJobs.first { it.name == "humn" }
            val mid = (low + high).floorDiv(2L)
            humn.number = mid

            while (!isSolved) {
                val (newJobs, solved) = calc(tempJobs, true)
                tempJobs = newJobs
                isSolved = solved
            }

            val operators = tempJobs.first { it.name == "root" }.job.split(" ")
            val left = operators[0].toLong()
            val right = operators[2].toLong()
            if (left == right) {
                println(mid)
                break
            } else if (left > right) {
                low = mid
            } else {
                high = mid
            }
        }
    }

    private fun calc(jobs: List<Job>, rootEquals: Boolean = false): Pair<MutableList<Job>, Boolean> {
        val newJobs = jobs.toMutableList()

        for (currentJob in jobs) {
            if (currentJob.number != 0L) {
                val name = currentJob.name
                val number = currentJob.number

                for (newJob in newJobs) {
                    if (newJob.job != "" && newJob.job.contains(name)) {
                        newJob.job = newJob.job.replace(name, number.toString())

                        val operator = newJob.job.split(" ")
                        if (operator[0].isNumeric() && operator[2].isNumeric()) {
                            val left = operator[0].toLong()
                            val right = operator[2].toLong()

                            if (rootEquals && newJob.name == "root") {
                                return newJobs to true
                            }

                            val newNumber = when (operator[1]) {
                                "+" -> left + right
                                "-" -> left - right
                                "*" -> left * right
                                "/" -> left / right
                                else -> 0
                            }

                            newJob.job = ""
                            newJob.number = newNumber

                            // println(newJob)
                        }
                    }
                }
            }
        }

        return newJobs to false
    }

    private fun getJobs(input: List<String>): List<Job> = input.map { it.split(": ") }.map { line ->
        val name = line[0]
        val isNumeric = line[1].isNumeric()
        val job = if (!isNumeric) line[1] else ""
        val number = if (isNumeric) line[1].toLong() else 0L

        Job(name, job, number)
    }
}

fun main() {
    Day21.solve()
}

data class Job(val name: String, var job: String, var number: Long)