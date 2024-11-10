package com.mostlynobody.aoc.y18

import com.mostlynobody.aoc.Day
import kotlin.math.log10

class Day14(rawInput: String) : Day(rawInput) {

    override fun silver(): String {
        val neededRecipes = rawInput.toInt()
        val recipes: ArrayList<Int> = arrayListOf(3, 7)
        var firstElf = 0
        var secondElf = 1

        while (recipes.size < neededRecipes + 10) {
            recipes.addAll(getRecipesFromInt(recipes[firstElf] + recipes[secondElf]))
            firstElf = (firstElf + 1 + recipes[firstElf]) % recipes.size
            secondElf = (secondElf + 1 + recipes[secondElf]) % recipes.size
        }

        return recipes.subList(neededRecipes, neededRecipes + 10).joinToString("")
    }

    override fun gold(): String {
        val neededRecipes = getRecipesFromInt(rawInput.toInt())
        val recipes: ArrayList<Int> = arrayListOf(3, 7)
        var firstElf = 0
        var secondElf = 1

        //get a few recipes into the list, so I don't have to do boundary checks all the time
        repeat(20) {
            recipes.addAll(getRecipesFromInt(recipes[firstElf] + recipes[secondElf]))
            firstElf = (firstElf + 1 + recipes[firstElf]) % recipes.size
            secondElf = (secondElf + 1 + recipes[secondElf]) % recipes.size
        }

        var checkTailIndex = neededRecipes.size
        while (true) {
            recipes.addAll(getRecipesFromInt(recipes[firstElf] + recipes[secondElf]))
            firstElf = (firstElf + 1 + recipes[firstElf]) % recipes.size
            secondElf = (secondElf + 1 + recipes[secondElf]) % recipes.size

            while (checkTailIndex < recipes.size) {
                if (recipes.subList(checkTailIndex - neededRecipes.size, checkTailIndex) == neededRecipes) {
                    return (checkTailIndex - neededRecipes.size).toString()
                }
                checkTailIndex++
            }
        }

        return "0"
    }

    private fun getRecipesFromInt(num: Int): List<Int> {
        if (num < 10) return listOf(num)
        val digitCount = (log10(num.toDouble()) + 1).toInt()
        val result = mutableListOf<Int>()
        var m = num
        repeat(digitCount) {
            result.addFirst(m % 10)
            m /= 10
        }

        return result
    }
}