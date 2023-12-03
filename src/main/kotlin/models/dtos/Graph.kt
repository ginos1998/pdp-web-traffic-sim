package models.dtos

class Graph (
    private var vertexes: MutableList<Int> = mutableListOf(),
    private var edges: MutableList<MutableList<Int>> = mutableListOf()
) {
    constructor():
            this(mutableListOf(), mutableListOf())

    fun getVertexes(): MutableList<Int> {
        return vertexes
    }

    fun setVertexes(vertexes: MutableList<Int>) {
        this.vertexes = vertexes
    }

    fun getEdges(): MutableList<MutableList<Int>> {
        return edges
    }

    fun setEdges(edges: MutableList<MutableList<Int>>) {
        this.edges = edges
    }

}