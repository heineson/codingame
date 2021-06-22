import java.util.*
import java.io.*
import kotlin.math.*

fun Double.nroot(n: Int) = this.pow(1.0.div(n.toDouble()))

data class NPoint(val n: Int, val label: String, val coords: List<Int>) {
    fun distanceTo(other: NPoint): Double {
        return abs(coords.zip(other.coords)
            .map { abs(it.second - it.first).toDouble().pow(2) }
            .reduce { acc, d -> acc + d }
            .nroot(n))
    }

    fun crossesOrthant(other: NPoint): Boolean {
        return coords.zip(other.coords).any { (it.first < 0 && it.second > 0) || (it.first > 0 && it.second < 0) }
    }

    override fun equals(other: Any?): Boolean {
        return this === other
    }
}

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
fun main(args : Array<String>) {
    val input = Scanner(System.`in`)
    val count = input.nextInt()
    val n = input.nextInt()
    if (input.hasNextLine()) {
        input.nextLine()
    }
    val points = mutableListOf<NPoint>()
    for (i in 0 until count) {
        val labeledPointData = input.nextLine().split(" ")
        points.add(NPoint(n, labeledPointData[0], labeledPointData.drop(1).map { it.toInt() }))
    }

    val used = mutableListOf<NPoint>()
    for (i in 0 until count) {
        val remaining = points.filter { !used.contains(it) }
        val start = if (used.size > 0) used.last() else NPoint(n, "", List(n) { 0 })

        val target = remaining.minByOrNull { start.distanceTo(it) }!!

        if (start.crossesOrthant(target)) {
            used.add(target.copy(label = " "))
        }
        used.add(target)
    }

    // Write an answer using println()
    // To debug: System.err.println("Debug messages...");

    println(used.joinToString(separator = "") { it.label })
}
