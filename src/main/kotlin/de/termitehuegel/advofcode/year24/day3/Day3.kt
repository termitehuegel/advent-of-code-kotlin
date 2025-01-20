package de.termitehuegel.advofcode.year24.day3

import java.io.FileInputStream
import java.io.InputStream
import kotlin.system.measureTimeMillis


fun parse(input: InputStream): String {
    return input.reader().readLines().joinToString("")
}

fun multiply(corruptedMemory: String): Int {
    return Regex("mul\\(([1-9][0-9]{0,2}),([1-9][0-9]{0,2})\\)").findAll(corruptedMemory)
        .sumOf { match -> match.groupValues[1].toInt()  * match.groupValues[2].toInt() }
}

fun multiplyConditional(corruptedMemory: String): Int {
    var memory = corruptedMemory

    while (Regex("don't\\(\\).*?do\\(\\)").containsMatchIn(memory)) {
        memory = Regex("don't\\(\\).*?do\\(\\)").replace(memory, "do()")
    }
    memory = memory.replace(Regex("don't.*"), "")

    return multiply(memory)
}

fun main() {
    val input = "src/main/resources/input.txt" // Input files are not provided in this repo
    val parsedInput = parse(FileInputStream(input))

    var result: Int = 0
    var duration: Long = 0L

    // Solving Part One
    duration = measureTimeMillis {
        result = multiply(parsedInput)
    }
    println("Calculated the number of safe reports as $result in ${duration}ms")

    // Solving Part Two
    duration = measureTimeMillis {
        result = multiplyConditional(parsedInput)
    }
    println("Calculated the number of safe reports when the dampener is taken into account as $result in ${duration}ms")
}