package com.mostlynobody.aoc.y18

import com.mostlynobody.aoc.Day
import kotlin.math.abs

class Day06(rawInput: String) : Day(rawInput) {


    override fun silver(): String {
        var origins = createOriginsFromInput()
        val width = origins.maxOf { it.x }
        val height = origins.maxOf { it.y }

        // calculate area inside bound for all non-infinite areas
        (1 until width).toSet().cartesianProduct((1 until height).toSet())
            .forEach { findClosestCoordinate(it.first, it.second, origins)?.area++ }

        // collect all origins that are on the outer bound ( = and extend into infinity)
        val infiniteOrigins =
            ((setOf(0, height).cartesianProduct((0..width).toSet()))
                    + (setOf(0, width).cartesianProduct((0..height).toSet())))
                .map { findClosestCoordinate(it.first, it.second, origins) }.filterNotNull().toSet()

        return (origins - infiniteOrigins).maxOf { it.area }.toString()
    }

    override fun gold(): String {
        val origins = createOriginsFromInput()
        val width = origins.maxOf { it.x }
        val height = origins.maxOf { it.y }

        return (0..width).toSet().cartesianProduct((0..height).toSet())
            .map { origins.map { o -> abs(it.first - o.x) + abs(it.second - o.y) }.sum() }.filter { it < 10000 }.count()
            .toString()
    }

    private fun createOriginsFromInput(): List<Origin> {
        val origins = rawInput.lines().map { it.split(", ") }.map { Origin(it[0].toInt(), it[1].toInt()) }
        val offsetX = origins.minOf { it.x }
        val offsetY = origins.minOf { it.y }
        origins.forEach { it.x -= offsetX; it.y -= offsetY }
        return origins
    }

    private fun findClosestCoordinate(x: Int, y: Int, origins: List<Origin>): Origin? {
        return origins.groupBy { abs(x - it.x) + abs(y - it.y) }.minByOrNull { it.key }?.value?.singleOrNull()
    }

    private fun <T> Set<T>.cartesianProduct(other: Set<T>): Set<Pair<T, T>> {
        return this.flatMapTo(HashSet()) {
            other.map { otherIt ->
                it to otherIt
            }
        }
    }

    private data class Origin(var x: Int, var y: Int, var area: Int = 0)
}