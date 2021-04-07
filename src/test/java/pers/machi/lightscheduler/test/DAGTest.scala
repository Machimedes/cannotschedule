package pers.machi.lightscheduler.test

import pers.machi.lightscheduler.dag.{DAG, Edge, Node, NodeSeqGenerator}

import scala.collection.immutable.HashSet
import scala.collection.mutable

object DAGTest {

  def main(args: Array[String]): Unit = {
    val n0 = NodeSeqGenerator.generate
    val n1 = NodeSeqGenerator.generate
    val n2 = NodeSeqGenerator.generate
    val n3 = NodeSeqGenerator.generate
    val n4 = NodeSeqGenerator.generate
    val n5 = NodeSeqGenerator.generate
    val n6 = NodeSeqGenerator.generate
    val n7 = NodeSeqGenerator.generate
    val n8 = NodeSeqGenerator.generate
    val n9 = NodeSeqGenerator.generate


    val e1 = Edge(n0, n1)
    val e2 = Edge(n1, n2)
    //val e =Edge(n2, n0)

    val e3 = Edge(n0, n3)
    val e4 = Edge(n3, n2)
    val e5 = Edge(n3, n5)
    val e6 = Edge(n5, n1)
    val e7 = Edge(n2, n4)
    val e8 = Edge(n1, n4)
    val e9 = Edge(n4, n0)

    val d = new DAG[Node, Edge[Node]]()


    /*    d.addEdge(e1)
        d.addEdge(e2)

        d.addEdge(e6)
        d.addEdge(e7)
        d.addEdge(e8)
        d.addEdge(e9)

        d.addEdge(e3)
        d.addEdge(e4)
        d.addEdge(e5)*/


    val ex1 = Edge(n0, n1)
    val ex2 = Edge(n1, n2)
    val ex3 = Edge(n2, n3)
    val ex4 = Edge(n3, n0)



    //d.buildDAGFromEdges(HashSet(e1, e2, e3, e4, e5, e6, e7, e8, e9))


    d.buildDAGFromEdges(HashSet(ex1, ex2, ex3, ex4))

    d.printDAGInfo
  }

}
