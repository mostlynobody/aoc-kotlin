package com.mostlynobody.aoc

import java.io.BufferedReader
import java.io.File

object TestUtils {

    fun getInput(year: Int, day: Int, dropLast: Boolean = true): String {
        val bufferedReader: BufferedReader =
            File(String.format("src/test/resources/%d/input%02d", year, day)).bufferedReader()
        val inputString = bufferedReader.use { it.readText() }

        return if (dropLast) inputString.dropLast(1)
        else inputString
    }
}