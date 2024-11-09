package com.mostlynobody.aoc.y18

import com.mostlynobody.aoc.Day

class Day12(rawInput: String) : Day(rawInput) {


    override fun silver(): String {
        val initialState = rawInput.lines().first().substringAfter("initial state: ")
        val patterns = rawInput.lines().drop(2).map { GrowthPattern(it.take(5), it.last()) }

        val plantPots = PlantPots(initialState, patterns)
        repeat(20) { plantPots.advanceGeneration() }

        return plantPots.getSumOfFilledPots().toString()
    }

    override fun gold(): String {
        val initialState = rawInput.lines().first().substringAfter("initial state: ")
        val patterns = rawInput.lines().drop(2).map { GrowthPattern(it.take(5), it.last()) }

        val plantPots = PlantPots(initialState, patterns)
        var generation = 0
        var cur = plantPots.getSumOfFilledPots()
        var old = cur
        val slidingWindow = IntArray(10) { 0 }
        slidingWindow[0] = cur

        // wait until increase becomes linear
        do {
            old = cur
            generation++
            plantPots.advanceGeneration()
            cur = plantPots.getSumOfFilledPots()
            slidingWindow[generation % 10] = cur - old
        } while (slidingWindow.distinct().size != 1)

        // calculate size after 50 billion generations
        return (plantPots.getSumOfFilledPots() + ((cur - old) * (50_000_000_000 - generation))).toString()
    }

    private data class PlantPots(var state: String, val patterns: List<GrowthPattern>, var offset: Int = 0) {
        fun checkOffset() {
            if (state.take(5).contains('#')) {
                state = ".....$state"
                offset += 5
            }

            if (state.takeLast(5).contains('#')) {
                state = "$state....."
            }
        }

        fun advanceGeneration() {
            checkOffset()
            val nextStateArray = state.toCharArray()
            for (i in 2 until state.length - 2) {
                nextStateArray[i] = patterns.filter { it.pattern == state.substring(i - 2, i + 3) }.first().result
            }

            state = nextStateArray.joinToString("")
        }

        fun getSumOfFilledPots(): Int {
            return state.mapIndexed { index, char -> if (char == '#') index - offset else 0 }.sum()
        }
    }

    private data class GrowthPattern(val pattern: String, val result: Char)
}