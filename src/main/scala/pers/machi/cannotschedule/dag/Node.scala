package pers.machi.cannotschedule.dag

class Node(val id: Int) {
  override def toString = s"Node$id"
}

object Node {
  def apply(id: Int): Node = new Node(id)
}
