package de.termitehuegel.advofcode.year24.day4

import java.io.FileInputStream
import java.io.InputStream
import kotlin.system.measureTimeMillis

fun parse(input: InputStream): List<List<Char>> {
    return input.reader().readLines().map { line -> line.toCharArray().toList() }
}

fun xmasCount(grid: List<List<Char>>): Int {
    var count: Int = 0
    for (x in grid.indices) {
        for (y in grid[x].indices) {
            if (grid[x][y] != 'X') continue
            // Vertical
            if (y + 3 < grid[x].size && grid[x][y + 1] == 'M' && grid[x][y + 2] == 'A' && grid[x][y + 3] == 'S') count++
            if (y - 3 >= 0 && grid[x][y - 1] == 'M' && grid[x][y - 2] == 'A' && grid[x][y - 3] == 'S') count++
            // Horizontal
            if (x + 3 < grid.size && grid[x + 1][y] == 'M' && grid[x + 2][y] == 'A' && grid[x + 3][y] == 'S') count++
            if (x - 3 >= 0 && grid[x - 1][y] == 'M' && grid[x - 2][y] == 'A' && grid[x - 3][y] == 'S') count++
            //Diagonals
            if (x + 3 < grid.size && y + 3 < grid[x].size && grid[x + 1][y + 1] == 'M' && grid[x + 2][y + 2] == 'A' && grid[x + 3][y + 3] == 'S') count++
            if (x - 3 >= 0 && y - 3 >= 0 && grid[x - 1][y - 1] == 'M' && grid[x - 2][y - 2] == 'A' && grid[x - 3][y - 3] == 'S') count++
            if (x + 3 < grid.size && y - 3 >= 0 && grid[x + 1][y - 1] == 'M' && grid[x + 2][y - 2] == 'A' && grid[x + 3][y - 3] == 'S') count++
            if (x - 3 >= 0 && y + 3 < grid[x].size && grid[x - 1][y + 1] == 'M' && grid[x - 2][y + 2] == 'A' && grid[x - 3][y + 3] == 'S') count++
        }
    }
    return count
}

fun masCrossCount(grid: List<List<Char>>): Int {
    var count: Int = 0
    for (x in grid.indices) {
        for (y in grid[x].indices) {
            if (grid[x][y] != 'A') continue
            if (x - 1 < 0 || x + 1 >= grid.size || y - 1 < 0 || y + 1 >= grid[x].size) continue
            if (
                ((grid[x - 1][y - 1] == 'M' && grid[x + 1][y + 1] == 'S') || (grid[x - 1][y - 1] == 'S' && grid[x + 1][y + 1] == 'M')) // Diagonal bottom left to top right
                &&
                ((grid[x - 1][y + 1] == 'M' && grid[x + 1][y - 1] == 'S') || (grid[x - 1][y + 1] == 'S' && grid[x + 1][y - 1] == 'M')) // Diagonal top left to bottom right
            ) {
                count++
            }
        }
    }
    return count
}


fun main() {
    val input = "src/main/resources/input.txt" // Input files are not provided in this repo
    val parsedInput = parse(FileInputStream(input))

    var result: Int = 0
    var duration: Long = 0L

    // Solving Part One
    duration = measureTimeMillis {
        result = xmasCount(parsedInput)
    }
    println("Counted XMAS $result times in ${duration}ms")

    // Solving Part Two
    duration = measureTimeMillis {
        result = masCrossCount(parsedInput)
    }
    println("Counted $result Crosses made up of MAS in ${duration}ms")
}