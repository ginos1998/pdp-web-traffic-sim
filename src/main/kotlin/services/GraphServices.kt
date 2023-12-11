package services

import models.dtos.Graph
import models.Router

class GraphServices {
    fun updateEdges(graph: Graph, routers: MutableList<Router>){
        val newEdges: MutableList<MutableList<Int>> = mutableListOf()

        routers.forEach { router ->
            val edge: MutableList<Int> = mutableListOf()
            edge.add(router.getRouterId())
            router.getOutputBuffer().keys.forEach { neighbour ->
                edge.add(neighbour)
                router.getOutputBuffer()[neighbour]?.let { edge.add(it.size) }
                newEdges.add(edge)
            }
        }
        graph.setEdges(newEdges)
    }
}