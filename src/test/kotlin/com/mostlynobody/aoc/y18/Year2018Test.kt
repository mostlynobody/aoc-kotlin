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

    @Test
    fun testDay04() {
        val rawInput = TestUtils.getInput(2018, 4)
        val day = Day04(rawInput)

        assertEquals("76357", day.silver())
        assertEquals("41668", day.gold())
    }

    @Test
    fun testDay05() {
        val rawInput = TestUtils.getInput(2018, 5)
        val day = Day05(rawInput)

        assertEquals("10496", day.silver())
        assertEquals("5774", day.gold())
    }

    @Test
    fun testDay06() {
        val rawInput = TestUtils.getInput(2018, 6)
        val day = Day06(rawInput)

        assertEquals("5626", day.silver())
        assertEquals("46554", day.gold())
    }

    @Test
    fun testDay07() {
        val rawInput = TestUtils.getInput(2018, 7)
        val day = Day07(rawInput)

        assertEquals("BKCJMSDVGHQRXFYZOAULPIEWTN", day.silver())
        assertEquals("1040", day.gold())
    }

    @Test
    fun testDay08() {
        val rawInput = TestUtils.getInput(2018, 8)
        val day = Day08(rawInput)

        assertEquals("47112", day.silver())
        assertEquals("28237", day.gold())
    }

    @Test
    fun testDay09() {
        val rawInput = TestUtils.getInput(2018, 9)
        val day = Day09(rawInput)

        assertEquals("374287", day.silver())
        assertEquals("3083412635", day.gold())
    }

    @Test
    fun testDay10() {
        val rawInput = TestUtils.getInput(2018, 10)
        val day = Day10(rawInput)

        assertEquals(
            """
            *****    ****   *****   *    *  *    *  *    *  *    *    **  
            *    *  *    *  *    *  *   *   *    *  *   *   **   *   *  * 
            *    *  *       *    *  *  *    *    *  *  *    **   *  *    *
            *    *  *       *    *  * *     *    *  * *     * *  *  *    *
            *****   *       *****   **      ******  **      * *  *  *    *
            *  *    *  ***  *  *    **      *    *  **      *  * *  ******
            *   *   *    *  *   *   * *     *    *  * *     *  * *  *    *
            *   *   *    *  *   *   *  *    *    *  *  *    *   **  *    *
            *    *  *   **  *    *  *   *   *    *  *   *   *   **  *    *
            *    *   *** *  *    *  *    *  *    *  *    *  *    *  *    *
        """.trimIndent(), day.silver()
        )
        assertEquals("10117", day.gold())
    }
}