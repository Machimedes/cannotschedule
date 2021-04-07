package pers.machi.lightscheduler.dag

import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger

class Node(val id: Int) {
  override def toString = s"Node$id"
}

object NodeSeqGenerator {
  val seq = new AtomicInteger()

  def generate = new Node(seq.getAndIncrement())
}

class Edge[N <: Node](val src: N, val tgt: N) {
  override def toString = s"Node${src.id} -> Node${tgt.id}"
}

object Edge {
  def apply[N <: Node](src: N, tgt: N): Edge[N] = new Edge(src, tgt)
}
