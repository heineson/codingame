import java.util.*
import java.io.*
import java.math.*

data class Point(val x: Int, val y: Int) {
    fun getRight(nodes: List<Point>): Point {
        val n = nodes.firstOrNull { n -> n.y == this.y && n.x > this.x }
        return n ?: Point(-1, -1)
    }

    fun getBottom(nodes: List<Point>): Point {
        val n = nodes.firstOrNull { n -> n.x == this.x && n.y > this.y }
        return n ?: Point(-1, -1)
    }
}

/**
 * Don't let the machines win. You are humanity's last hope...
 **/
fun main(args : Array<String>) {
    val input = Scanner(System.`in`)
    val width = input.nextInt() // the number of cells on the X axis
    val height = input.nextInt() // the number of cells on the Y axis
    if (input.hasNextLine()) {
        input.nextLine()
    }
    val lines = mutableListOf<String>()
    for (i in 0 until height) {
        lines += input.nextLine() // width characters, each either 0 or .
    }
    val nodes = lines.foldIndexed(listOf<Point>()) { y, acc, s ->
        acc + s.foldIndexed(listOf()) { x, accX, c -> if (c == '0') accX + Point(x, y) else accX } }

    // Write an action using println()
    // To debug: System.err.println("Debug messages...");
    val result = nodes.map { n -> "${n.x} ${n.y} ${n.getRight(nodes).x} ${n.getRight(nodes).y} ${n.getBottom(nodes).x} ${n.getBottom(nodes).y}" }

    // Three coordinates: a node, its right neighbor, its bottom neighbor
    result.forEach { println(it) }
}
