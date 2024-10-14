package com.mostlynobody.aoc.y18

import com.mostlynobody.aoc.Day

class Day01(rawInput: String) : Day(rawInput) {

    override fun silver(): String {
        return rawInput.lines().sumOf { s -> s.toInt() }.toString()
    }

    override fun gold(): String {
        val set = HashSet<Int>()
        var sum = 0
        rawInput.lines().map { s -> s.toInt() }.asSequence().repeat().forEach {
            sum += it
            if (set.contains(sum)) return sum.toString() else set.add(sum)
        }
        return ""
    }

    /***
     * Iterate over a sequence an infinite amount of times, I wanted to try a qualified this
     */
    private fun <T> Sequence<T>.repeat() = sequence { while (true) yieldAll(this@repeat) }
}