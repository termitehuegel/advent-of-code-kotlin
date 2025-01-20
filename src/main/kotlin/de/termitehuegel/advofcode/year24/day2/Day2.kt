package de.termitehuegel.advofcode.year24.day2

import java.io.FileInputStream
import java.io.InputStream
import kotlin.system.measureTimeMillis


fun parse(input: InputStream): List<List<Int>> {
    return input.reader().readLines().map { line -> line.split(" ").map { it.toInt() } }
}

fun safe(report: List<Int>): Boolean {
    var decrementing: Boolean? = null

    for (i in 1..<report.size) {
        val levelDifference = report[i] - report[i - 1]
        if (levelDifference in 1..3 && decrementing != true) {
            decrementing = false
        } else if (levelDifference in -3..-1 && decrementing != false) {
            decrementing = true
        } else {
            return false
        }
    }
    return true
}

fun safeCount(reports: List<List<Int>>): Int {
    return reports.count { safe(it) }
}

fun safeWithDampener(report: List<Int>): Boolean {
    var decrementing: Boolean? = null

    for (i in 1..<report.size) {
        val levelDifference = report[i] - report[i - 1]
        if (levelDifference in 1..3 && decrementing != true) {
            decrementing = false
        } else if (levelDifference in -3..-1 && decrementing != false) {
            decrementing = true
        } else {
            // May not be the best way, but it works in an acceptable amount of time
            return safe(report.filterIndexed { index, _ -> index != 0 }) || // try without the first element to account for errors in decrementing / incrementing that might come up two levels later
                    safe(report.filterIndexed { index, _ -> index != i - 1 }) ||
                    safe(report.filterIndexed { index, _ -> index != i })
        }
    }
    return true
}

fun safeCountWithDampener(reports: List<List<Int>>): Int {
    return reports.count { safeWithDampener(it) }
}


fun main() {
    val input = "src/main/resources/input.txt" // Input files are not provided in this repo
    val parsedInput = parse(FileInputStream(input))

    var result: Int = 0
    var duration: Long = 0L

    // Solving Part One
    duration = measureTimeMillis {
        result = safeCount(parsedInput)
    }
    println("Calculated the number of safe reports as $result in ${duration}ms")

    // Solving Part Two
    duration = measureTimeMillis {
        result = safeCountWithDampener(parsedInput)
    }
    println("Calculated the number of safe reports when the dampener is taken into account as $result in ${duration}ms")
}