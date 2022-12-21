object Day21 : DayXX() {
    override fun part1() {
        var jobs = readInput("day21").map { it.split(": ") }.map { line ->
            val name = line[0]
            val isNumeric = isNumeric(line[1])
            val job = if (!isNumeric) line[1] else ""
            val number = if (isNumeric) line[1].toLong() else 0L

            Job(name, job, number)
        }

        // jobs.forEach(::println)

        while (jobs.first { it.name == "root" }.job != "") {
            jobs = calc(jobs)
        }
        println(jobs.first { it.name == "root" }.number)
    }

    override fun part2() {
        val jobs = readInput("day21").map { it.split(": ") }.map { line ->
            val name = line[0]
            val isNumeric = isNumeric(line[1])
            val job = if (!isNumeric) line[1] else ""
            val number = if (isNumeric) line[1].toLong() else 0L

            Job(name, job, number)
        }.toMutableList()

        val humn = jobs.first { it.name == "humn" }
        humn.number = 0L
        humn.job = "x"

        var i = 0
        var tempJobs = jobs.toList()
        while (i < 1_000) {
            tempJobs = calc(tempJobs)
            i++
        }

        println(tempJobs.first { it.name == "root" }.job)
        // And then put it in a solver...
    }

    private fun calc(jobs: List<Job>): List<Job> {
        val newJobs = jobs.toMutableList()

        for (currentJob in jobs) {
            if (currentJob.number != 0L) {
                val name = currentJob.name
                val number = currentJob.number

                for (newJob in newJobs) {
                    if (newJob.job != "" && newJob.job.contains(name)) {
                        newJob.job = newJob.job.replace(name, number.toString())

                        val operator = newJob.job.split(" ")
                        if (isNumeric(operator[0]) && isNumeric(operator[2])) {
                            val left = operator[0].toLong()
                            val right = operator[2].toLong()

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
            } else if (currentJob.job.contains('x')) {
                val name = currentJob.name
                val job = currentJob.job

                for (newJob in newJobs) {
                    if (newJob.job != "" && newJob.job.contains(name)) {
                        newJob.job = newJob.job.replace(name, "($job)")
                    }
                }
            }
        }

        return newJobs
    }

    private fun isNumeric(toCheck: String): Boolean {
        val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
        return toCheck.matches(regex)
    }
}

fun main() {
    Day21.solve()
}

data class Job(val name: String, var job: String, var number: Long)