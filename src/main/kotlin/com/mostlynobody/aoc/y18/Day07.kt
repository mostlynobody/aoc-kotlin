package com.mostlynobody.aoc.y18

import com.mostlynobody.aoc.Day
import kotlin.math.max

class Day07(rawInput: String) : Day(rawInput) {

    override fun silver(): String {
        val steps = sortedMapOf<Char, SimpleStep>()
        rawInput.lines().map { Pair(it[5], it[36]) }
            .forEach { (steps.getOrPut(it.second) { SimpleStep() }).requiredSteps.add(steps.getOrPut(it.first) { SimpleStep() }) }

        val stepOrder = StringBuilder()
        while (stepOrder.length < steps.size) {
            val nextStep = steps.filter { it.value.isReady() }.entries.first()
            stepOrder.append(nextStep.key)
            nextStep.value.finish()
        }

        return stepOrder.toString()
    }

    private data class SimpleStep(
        val requiredSteps: MutableList<SimpleStep> = mutableListOf(), private var finished: Boolean = false
    ) {
        fun isReady(): Boolean = !finished && requiredSteps.all { it.finished }
        fun finish() {
            finished = true
        }
    }

    override fun gold(): String {
        val steps = sortedMapOf<Char, TimedStep>()
        rawInput.lines().map { Pair(it[5], it[36]) }.forEach {
            (steps.getOrPut(it.second) { TimedStep(requiredTime = 61 + it.second.code - 'A'.code) }).requiredSteps.add(
                steps.getOrPut(it.first) { TimedStep(requiredTime = 61 + it.first.code - 'A'.code) })
        }

        var clk = 0
        var workers = Array(5) { Worker() }
        do {
            // make sure all workers that are done with their step mark it finished
            workers.forEach { it.checkWork(clk) }
            // if workers and steps are available, assign steps to workers
            while (workers.any { it.isReady(clk) } && steps.any { it.value.isReady() }) {
                workers.filter { it.isReady(clk) }.first()
                    .giveStep(clk, steps.filter { it.value.isReady() }.entries.first().value)
            }
            // calculate the lowest remaining time and advance clk by that time
            clk += workers.map { it.getRemainingTime(clk) }.filter { it != 0 }.minOrNull() ?: 0
        } while (!steps.values.all { it.isFinished() })

        return clk.toString()
    }


    private data class TimedStep(
        val requiredSteps: MutableList<TimedStep> = mutableListOf(),
        val requiredTime: Int,
        private var claimed: Boolean = false,
        private var finished: Boolean = false
    ) {

        fun isReady(): Boolean = !finished && !claimed && requiredSteps.all { it.finished }
        fun isFinished(): Boolean = finished
        fun claim() {
            claimed = true
        }

        fun finish() {
            finished = true
        }
    }

    private data class Worker(var endTime: Int = 0, private var step: TimedStep? = null) {

        fun checkWork(clk: Int) {
            if (getRemainingTime(clk) == 0 && step != null) step!!.finish()
        }

        fun isReady(clk: Int): Boolean = getRemainingTime(clk) == 0

        fun getRemainingTime(clk: Int): Int = max(0, endTime - clk)

        fun giveStep(clk: Int, step: TimedStep) {
            this.step = step
            step.claim()
            endTime = clk + step.requiredTime
        }
    }
}