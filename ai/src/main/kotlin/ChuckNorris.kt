import java.util.*
import java.io.*
import java.math.*

fun encode(s: String): String {
    val prefix = if (s[0] == '1') "0" else "00"
    return prefix + " " + "0".repeat(s.length) + " "
}

fun getGroupAndRemainder(s: String): Pair<String, String> {
    val g = if (s[0] == '1') s.takeWhile { it == '1' } else s.takeWhile { it == '0' }
    return Pair(encode(g), s.drop(g.length))
}

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
fun main(args : Array<String>) {
    val input = Scanner(System.`in`)
    val message = input.nextLine()

    // Write an answer using println()
    // To debug: System.err.println("Debug messages...");
    val byteString = message.toCharArray().map { it.toByte().toString(2).padStart(7, '0') }.joinToString("").also { System.err.println(it) }
    var remainder = byteString
    var encodedString = ""
    while (remainder.isNotEmpty()) {
        val res = getGroupAndRemainder(remainder)
        encodedString += res.first
        remainder = res.second
    }
    println(encodedString.trim())
}
