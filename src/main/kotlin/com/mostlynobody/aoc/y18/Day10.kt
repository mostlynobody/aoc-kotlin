package com.mostlynobody.aoc.y18

import com.mostlynobody.aoc.Day
import com.mostlynobody.aoc.Vector2D


class Day10(rawInput: String) : Day(rawInput) {

    override fun silver(): String {
        val lights = getLights(rawInput)
        repeat(100_000) {
            lights.forEach { it.position = it.position.add(it.velocity) }
            val minY = lights.map { it.position.y }.minOrNull()
            if (lights.map { it.position.y }.filter { it == minY }.count() > 5) {
                return getLightsAsString(lights)
            }
        }

        return "No match"
    }

    override fun gold(): String {
        val lights = getLights(rawInput)

        for (i in 1..100_000) {
            lights.forEach { it.position = it.position.add(it.velocity) }
            val minY = lights.map { it.position.y }.minOrNull()
            if (lights.map { it.position.y }.filter { it == minY }.count() > 5) {
                return i.toString()
            }
        }

        return "0"
    }

    private fun getLights(input: String): List<Light> {
        val regex = """<\s*(-?\d+),\s*(-?\d+)>\s*velocity=<\s*(-?\d+),\s*(-?\d+)>""".toRegex()
        return input.lines().map {
            val matches = regex.find(it)!!.groups.drop(1).map { it!!.value.toDouble() }
            Light.create(matches[0], matches[1], matches[2], matches[3])
        }.toList()
    }

    private fun getLightsAsString(lights: List<Light>): String {
        val positions = lights.map { it.position }
        val offset = Vector2D(positions.minOf { it.x }, positions.minOf { it.y })
        val size = Vector2D(positions.maxOf { it.x }, positions.maxOf { it.y }).subtract(offset)

        val charGrid = Array(size.y.toInt() + 1) { CharArray(size.x.toInt() + 1) { ' ' } }
        positions.map { it.subtract(offset) }.forEach { charGrid[it.y.toInt()][it.x.toInt()] = '*' }
        return charGrid.map { (it.joinToString("")) }.joinToString("\n")
    }

    private data class Light(val origin: Vector2D, var position: Vector2D, val velocity: Vector2D) {

        companion object Factory {

            fun create(x: Double, y: Double, velocityX: Double, velocityY: Double): Light {
                return Light(Vector2D(x, y), Vector2D(x, y), Vector2D(velocityX, velocityY))
            }
        }
    }
}