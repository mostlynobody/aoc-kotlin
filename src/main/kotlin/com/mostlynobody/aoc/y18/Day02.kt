package com.mostlynobody.aoc.y18

import com.mostlynobody.aoc.Day

class Day02(rawInput: String) : Day(rawInput) {

    override fun silver(): String {
        var twins = 0
        var triplets = 0

        val twinRegex = """(.)\1""".toRegex()
        val tripletRegex = """(.)\1{2}""".toRegex()

        rawInput.lines().map { it.toCharArray().sorted().joinToString("") }.forEach {
            if (twinRegex.containsMatchIn(it)) twins++
            if (tripletRegex.containsMatchIn(it)) triplets++
        }

        return (twins * triplets).toString()
    }

    override fun gold(): String {
        val lines = rawInput.lines()
        for (i in lines.indices) {
            for (j in i + 1 until lines.size) {
                if (fuzzyMatch(lines[i], lines[j])) return getMatchingChars(lines[i], lines[j])
            }
        }
        return ""
    }


    private fun fuzzyMatch(s1: String, s2: String, expectedDiff: Int = 1): Boolean {
        var diff = 0
        s1.zip(s2).forEach {
            if (it.first != it.second) diff++
            if (diff > expectedDiff) return false
        }
        return diff == expectedDiff
    }

    private fun getMatchingChars(s1: String, s2: String): String {
        return s1.zip(s2).filter { it.first == it.second }.map { it.first }.joinToString("")
    }
}