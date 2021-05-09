import java.util.*

data class Cell(val index: Int, val richness: Int, val neighbors: List<Int>)
data class Tree(val cellIndex: Int, val size: Int, val isMine: Boolean, val isDormant: Boolean)
data class Action(val type: String, val sourceIndex: Int?, val targetIndex: Int?) {
    override fun toString(): String {
        return "$type${if (sourceIndex != null) " $sourceIndex" else ""}${if (targetIndex != null) " $targetIndex" else ""}"
    }
}

fun getTargetCell(cells: Map<Int, Cell>, a: Action): Cell { return cells.getValue(a.targetIndex!!) }
fun getTargetTree(trees: Map<Int, Tree>, a: Action): Tree { return trees.getValue(a.targetIndex!!) }
fun minDaysUntilCompleted(t: Tree): Int {
    // 4 = 3x grow + complete. If not dormant, action can be taken this day.
    return 4 - t.size - if (t.isDormant) 0 else 1
}
fun estDaysToCompleteAll(myTrees: Collection<Tree>): Int {
    // Guess there is enough sun points to do 2 actions per day on average = 2 trees per day actioned upon
    return myTrees.map { minDaysUntilCompleted(it) }.sum() / 2
}
fun growCost(trees: Map<Int, Tree>, a: Action): Int {
    return growCost(trees.values.filter { it.isMine }, getTargetTree(trees, a))
}
fun growCost(myTrees: Collection<Tree>, t: Tree): Int {
    if (t.size == 3) {
        System.err.println("growCost: Can't grow a size 3 tree further!")
    }
    return when (t.size) {
        0 -> 1 + myTrees.filter { it.size == 1 }.size
        1 -> 3 + myTrees.filter { it.size == 2 }.size
        2 -> 7 + myTrees.filter { it.size == 3 }.size
        else -> 999
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
        val remainingDays = 23 - day // days remaining, excluding current
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
        val myTrees = trees.values.filter { it.isMine }
        val seedingCost = myTrees.filter { it.size == 0 }.size

        var completeAction: Action? = null
        // If just one tree remaining, wait until final turn to complete (to get more sun points)
        if (myTrees.size > 1 || remainingDays == 0) {
            completeAction = actions.filter { it.type == "COMPLETE" }
                .sortedByDescending { getTargetCell(cells, it).richness }
                .firstOrNull()
        }

        val growAction = actions.filter { it.type == "GROW" }
            .sortedByDescending { (getTargetTree(trees, it).size + 1) * getTargetCell(cells, it).richness / growCost(trees, it) }
            .firstOrNull()

        var seedAction: Action? = null
        if (estDaysToCompleteAll(myTrees) < remainingDays && seedingCost <= 2) {
            seedAction = actions.filter { it.type == "SEED" }
                // don't seed on lowest richness (1) if I already have more than 2 trees
                .filter { if (myTrees.size > 2) getTargetCell(cells, it).richness > 1 else true }
                .sortedByDescending { getTargetCell(cells, it).richness }
                .firstOrNull()
        }

        // GROW cellIdx | SEED sourceIdx targetIdx | COMPLETE cellIdx | WAIT <message>
        println(completeAction ?: growAction ?: seedAction ?: "WAIT")
    }
}
