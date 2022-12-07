import java.util.*

object Day07 : DayXX() {
    override fun part1() {

        val rootNode = getNodeStructure(readInput("day07"))

        rootNode.getSize()

        rootNode.showSize()

        println(Node.sumSize)

    }

    override fun part2() {
        val rootNode = getNodeStructure(readInput("day07"))

        rootNode.getSize()

        rootNode.showFreeableSizes(rootNode.size)

        println(Node.freeupFolders.min())
    }

    private fun getNodeStructure(input: List<String>): Node {
        val rootNode = Node("/", 0, mutableListOf())
        var currentNode = rootNode
        val previousNodes = Stack<Node>()
        previousNodes.add(rootNode)
        readInput("day07").map {
            it.split(" ")
        }.forEach {
            if (it[0] == "$" && it[1] == "cd") {
                if (it[2] == "/") {
                    currentNode = rootNode
                    previousNodes.empty()
                    previousNodes.add(rootNode)
                } else if (it[2] == "..") {
                    currentNode = previousNodes.pop()
                } else {
                    previousNodes.add(currentNode)
                    currentNode = currentNode.children?.find { node -> node.name == it[2] }!!
                }
            } else if (it[0] == "dir") {
                currentNode.addChild(Node(it[1], 0, mutableListOf()))
            } else if (it[0] == "$" && it[1] == "ls") {
            } else {
                currentNode.addChild(Node(it[1], it[0].toInt(), null))
            }
        }

        return rootNode
    }
}

private data class Node(val name: String, var size: Int, val children: MutableList<Node>?) {
    fun addChild(node: Node) {
        children?.add(node)
    }

    fun getSize() {
        if (!children.isNullOrEmpty()) {
            for (child in children) {
                child.getSize()
            }
            children.forEach {
                size += it.size
            }
        }
    }

    fun showSize() {
        if (size < 100000) {
            sumSize += size
        }
        if (!children.isNullOrEmpty()) {
            children.forEach {
                if (it.children != null) {
                    it.showSize()
                }
            }
        }
    }

    fun showFreeableSizes(rootSize: Int) {
        val freeSpace = 70000000 - rootSize
        if (size > 30000000 - freeSpace) {
            freeupFolders.add(size)

            if (!children.isNullOrEmpty()) {
                children.forEach {
                    if (it.children != null) {
                        it.showFreeableSizes(rootSize)
                    }
                }
            }
        }
    }

    companion object {
        var sumSize = 0

        val freeupFolders = mutableListOf<Int>()
    }
}

fun main() {
    Day07.solve()
}