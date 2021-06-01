import java.util.*
import java.io.*
import java.math.*

class Alphabet(val rows: List<String>, val width: Int, val height: Int) {
    private val positions = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    fun getPosition(letter: String): Int {
        val pos = positions.indexOf(letter.uppercase())
        return if (pos == -1) positions.length else pos
    }
    fun getAsciiLetter(letter: String): List<String> {
        val pos = getPosition(letter)
        System.err.println("$letter, ($pos)")
        return rows.map { it.substring(pos * width, pos * width + width) }.also { System.err.println(it.joinToString("\n")) }
    }
}

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
fun main(args : Array<String>) {
    val input = Scanner(System.`in`)
    val L = input.nextInt()
    val H = input.nextInt()
    if (input.hasNextLine()) {
        input.nextLine()
    }
    val T = input.nextLine()
    val rows = mutableListOf<String>()
    for (i in 0 until H) {
        rows.add(input.nextLine())
    }
    val alphabet = Alphabet(rows, L, H)

    // Write an answer using println()
    // To debug: System.err.println("Debug messages...");
    val charsAsLists = T.toCharArray().map { alphabet.getAsciiLetter(it.toString()) }
    val lines = mutableListOf<String>()
    for (i in 0 until H) {
        lines.add(charsAsLists.map { it[i] }.joinToString(""))
    }
    println(lines.joinToString("\n"))
}