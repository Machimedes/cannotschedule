package pers.machi.lightscheduler.exceptions

sealed trait GenericException {
  self: Throwable =>
  val code :Int
  val message: String
  val desc:String
}

case class  DAGHasCycleException(message:String) extends Exception(message) with GenericException {
  override val code: Int = 1
  override val desc: String =
    """
      |when this exception is thrown, a cycle has been detected in the DAG.
      |please check the message and it may include information of the edge cause this issue.
      |""".stripMargin
}
