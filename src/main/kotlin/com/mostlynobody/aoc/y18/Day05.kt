package com.mostlynobody.aoc.y18

import com.mostlynobody.aoc.Day
import kotlin.math.abs

class Day05(rawInput: String) : Day(rawInput) {

    override fun silver(): String {
        return collapsePolymer(rawInput.trim().toMutableList()).toString()
    }

    override fun gold(): String {
        val polymer = rawInput.trim().toList()
        return ('a'..'z').map { collapsePolymer(improvePolymer(polymer.toMutableList(), it)) }.min().toString()
    }

    private fun improvePolymer(polymer: MutableList<Char>, c: Char): MutableList<Char> {
        polymer.removeAll(setOf(c, c.uppercaseChar()))
        return polymer
    }

    private fun collapsePolymer(polymer: MutableList<Char>): Int {
        var i = 0
        while (i < polymer.size - 1) {
            if (abs(polymer[i] - polymer[i + 1]) == 32) {
                polymer.removeAt(i)
                polymer.removeAt(i)
                i = 0.coerceAtLeast(i - 1)
            } else i++
        }

        return polymer.size
    }
}