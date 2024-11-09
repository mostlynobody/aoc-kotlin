package com.mostlynobody.aoc.y18

import com.mostlynobody.aoc.Day

class Day13(rawInput: String) : Day(rawInput) {

    override fun silver(): String {
        val cartList = mutableListOf<Cart>()
        val pathGrid: Array<Array<Path?>> = Array(rawInput.lines().size) { Array(rawInput.lines().size) { null } }
        setupGrid(pathGrid, cartList)

        while (true) {
            cartList.sort()
            for (cart in cartList) {
                cart.move()
                if (cartList.any { it != cart && it.compareTo(cart) == 0 }) return "${cart.x},${cart.y}"
                cart.direction = pathGrid[cart.y][cart.x]?.getOutgoingDirection(cart)!!
            }
        }
    }

    override fun gold(): String {
        val cartList = mutableListOf<Cart>()
        val pathGrid: Array<Array<Path?>> = Array(rawInput.lines().size) { Array(rawInput.lines().size) { null } }
        setupGrid(pathGrid, cartList)

        while (cartList.filter { !it.hasCrashed }.count() > 1) {
            for (cart in cartList.sorted()) {
                if (cart.hasCrashed) continue
                cart.move()
                cart.direction = pathGrid[cart.y][cart.x]?.getOutgoingDirection(cart)!!
                for (otherCart in cartList) {
                    if (!otherCart.hasCrashed && otherCart !== cart && otherCart.compareTo(cart) == 0) {
                        cart.hasCrashed = true
                        otherCart.hasCrashed = true
                    }
                }
            }

            cartList.removeIf { it.hasCrashed }
        }

        return "${cartList.first().x},${cartList.first().y}"
    }

    private fun setupGrid(pathGrid: Array<Array<Path?>>, cartList: MutableList<Cart>) {
        val input = rawInput.lines().map { it.toCharArray() }.toTypedArray()
        for (x in 0 until input[0].size) {
            for (y in 0 until input.size) {
                when (input[y][x]) {
                    '-', '|' -> pathGrid[y][x] = StraightPath()
                    '/' -> pathGrid[y][x] = TurnPath(TurnPathType.SLASH)
                    '\\' -> pathGrid[y][x] = TurnPath(TurnPathType.BACKSLASH)
                    '+' -> pathGrid[y][x] = IntersectionPath()
                    '^' -> {
                        pathGrid[y][x] = StraightPath()
                        cartList.add(Cart(Direction.UP, x, y))
                    }

                    'v' -> {
                        pathGrid[y][x] = StraightPath()
                        cartList.add(Cart(Direction.DOWN, x, y))
                    }

                    '<' -> {
                        pathGrid[y][x] = StraightPath()
                        cartList.add(Cart(Direction.LEFT, x, y))
                    }

                    '>' -> {
                        pathGrid[y][x] = StraightPath()
                        cartList.add(Cart(Direction.RIGHT, x, y))
                    }
                }
            }
        }
    }

    private class Cart(var direction: Direction, var x: Int, var y: Int) : Comparable<Cart> {
        var intersectionVisits = 0
        var hasCrashed = false

        fun move() {
            when (direction) {
                Direction.UP -> y--
                Direction.DOWN -> y++
                Direction.LEFT -> x--
                Direction.RIGHT -> x++
            }
        }

        override fun compareTo(other: Cart): Int {
            if (x == other.x && y == other.y) return 0
            return if (y != other.y) y - other.y
            else x - other.x
        }
    }

    private abstract class Path() {
        abstract fun getOutgoingDirection(cart: Cart): Direction
    }

    private class StraightPath() : Path() {
        override fun getOutgoingDirection(cart: Cart): Direction {
            return cart.direction
        }
    }

    private class TurnPath(val type: TurnPathType) : Path() {
        override fun getOutgoingDirection(cart: Cart): Direction {
            return when (type) {
                TurnPathType.SLASH -> {
                    when (cart.direction) {
                        Direction.UP, Direction.DOWN -> cart.direction.turnRight()
                        Direction.LEFT, Direction.RIGHT -> cart.direction.turnLeft()
                    }
                }

                TurnPathType.BACKSLASH -> {
                    when (cart.direction) {
                        Direction.UP, Direction.DOWN -> cart.direction.turnLeft()
                        Direction.LEFT, Direction.RIGHT -> cart.direction.turnRight()
                    }
                }
            }
        }
    }

    private class IntersectionPath() : Path() {
        override fun getOutgoingDirection(cart: Cart): Direction {
            return when (cart.intersectionVisits++ % 3) {
                0 -> cart.direction.turnLeft()
                1 -> cart.direction
                2 -> cart.direction.turnRight()
                else -> throw IllegalStateException("Cannot happen")
            }
        }
    }

    private enum class TurnPathType {
        SLASH, BACKSLASH
    }

    private enum class Direction {
        UP {
            override fun turnRight() = RIGHT
            override fun turnLeft() = LEFT
        },

        DOWN {
            override fun turnRight() = LEFT
            override fun turnLeft() = RIGHT
        },

        LEFT {
            override fun turnRight() = UP
            override fun turnLeft() = DOWN
        },

        RIGHT {
            override fun turnRight() = DOWN
            override fun turnLeft() = UP
        };

        abstract fun turnRight(): Direction
        abstract fun turnLeft(): Direction
    }
}