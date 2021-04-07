package pers.machi.cannotschedule.dag

import java.util.concurrent.atomic.AtomicBoolean

import scala.collection.mutable
import org.apache.logging.log4j.LogManager
import DAG.logger
import pers.machi.cannotschedule.exceptions.DAGHasCycleException

import scala.collection.mutable.ArrayBuffer


class DAG[N <: Node, E <: Edge[N]] {

  private val nodeIndegreeMap = new mutable.HashMap[N, Int]
  private val edges = new mutable.HashSet[E]
  private val dag = new mutable.HashMap[N, mutable.HashSet[N]]
  private val isSorted = new AtomicBoolean()
  private val topologicalSorted = new ArrayBuffer[N]

  def addEdge(_edge: E): Unit = {
    if (edges.contains(_edge)) {
      logger.warn("duplicated edge added!")
    }

    if (canConnectionFormCycle(_edge)) {
      logger.error("adding this edge will form a cycle!")
      throw DAGHasCycleException(_edge.toString)
    } else {
      nodeIndegreeMap.getOrElseUpdate(_edge.src, 0)
      nodeIndegreeMap.put(_edge.tgt, nodeIndegreeMap.getOrElseUpdate(_edge.tgt, 0) + 1)

      edges += _edge

      dag.get(_edge.src) match {
        case Some(hs) => hs += _edge.tgt
        case None => dag += ((_edge.src, mutable.HashSet(_edge.tgt)))
      }
    }
  }

  private def canConnectionFormCycle(_edge: Edge[N]) = {
    var result: Boolean = false
    val src = _edge.src
    val tgt = _edge.tgt

    def recursionCheck(_tgt: N): Unit = {
      val nns = dag.get(_tgt)

      nns match {
        case Some(hs) => {
          if (hs.contains(src)) {
            result = true
          } else {
            hs.foreach {
              e => if (!result) recursionCheck(e)
            }
          }
        }
        case None => ()
      }
    }

    recursionCheck(tgt)
    result
  }

  def buildDAGFromEdges(_edges: Set[E]) {
    for (_edge <- _edges) {
      addEdge(_edge)
    }
    topologicalSort
  }

  def printDAGInfo: Unit = {
    println("dag:")
    println(dag.mkString("\n"))
    println("nodeIndegreeMap:")
    println(nodeIndegreeMap.mkString(", "))
    println("topologicalSorted:")
    println(topologicalSorted.mkString(", "))
  }

  def hasCycle = {
    if (!isSorted.get()) {
      topologicalSort
    }

    topologicalSorted.length != nodeIndegreeMap.size
  }

  def topologicalSort = {
    val _nodeIndegreeMap = nodeIndegreeMap.clone()
    val zeroIndegreeNodes = mutable.Queue[N]()

    _nodeIndegreeMap.filter(_._2 == 0).keySet.foreach(zeroIndegreeNodes.enqueue(_))

    while (zeroIndegreeNodes.nonEmpty) {
      val current = zeroIndegreeNodes.dequeue
      topologicalSorted += current

      for (t <- dag.getOrElse(current, mutable.HashSet.empty[N])) {
        _nodeIndegreeMap.update(t, _nodeIndegreeMap(t) - 1)

        if (_nodeIndegreeMap(t) == 0)
          zeroIndegreeNodes.enqueue(t)
      }
    }
  }
  isSorted.set(true)
}

object DAG {
  private val logger = LogManager.getLogger(this.getClass)
}