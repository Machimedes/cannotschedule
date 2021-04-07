package pers.machi.cannotschedule.flowtask

import java.util.concurrent.atomic.AtomicInteger

import pers.machi.cannotschedule.dag.{Edge, Node}

import scala.collection.immutable.HashMap


class Task(id: Int, taskName: String, todo: () => Unit, taskParams: Map[String, Any]) extends Node(id)

object Task {
  def apply(id: Int, taskName: String, todo: () => Unit, taskParams: Map[String, Any]): Task = new Task(id, taskName, todo, taskParams)

  def getTaskFromJson ={

  }

  def toJson={

  }
}



class Dependency(src: Task, tgt: Task) extends Edge[Task](src,tgt)

object TaskSeqGenerator {
  val seq = new AtomicInteger()
  val n = seq.get()
  def generate = new Task(n, s"task$n", ()=>println(s"hello$n"), HashMap("1"->1,"2"->"two"))
}
