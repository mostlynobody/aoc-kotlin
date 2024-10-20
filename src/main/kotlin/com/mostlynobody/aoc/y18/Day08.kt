package com.mostlynobody.aoc.y18

import com.mostlynobody.aoc.Day

class Day08(rawInput: String) : Day(rawInput) {

    override fun silver(): String {
        return Node.createTree(rawInput.split(" ").map { it.toInt() }).getMetadataSum().toString()
    }

    override fun gold(): String {
        return Node.createTree(rawInput.split(" ").map { it.toInt() }).getValue().toString()
    }

    private data class Node(
        val children: MutableList<Node> = mutableListOf(), val metadata: MutableList<Int> = mutableListOf()
    ) {
        fun getMetadataSum(): Int = metadata.sum() + children.map { it.getMetadataSum() }.sum()

        fun getValue(): Int {
            if (children.isEmpty()) return metadata.sum()
            return metadata.filter { it <= children.size }.map { children[it - 1].getValue() }.sum()
        }

        companion object Factory {
            fun createTree(input: List<Int>): Node {
                return createTreeRecursive(input.iterator())
            }

            private fun createTreeRecursive(iterator: Iterator<Int>): Node {
                val childNodeCount = iterator.next()
                val metadataCount = iterator.next()
                val node = Node()

                repeat(childNodeCount) { node.children.add(createTreeRecursive(iterator)) }
                repeat(metadataCount) { node.metadata.add(iterator.next()) }
                return node
            }
        }
    }
}

