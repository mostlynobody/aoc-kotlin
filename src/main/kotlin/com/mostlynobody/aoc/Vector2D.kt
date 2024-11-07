package com.mostlynobody.aoc

import kotlin.math.sqrt

class Vector2D(val x: Double, val y: Double) {
    fun add(other: Vector2D): Vector2D {
        return Vector2D(x + other.x, y + other.y)
    }

    fun subtract(other: Vector2D): Vector2D {
        return Vector2D(x - other.x, y - other.y)
    }

    fun multiply(other: Vector2D): Vector2D {
        return Vector2D(x * other.x, y * other.y)
    }

    fun normalize(): Vector2D {
        var length = sqrt(x * x + y * y)
        return Vector2D(x / length, y / length)
    }
}