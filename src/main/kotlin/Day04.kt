object Day04 : DayXX() {
    private data class Room(val first: HashSet<Int>, val second: HashSet<Int>)
    override fun part1() {
        var score = 0
        val inputs = readInput("day04")
        val rooms = ArrayList<Room>()

        for (input in inputs) {
            val split = input.split(",")
            val firstElf = split[0]
            val secondElf = split[1]

            val room = Room(HashSet(), HashSet())

            val firstElfsFirstNumber = Integer.parseInt(firstElf.split("-")[0])
            val firstElfsSecondNumber = Integer.parseInt(firstElf.split("-")[1])
            for (i in firstElfsFirstNumber..firstElfsSecondNumber) {
                room.first.add(i)
            }

            val secondElfsFirstNumber = Integer.parseInt(secondElf.split("-")[0])
            val secondtElfsSecondNumber = Integer.parseInt(secondElf.split("-")[1])
            for (i in secondElfsFirstNumber..secondtElfsSecondNumber) {
                room.second.add(i)
            }

            rooms.add(room)
        }

        for (room in rooms) {
            val intersect = room.first.intersect(room.second)

            if (intersect.size == room.first.size || intersect.size == room.second.size) {
                score++
            }
        }

        println(score)
    }

    override fun part2() {
        var score = 0
        val inputs = readInput("day04")
        val rooms = ArrayList<Room>()

        for (input in inputs) {
            val split = input.split(",")
            val firstElf = split[0]
            val secondElf = split[1]

            val room = Room(HashSet(), HashSet())

            val firstElfsFirstNumber = Integer.parseInt(firstElf.split("-")[0])
            val firstElfsSecondNumber = Integer.parseInt(firstElf.split("-")[1])
            for (i in firstElfsFirstNumber..firstElfsSecondNumber) {
                room.first.add(i)
            }

            val secondElfsFirstNumber = Integer.parseInt(secondElf.split("-")[0])
            val secondtElfsSecondNumber = Integer.parseInt(secondElf.split("-")[1])
            for (i in secondElfsFirstNumber..secondtElfsSecondNumber) {
                room.second.add(i)
            }

            rooms.add(room)
        }

        for (room in rooms) {
            val intersect = room.first.intersect(room.second)

            if (intersect.isNotEmpty()) {
                score++
            }
        }

        println(score)
    }
}

fun main() {
    Day04.solve()
}