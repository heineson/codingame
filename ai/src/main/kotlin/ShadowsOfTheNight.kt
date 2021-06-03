import java.util.*

fun next(low: Int, high: Int) = (low + high) / 2

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
fun main(args : Array<String>) {
    val input = Scanner(System.`in`)
    val W = input.nextInt() // width of the building.
    val H = input.nextInt() // height of the building.
    val N = input.nextInt() // maximum number of turns before game over.
    val X0 = input.nextInt()
    val Y0 = input.nextInt()

    var currX = X0
    var currY = Y0
    var currLowX = 0
    var currHighX = W
    var currLowY = 0
    var currHighY = H
    // game loop
    while (true) {
        val bombDir = input.next() // the direction of the bombs from batman's current location (U, UR, R, DR, D, DL, L or UL)

        // Write an action using println()
        // To debug: System.err.println("Debug messages...");
        if (bombDir.contains("U")) {
            currHighY = currY
        }
        if (bombDir.contains("D")) {
            currLowY = currY
        }
        if (bombDir.contains("R")) {
            currLowX = currX
        }
        if (bombDir.contains("L")) {
            currHighX = currX
        }
        currX = next(currLowX, currHighX)
        currY = next(currLowY, currHighY)

        // the location of the next window Batman should jump to.
        println("$currX $currY")
    }
}
