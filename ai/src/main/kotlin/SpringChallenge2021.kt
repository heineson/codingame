import java.util.*
import java.io.*
import java.math.*

data class Cell(val index: Int, val richness: Int, val neighbors: List<Int>)
data class Tree(val cellIndex: Int, val size: Int, val isMine: Boolean, val isDormant: Boolean)
data class Action(val type: String, val sourceIndex: Int?, val targetIndex: Int?) {
    override fun toString(): String {
        return "$type${if (sourceIndex != null) " $sourceIndex" else ""}${if (targetIndex != null) " $targetIndex" else ""}"
    }
}


/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
fun main(args : Array<String>) {
    val input = Scanner(System.`in`)
    val numberOfCells = input.nextInt() // 37
    val cells = mutableMapOf<Int, Cell>()
    for (i in 0 until numberOfCells) {
        val index = input.nextInt() // 0 is the center cell, the next cells spiral outwards
        val richness = input.nextInt() // 0 if the cell is unusable, 1-3 for usable cells
        val neigh0 = input.nextInt() // the index of the neighbouring cell for each direction
        val neigh1 = input.nextInt()
        val neigh2 = input.nextInt()
        val neigh3 = input.nextInt()
        val neigh4 = input.nextInt()
        val neigh5 = input.nextInt()
        cells[index] = Cell(index, richness, listOf(neigh0, neigh1, neigh2, neigh3, neigh4, neigh5))
    }

    // game loop
    while (true) {
        val trees = mutableMapOf<Int, Tree>()
        val actions = mutableListOf<Action>()

        val day = input.nextInt() // the game lasts 24 days: 0-23
        val nutrients = input.nextInt() // the base score you gain from the next COMPLETE action
        val sun = input.nextInt() // your sun points
        val score = input.nextInt() // your current score
        val oppSun = input.nextInt() // opponent's sun points
        val oppScore = input.nextInt() // opponent's score
        val oppIsWaiting = input.nextInt() != 0 // whether your opponent is asleep until the next day
        val numberOfTrees = input.nextInt() // the current amount of trees
        for (i in 0 until numberOfTrees) {
            val cellIndex = input.nextInt() // location of this tree
            val size = input.nextInt() // size of this tree: 0-3
            val isMine = input.nextInt() != 0 // 1 if this is your tree
            val isDormant = input.nextInt() != 0 // 1 if this tree is dormant
            trees[cellIndex] = Tree(cellIndex, size, isMine, isDormant)
        }
        val numberOfPossibleMoves = input.nextInt()
        if (input.hasNextLine()) {
            input.nextLine()
        }
        for (i in 0 until numberOfPossibleMoves) {
            val actionParts = input.nextLine().split(" ")
            when (actionParts[0]) {
                "GROW" -> actions.add(Action("GROW", null, actionParts[1].toInt()))
                "SEED" -> actions.add(Action("SEED", actionParts[1].toInt(), actionParts[2].toInt()))
                "COMPLETE" -> actions.add(Action("COMPLETE", null, actionParts[1].toInt()))
                "WAIT" -> actions.add(Action("WAIT", null, null))
            }
        }

        val completeAction = actions.filter { it.type == "COMPLETE" }
            .sortedByDescending { cells[it.targetIndex!!]?.richness ?: 0 }
            .firstOrNull()

        val growAction = actions.filter { it.type == "GROW" }
            .sortedByDescending { trees[it.targetIndex!!]?.size }
            .firstOrNull()

        // GROW cellIdx | SEED sourceIdx targetIdx | COMPLETE cellIdx | WAIT <message>
        println(completeAction ?: growAction ?: "WAIT")
    }
}
