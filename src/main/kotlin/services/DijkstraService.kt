package services

import models.dtos.WebSimulator

class DijkstraService {

    // Dado un grafo definido por una lista de vertices y una lista de aristas, y un vertice inicial,
    // devuelve el camino mas corto desde el vertice inicial a todos los demas vertices del grafo.
    fun nextVertexInShortestPath(startVertex: Int, endVertex: Int): Int? {
        val graph = WebSimulator.getInstance().getGraph()
        val vertexes = graph.getVertexes()
        val edges = graph.getEdges()

        val distance = mutableMapOf<Int, Int>()
        val visited = mutableSetOf<Int>()

        initDistances(distance, vertexes)
        distance[startVertex] = 0

        while (visited.size < vertexes.size) {
            // Encontrar el vértice con la distancia mínima no visitado
            val currentVertex = distance.filter { !visited.contains(it.key) }.minByOrNull { it.value }?.key
                ?: return null

            visited.add(currentVertex)

            // Actualizar las distancias a los vecinos
            for (edge in edges.filter { it[0] == currentVertex }) {
                val neighbor = edge[1]
                val weight = edge[2]
                val newDistance = distance[currentVertex]?.plus(weight)

                if (newDistance != null && newDistance < distance[neighbor]!!) {
                    distance[neighbor] = newDistance

                }
            }
        }

        if (isNeighbourVertex(startVertex, endVertex, edges)) {
            return endVertex
        }

        // Devolver el siguiente vértice en el camino más corto desde el inicio hasta el destino
        while (endVertex != startVertex) {
            for (edge in edges.filter { it[1] == endVertex }) {
                val neighbor = edge[0]
                val weight = edge[2]
                val newPathDistance = distance[endVertex]?.minus(weight)

                if (newPathDistance == distance[neighbor]) {
                    return neighbor
                }
            }
        }

        return null
    }

    private fun initDistances(distance: MutableMap<Int, Int>, vertexes: List<Int>) {
        for (vertex in vertexes) {
            distance[vertex] = Int.MAX_VALUE
        }
    }

    private fun isNeighbourVertex(startVertex: Int, endVertex: Int, edges: List<List<Int>>): Boolean {
        return edges.any { it[0] == startVertex && it[1] == endVertex }
    }


}