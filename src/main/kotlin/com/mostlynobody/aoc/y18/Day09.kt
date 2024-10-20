package com.mostlynobody.aoc.y18

import com.mostlynobody.aoc.Day

class Day09(rawInput: String) : Day(rawInput) {

    override fun silver(): String {
        val input = rawInput.split(" ").map { it.toIntOrNull() }.filterNotNull()
        val players = IntArray(input[0])
        val ring = MarbleRing(Marble.createZeroMarble())

        for (i in 1..input[1]) {
            if (i % 23 == 0) players[i % players.size] += i + ring.removeCounterClockwise(6)
            else ring.insertClockwise(i, 1)
        }

        return players.maxOrNull().toString()
    }

    override fun gold(): String {
        val input = rawInput.split(" ").map { it.toIntOrNull() }.filterNotNull()
        val players = LongArray(input[0])
        val ring = MarbleRing(Marble.createZeroMarble())

        for (i in 1..input[1] * 100) {
            if (i % 23 == 0) players[i % players.size] += i + ring.removeCounterClockwise(6)
            else ring.insertClockwise(i, 1)
        }

        return players.maxOrNull().toString()
    }

    private data class MarbleRing(var currentMarble: Marble) {

        fun insertClockwise(value: Int, distance: Int) {
            var before: Marble = currentMarble
            repeat(distance) { before = before.cw }

            val after = before.cw
            val newMarble = Marble(value)
            newMarble.cw = after
            newMarble.ccw = before

            currentMarble = newMarble
            before.cw = currentMarble
            after.ccw = currentMarble
        }

        fun removeCounterClockwise(distance: Int): Int {
            var before: Marble = currentMarble
            repeat(distance) { before = before.ccw }

            val removed = before.ccw
            before.ccw = removed.ccw
            before.ccw.cw = before
            currentMarble = before
            return removed.value
        }
    }

    private data class Marble(val value: Int) {
        lateinit var cw: Marble
        lateinit var ccw: Marble

        companion object Factory {
            fun createZeroMarble(): Marble {
                val m = Marble(0)
                m.cw = m
                m.ccw = m
                return m
            }
        }
    }
}
