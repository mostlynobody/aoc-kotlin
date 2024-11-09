package com.mostlynobody.aoc.y18

import com.mostlynobody.aoc.Day

class Day11(rawInput: String) : Day(rawInput) {

    var serialNumber: Int = rawInput.lines().first().toInt()
    val powerCells = Array(300) { IntArray(300) { 0 } }

    override fun silver(): String {
        powerCells.forEachIndexed { x, row ->
            row.forEachIndexed { y, cell ->
                powerCells[x][y] = calculatePowerLevel(x + 1, y + 1)
            }
        }

        return getMaxPowerGrid(3).second.dropLast(2)
    }

    override fun gold(): String {
        powerCells.forEachIndexed { x, row ->
            row.forEachIndexed { y, cell ->
                powerCells[x][y] = calculatePowerLevel(x + 1, y + 1)
            }
        }

        return (1..300).map { getMaxPowerGrid(it) }.maxBy { it.first }.second
    }

    private fun getMaxPowerGrid(squareSize: Int): Pair<Int, String> {
        var coordinateString = ""
        var max = Int.MIN_VALUE
        var m = (0..0 + squareSize - 1).sumOf<Int> { i -> (0..0 + squareSize - 1).sumOf<Int> { j -> powerCells[i][j] } }

        for (x in 0..300 - squareSize) {
            if (x > 0) {
                m -= (0..squareSize - 1).sumOf { tempY -> powerCells[x - 1][tempY] }
                m += (0..squareSize - 1).sumOf { tempY -> powerCells[x + squareSize - 1][tempY] }
            }

            var n = m
            for (y in 0..300 - squareSize) {
                if (y > 0) {
                    n -= (x..x + squareSize - 1).sumOf { tempX -> powerCells[tempX][y - 1] }
                    n += (x..x + squareSize - 1).sumOf { tempX -> powerCells[tempX][y + squareSize - 1] }
                }

                if (n > max) {
                    max = n
                    coordinateString = "${x + 1},${y + 1},$squareSize"
                }
            }
        }

        return Pair(max, coordinateString)
    }

    private fun calculatePowerLevel(x: Int, y: Int): Int {
        return ((((((x + 10) * y) + serialNumber) * (x + 10)) / 100) % 10) - 5
    }
}