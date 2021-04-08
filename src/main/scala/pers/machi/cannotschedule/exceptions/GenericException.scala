package pers.machi.cannotschedule.exceptions


/**
 * GenericException, all custom exception must extends this trait.
 * code: a number assigned
 * notation: quick describe of usage of this exception
 * message: detail of the runtime condition when exception thrown
 */
sealed trait GenericException {
  self: Throwable =>
  val message: String

  val code: Int
  val notation: String
}

case class DAGHasCycleException(message: String) extends Exception(message) with GenericException {
  override val code: Int = 1
  override val notation: String =
    """
      |when this exception is thrown, a cycle has been detected in the DAG.
      |please check the message and it may include information of the edge cause this issue.
      |""".stripMargin
}
