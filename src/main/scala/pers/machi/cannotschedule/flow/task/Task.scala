package pers.machi.cannotschedule.flow.task

import java.util.concurrent.atomic.AtomicInteger

import pers.machi.cannotschedule.dag.{Edge, Node}

import scala.collection.immutable.HashMap


class AbstractTask(i: Int) extends Node(i) {
  def runTask(todo: () => Unit): Unit = {
    todo()
  }
}

class Task(
            override val id: Int,
            val taskName: String,
            val todo: () => Unit,
            val taskParams: Map[String, Any]
          ) extends AbstractTask(id) {
}
