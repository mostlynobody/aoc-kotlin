package com.mostlynobody.aoc.y18

import com.mostlynobody.aoc.Day
import java.util.*

class Day04(rawInput: String) : Day(rawInput) {

    override fun silver(): String {
        val guards = calculateGuards()
        val sleepyGuard = guards.maxByOrNull { it.value.sum() }!!
        return (sleepyGuard.key * sleepyGuard.value.indexOf(sleepyGuard.value.max())).toString()
    }

    override fun gold(): String {
        val guards = calculateGuards()
        val sleepyGuard = guards.maxByOrNull { it.value.max() }!!
        return (sleepyGuard.key * sleepyGuard.value.indexOf(sleepyGuard.value.max())).toString()
    }

    private fun calculateGuards(): HashMap<Int, IntArray> {
        val logQueue = rawInput.lines().sorted().toCollection(LinkedList())
        val guards = HashMap<Int, IntArray>()

        while (logQueue.isNotEmpty()) {
            val guardId = logQueue.removeFirst().substringAfter('#').removeSuffix(" begins shift").toInt()
            val guard = guards.getOrPut(guardId) { IntArray(60) }

            while (logQueue.isNotEmpty() && !logQueue.peek().contains("Guard")) {
                val startMinute = logQueue.removeFirst().substring(15, 17).toInt()
                val endMinute = logQueue.removeFirst().substring(15, 17).toInt()
                for (i in startMinute until endMinute) guard[i]++
            }
        }

        return guards
    }
}