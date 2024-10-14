package com.mostlynobody.aoc.y18

import com.mostlynobody.aoc.Day

class Day03(rawInput: String) : Day(rawInput) {

    override fun silver(): String {
        val fabric = Array(1000) { IntArray(1000) }
        val claims = rawInput.lines().map { Claim.fromString(it) }

        for (claim in claims) {
            for (x in claim.x until claim.x + claim.width) {
                for (y in claim.y until claim.y + claim.height) {
                    fabric[x][y]++
                }
            }
        }

        return fabric.flatMap { it.asIterable() }.count { it > 1 }.toString()
    }

    override fun gold(): String {
        val fabric = Array(1000) { IntArray(1000) }
        val claims = rawInput.lines().map { Claim.fromString(it) }
        val nonOverlappingClaims = HashSet<Int>()

        for (claim in claims) {
            val taints = HashSet<Int>()
            for (x in claim.x until claim.x + claim.width) {
                for (y in claim.y until claim.y + claim.height) {
                    if (fabric[x][y] != 0) taints.add(fabric[x][y])
                    fabric[x][y] = claim.id
                }
            }

            if (taints.isEmpty()) nonOverlappingClaims.add(claim.id)
            else nonOverlappingClaims.removeAll(taints)
        }

        return nonOverlappingClaims.first().toString()
    }

}

private data class Claim(val id: Int, val x: Int, val y: Int, val width: Int, val height: Int) {

    companion object Factory {
        fun fromString(string: String): Claim {
            val matches = """\d+""".toRegex().findAll(string).map { it.value.toInt() }.toList()
            return Claim(matches[0], matches[1], matches[2], matches[3], matches[4])
        }
    }
}