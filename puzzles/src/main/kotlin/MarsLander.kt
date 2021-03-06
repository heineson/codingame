import java.awt.Point
import java.util.*
import java.io.*
import java.math.*

class SurfacePoints(val initPoints: List<Pair<Int, Int>>) {
    fun getValidY(x: Int): Int? {
        val startPoint = initPoints.maxByOrNull { it.first <= x } ?: initPoints[0]
        val endPoint = initPoints.minByOrNull { it.first >= x } ?: initPoints.last()
        if (startPoint.second == endPoint.second) {
            return startPoint.second
        }
        return null
    }
}

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
fun main(args : Array<String>) {
    val input = Scanner(System.`in`)
    val surfaceN = input.nextInt() // the number of points used to draw the surface of Mars.
    val points = mutableListOf<Pair<Int, Int>>()
    for (i in 0 until surfaceN) {
        val landX = input.nextInt() // X coordinate of a surface point. (0 to 6999)
        val landY = input.nextInt() // Y coordinate of a surface point. By linking all the points together in a sequential fashion, you form the surface of Mars.
        points.add(Pair(landX, landY))
    }
    val surfacePoints = SurfacePoints(points)

    // game loop
    while (true) {
        val X = input.nextInt()
        val Y = input.nextInt()
        val hSpeed = input.nextInt() // the horizontal speed (in m/s), can be negative.
        val vSpeed = input.nextInt() // the vertical speed (in m/s), can be negative.
        val fuel = input.nextInt() // the quantity of remaining fuel in liters.
        val rotate = input.nextInt() // the rotation angle in degrees (-90 to 90).
        val power = input.nextInt() // the thrust power (0 to 4).

        val vDistance = Y - surfacePoints.getValidY(X)!!

        // Write an action using println()
        // To debug: System.err.println("Debug messages...");
        val newPower = if (vSpeed <= -35) 4 else 3

        // 2 integers: rotate power. rotate is the desired rotation angle (should be 0 for level 1), power is the desired thrust power (0 to 4).
        println("0 $newPower")
    }
}
