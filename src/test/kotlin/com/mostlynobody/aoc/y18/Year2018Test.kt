package com.mostlynobody.aoc.y18

import com.mostlynobody.aoc.TestUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Year2018Test {

    @Test
    fun testDay01() {
        val rawInput = TestUtils.getInput(2018, 1)
        val day = Day01(rawInput)

        assertEquals("484", day.silver())
        assertEquals("367", day.gold())
    }

    @Test
    fun testDay02() {
        val rawInput = TestUtils.getInput(2018, 2)
        val day = Day02(rawInput)

        assertEquals("5000", day.silver())
        assertEquals("ymdrchgpvwfloluktajxijsqb", day.gold())
    }

    @Test
    fun testDay03() {
        val rawInput = TestUtils.getInput(2018, 3)
        val day = Day03(rawInput)

        assertEquals("107663", day.silver())
        assertEquals("1166", day.gold())
    }
}