package de.termitehuegel.advofcode.year24.day5

import java.io.FileInputStream
import java.io.InputStream
import java.util.HashMap
import kotlin.system.measureTimeMillis


data class Input(
    val rules: MutableMap<Int, MutableSet<Int>>,
    val updates: List<List<Int>>,
)


fun parse(input: InputStream): Input {
    val lines: List<String> = input.reader().readLines()
    val splitIndex: Int = lines.indexOfFirst { it == "" }
    val rules: MutableMap<Int, MutableSet<Int>> = HashMap()
    lines.subList(0, splitIndex)
        .map { rule -> rule.split("|").map { it.toInt() } }
        .forEach { rule ->
            if (!rules.containsKey(rule[0])) {
                rules[rule[0]] = mutableSetOf(rule[1])
                return@forEach
            }
            rules[rule[0]]?.add(rule[1])
        }
    val updates: List<List<Int>> =
        lines.subList(splitIndex + 1, lines.size).map { update -> update.split(",").map { it.toInt() } }

    return Input(rules, updates)
}

fun correctUpdates(rules: MutableMap<Int, MutableSet<Int>>, updates: List<List<Int>>): List<List<Int>> {
    return updates.filter { update ->
        for (i in update.indices) {
            if (!rules.containsKey(update[i])) continue
            val rule: Set<Int> = rules[update[i]]!!
            for (j in i - 1 downTo 0) {
                if (rule.contains(update[j])) return@filter false
            }
        }
        true
    }
}

fun sumListMiddles(lists: List<List<Int>>): Int {
    return lists.sumOf { list -> list[list.size / 2] }
}

fun incorrectUpdates(rules: MutableMap<Int, MutableSet<Int>>, updates: List<List<Int>>): List<List<Int>> {
    return updates.filter { update ->
        for (i in update.indices) {
            if (!rules.containsKey(update[i])) continue
            val rule: Set<Int> = rules[update[i]]!!
            for (j in i - 1 downTo 0) {
                if (rule.contains(update[j])) return@filter true
            }
        }
        false
    }
}

/**
 * Could be done in a better way, but this one is easy and is fast enough
 */
fun fixUpdates(rules: Map<Int, MutableSet<Int>>, updates: List<List<Int>>): List<List<Int>> {
    val mutableUpdates = updates.map { update -> update.toMutableList() }
    mutableUpdates.forEach { update ->
        var change = true
        while (change) {
            change = false
            for (i in update.indices) {
                if (!rules.containsKey(update[i])) continue
                val rule: Set<Int> = rules[update[i]]!!
                for (j in i - 1 downTo 0) {
                    if (rule.contains(update[j])) {
                        val temp = update[j]
                        update[j] = update[i]
                        update[i] = temp
                        change = true
                    }
                }
            }
        }
    }
    return mutableUpdates
}


fun main() {
    val input = "src/main/resources/input.txt" // Input files are not provided in this repo
    val (rules, updates) = parse(FileInputStream(input))

    var result: Int = 0
    var duration: Long = 0L

    // Solving Part One
    duration = measureTimeMillis {
        result = sumListMiddles(correctUpdates(rules, updates))
    }
    println("Calculated sum of $result in ${duration}ms")

    // Solving Part Two
    duration = measureTimeMillis {
        result = sumListMiddles(fixUpdates(rules, incorrectUpdates(rules, updates)))
    }
    println("Counted $result Crosses made up of MAS in ${duration}ms")
}