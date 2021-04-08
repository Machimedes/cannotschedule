package pers.machi.cannotschedule.dag

class Edge[N <: Node](val src: N, val tgt: N) {
  override def toString = s"Node${src.id} -> Node${tgt.id}"
}

object Edge {
  def apply[N <: Node](src: N, tgt: N): Edge[N] = new Edge[N](src, tgt)
}
