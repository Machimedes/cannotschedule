package pers.machi.cannotschedule.test

import pers.machi.cannotschedule.exceptions.DAGHasCycleException

object ExceptionTest {
  def main(args: Array[String]): Unit = {
    println(DAGHasCycleException("cycle"))

  }
}
