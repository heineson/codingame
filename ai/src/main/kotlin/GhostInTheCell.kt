import java.util.*
import java.io.*
import java.math.*

data class Factory(val id: Int, val owner: Int, val cyborgs: Int, val production: Int)
data class Troop(val id: Int, val owner: Int, val sourceId: Int, val targetId: Int, val troopCount: Int, val turnsToArrival: Int)
data class Bomb(val id: Int, val owner: Int, val sourceId: Int, val targetId: Int, val turnsToArrival: Int)
data class FactoryLink(val f1: Int, val f2: Int, val distance: Int)

data class FactoriesByOwner(val my: List<Factory>, val neutral: List<Factory>, val enemy: List<Factory>)
fun factoriesByOwner(factories: Collection<Factory>): FactoriesByOwner {
    val grouped = factories.groupBy { it.owner }
    return FactoriesByOwner(grouped[1] ?: emptyList(), grouped[0] ?: emptyList(), grouped[-1] ?: emptyList())
}

fun getSourceFactory(my: List<Factory>, target: Factory): Factory? {
    return my.filter { it.cyborgs > target.cyborgs + 2 }.shuffled().firstOrNull()
}

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
fun main(args : Array<String>) {
    val input = Scanner(System.`in`)
    val factoryCount = input.nextInt() // the number of factories
    val linkCount = input.nextInt() // the number of links between factories
    var links = listOf<FactoryLink>();
    for (i in 0 until linkCount) {
        links = links + FactoryLink(input.nextInt(), input.nextInt(), input.nextInt())
    }
    val factoriesState = mutableMapOf<Int, Factory>()
    var remainingBombs = 2

    // game loop
    while (true) {
        val troopsState = mutableMapOf<Int, Troop>()
        val bombsState = mutableMapOf<Int, Bomb>()

        val entityCount = input.nextInt() // the number of entities (e.g. factories and troops)
        for (i in 0 until entityCount) {
            val entityId = input.nextInt()
            val entityType = input.next()
            val arg1 = input.nextInt()
            val arg2 = input.nextInt()
            val arg3 = input.nextInt()
            val arg4 = input.nextInt()
            val arg5 = input.nextInt()
            when (entityType) {
                "FACTORY" -> factoriesState[entityId] = Factory(entityId, arg1, arg2, arg3)
                "TROOP" -> troopsState[entityId] = Troop(entityId, arg1, arg2, arg3, arg4, arg5)
                "BOMB" -> bombsState[entityId] = Bomb(entityId, arg1, arg2, arg3, arg4)
            }
        }

        val (myF, neutralF, enemyF) = factoriesByOwner(factoriesState.values)

        val ops = mutableListOf<String>()
        val noOfOps = 4

        // First, attack up to 4 neutrals which no friendly troops already target
        neutralF
            .filter { !(troopsState.values.map { v -> v.targetId }.contains(it.id)) }
            .shuffled()
            .take(noOfOps)
            .forEach { target ->
                val source = getSourceFactory(myF, target)
                val countToSend: Int = target.cyborgs + 2
                if (source != null) {
                    ops.add("MOVE ${source.id} ${target.id} $countToSend")
                }
            }

        // Second, if < 4 ops then attack enemy which no friendly troops already target
        if (ops.size < noOfOps) {
            enemyF
                .filter { !(troopsState.values.map { v -> v.targetId }.contains(it.id)) }
                .shuffled()
                .take(noOfOps - ops.size)
                .forEach { target ->
                    val source = getSourceFactory(myF, target)
                    val countToSend: Int = target.cyborgs + 2
                    System.err.println("Enemy ${source?.id} ${target.id} $countToSend")
                    if (source != null) {
                        ops.add("MOVE ${source.id} ${target.id} $countToSend")
                    }
                }
        }

        if (ops.isEmpty()) { ops.add("WAIT") }

        // Write an action using println()
        // To debug: System.err.println("Debug messages...");

        // Any valid action, such as "WAIT" or "MOVE source destination cyborgs"
        println(ops.joinToString(";"))
    }
}
