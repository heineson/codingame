import java.util.*
import java.io.*
import java.math.*

data class Factory(val id: Int, val owner: Int, val cyborgs: Int, val production: Int)
data class Troop(val id: Int, val owner: Int, val sourceId: Int, val targetId: Int, val troopCount: Int, val turnsToArrival: Int)
data class FactoryLink(val f1: Int, val f2: Int, val distance: Int)

data class FactoriesByOwner(val my: List<Factory>, val neutral: List<Factory>, val enemy: List<Factory>)
fun factoriesByOwner(factories: Collection<Factory>): FactoriesByOwner {
    val grouped = factories.groupBy { it.owner }
    return FactoriesByOwner(grouped[1] ?: emptyList(), grouped[0] ?: emptyList(), grouped[-1] ?: emptyList())
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
    val troopsState = mutableMapOf<Int, Troop>()

    // game loop
    while (true) {
        val entityCount = input.nextInt() // the number of entities (e.g. factories and troops)
        for (i in 0 until entityCount) {
            val entityId = input.nextInt()
            val entityType = input.next()
            val arg1 = input.nextInt()
            val arg2 = input.nextInt()
            val arg3 = input.nextInt()
            val arg4 = input.nextInt()
            val arg5 = input.nextInt()
            if (entityType == "FACTORY") {
                factoriesState[entityId] = Factory(entityId, arg1, arg2, arg3)
            } else {
                troopsState[entityId] = Troop(entityId, arg1, arg2, arg3, arg4, arg5)
            }
        }

        val (myF, neutralF, enemyF) = factoriesByOwner(factoriesState.values)

        val target: Int? = if (neutralF.isEmpty())
            enemyF.map { it.id }.shuffled().firstOrNull()
        else neutralF
                .filter { !(troopsState.values.map { v -> v.targetId }.contains(it.id)) }
                .map { it.id }
                .shuffled()
                .firstOrNull()

        val countToSend: Int = (factoriesState[target]?.cyborgs ?: 0) + 2

        val source: Int? = myF.filter { it.cyborgs > countToSend + 1 }.shuffled().firstOrNull()?.id

        // Write an action using println()
        // To debug: System.err.println("Debug messages...");
        val op = if (source != null && target != null)
            "MOVE $source $target $countToSend" else "WAIT"

        // Any valid action, such as "WAIT" or "MOVE source destination cyborgs"
        println(op)
    }
}
