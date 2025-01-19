package de.termitehuegel.advofcode.year24

import java.io.FileInputStream
import java.io.InputStream
import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun parse(input: InputStream): List<List<Int>> {
    val lists = input.reader().readLines().map { line -> line.split("   ").map { it.toInt() } }
    return listOf(lists.map { list -> list[0] }, lists.map { list -> list[1] })
}

fun distance(a: List<Int>, b: List<Int>): Int {
    return a.sorted().zip(b.sorted()).sumOf { abs(it.first - it.second) }
}

fun similarity(a: List<Int>, b: List<Int>): Int {
    return a.sumOf { left -> left * b.count { right -> left == right } }
}

/**
 * Might increase the execution time for large lists with many duplicates (not necessarily given for the input)
 */
fun similarityWithCache(a: List<Int>, b: List<Int>): Int {
    val cache: MutableMap<Int, Int> = HashMap<Int, Int>()

    return a.sumOf { left ->
        if (cache.containsKey(left)) {
            println("Cache hit")
            return@sumOf cache[left]!!
        }
        val multiple = left * b.count { right -> left == right }
        cache[left] = multiple
        return@sumOf multiple
    }
}

fun main() {
    val input = "src/main/resources/input.txt" // Input files are not provided in this repo
    val parsedInput = parse(FileInputStream(input))

    var result: Int = 0;
    var duration: Long = 0L;

    // Solving Part One
    duration = measureTimeMillis {
        result = distance(parsedInput[0], parsedInput[1])
    }
    println("Calculated a difference for the provided lists of $result in ${duration}ms")

    // Solving Part Two
    duration = measureTimeMillis {
        result = similarity(parsedInput[0], parsedInput[1])
    }
    println("Calculated a similarity score for the provided lists of $result in ${duration}ms")

    // Solving Part Two a second time
    duration = measureTimeMillis {
        result = similarityWithCache(parsedInput[0], parsedInput[1])
    }
    println("Calculated a similarity score for the provided lists of $result in ${duration}ms")
}