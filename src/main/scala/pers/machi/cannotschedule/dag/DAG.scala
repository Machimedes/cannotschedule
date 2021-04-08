package pers.machi.cannotschedule.dag

import scala.collection.mutable
import org.apache.logging.log4j.LogManager
import DAG.logger
import pers.machi.cannotschedule.exceptions.DAGHasCycleException

import scala.collection.immutable.HashSet
import scala.collection.mutable.ArrayBuffer

/**
 * The DAG class:
 * Basic data structure for Scheduling Algorithm, we have extended DAG with:
 * 1 startNodes: build sub dag from start nodes
 * 2 islands: isolated nodes has not connection with others
 *
 * @param edges      set of edges, defines a dag
 * @param nodes      set of nodes, includes islands
 * @param startNodes set of start nodes, used to build sub dag
 * @tparam N
 * @tparam E
 */
class DAG[N <: Node, E <: Edge[N]](val edges: HashSet[E],
                                   var nodes: HashSet[N],
                                   val startNodes: HashSet[N]) {

  /** dag data structure */
  protected val dagTemplate = new mutable.HashMap[N, mutable.HashSet[N]]
  protected var dag = new mutable.HashMap[N, mutable.HashSet[N]]

  /** record and update each node's indegree once new edge added */
  protected val nodeIndegreeMapTemplate = new mutable.HashMap[N, Int]
  protected var nodeIndegreeMap = new mutable.HashMap[N, Int]

  /** always initialize the dag template fisrt */
  buildDAGTemplateFromEdges()

  if (startNodes.nonEmpty)
    buildDAGFromStartNodes()
  else {
    dag = dagTemplate
    nodeIndegreeMap = nodeIndegreeMapTemplate
  }

  dagTemplate.clear()
  nodeIndegreeMapTemplate.clear()

  /**
   * Is this dag has been topological Sorted, result is stored in topologicalSortingArray
   */
  protected var isSorted = false
  protected val topologicalSortingArray = new ArrayBuffer[N]
  topologicalSort()

  protected var islands: HashSet[N] = _

  if (startNodes.nonEmpty) {
    nodes = startNodes ++ nodeIndegreeMap.keySet
    islands = startNodes -- (nodeIndegreeMap.keySet)
  } else {
    islands = nodes.intersect(nodeIndegreeMap.keySet)
  }


  /**
   * Full dag built from all edges.
   */
  protected def buildDAGTemplateFromEdges() {
    edges.foreach(addEdge(_, dagTemplate, nodeIndegreeMapTemplate))
  }

  /**
   * Sub dag built from start nodes.
   */
  protected def buildDAGFromStartNodes(): Unit = {
    val queue: mutable.Queue[N] = mutable.Queue(startNodes.toSeq: _*)
    while (queue.nonEmpty) {
      val src = queue.dequeue
      val tgts = dagTemplate.getOrElse[mutable.HashSet[N]](src, mutable.HashSet.empty[N])

      tgts.foreach(tgt => {
        addEdge(Edge(src, tgt).asInstanceOf[E], dag, nodeIndegreeMap)
        queue.enqueue(tgt)
      })
    }
  }


  /**
   * Add one edge, should check internally if the added edge will form a cycle.
   * nodeIndegreeMap record and update each node's indegree once new edge added, its keySet is also used as node set.
   *
   * @param _edge
   */
  protected def addEdge(_edge: E,
                        dag: mutable.HashMap[N, mutable.HashSet[N]],
                        nodeIndegreeMap: mutable.HashMap[N, Int]): Unit = {


    if (canEdgeFormCycle(_edge)) {
      logger.error("adding this edge will form a cycle!")
      throw DAGHasCycleException(_edge.toString)
    } else {
      nodeIndegreeMap.getOrElseUpdate(_edge.src, 0)
      nodeIndegreeMap.put(_edge.tgt, nodeIndegreeMap.getOrElseUpdate(_edge.tgt, 0) + 1)


      dag.get(_edge.src) match {
        case Some(hs) => hs += _edge.tgt
        case None => dag += ((_edge.src, mutable.HashSet(_edge.tgt)))
      }
    }
  }

  /**
   * If adding an edge will from a cycle in dag,
   * the cycle must start from the target node and end with the source node.
   *
   * @param _edge
   * @return
   */
  protected def canEdgeFormCycle(_edge: Edge[N]) = {
    var result: Boolean = false
    val src = _edge.src
    val tgt = _edge.tgt

    /**
     * Check recursively
     *
     * @param _tgt
     */
    def recursionCheck(_tgt: N): Unit = {
      val nns = dagTemplate.get(_tgt)

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

  /**
   * Print information of a dag.
   */
  def printDAGInfo: Unit = {
    print("dag:               ")
    println(dag.mkString(", "))

    print("nodeIndegreeMap:   ")
    println(nodeIndegreeMap.mkString(", "))

    print("nodes:             ")
    println(nodes.mkString(", "))

    print("islands:           ")
    println(islands.mkString(", "))

    print("topologicalSorted: ")
    println(topologicalSortingArray.mkString(", "))
  }

  /**
   * After a dag is built, check if it contains a cycle.
   *
   * @return
   */
  def hasCycle = {
    if (isSorted) {
      topologicalSort
    }
    topologicalSortingArray.length != nodeIndegreeMapTemplate.size
  }

  /**
   * Topological Sort
   */
  def topologicalSort() = {
    val _nodeIndegreeMap = nodeIndegreeMap.clone()
    val zeroIndegreeNodes = mutable.Queue[N]()

    _nodeIndegreeMap.filter(_._2 == 0).keySet.foreach(zeroIndegreeNodes.enqueue)

    while (zeroIndegreeNodes.nonEmpty) {
      val current = zeroIndegreeNodes.dequeue
      topologicalSortingArray += current

      for (t <- dag.getOrElse(current, mutable.HashSet.empty[N])) {
        _nodeIndegreeMap.update(t, _nodeIndegreeMap(t) - 1)

        if (_nodeIndegreeMap(t) == 0)
          zeroIndegreeNodes.enqueue(t)
      }
    }
  }

  isSorted = true
}

object DAG {
  protected val logger = LogManager.getLogger(this.getClass)
}