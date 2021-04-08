package pers.machi.cannotschedule.flow

import java.util.concurrent.locks.ReentrantLock

import pers.machi.cannotschedule.dag.DAG
import pers.machi.cannotschedule.flow.dependency.Dependency
import pers.machi.cannotschedule.flow.task.Task
import pers.machi.cannotschedule.flow.task.AbstractTask

import scala.collection.immutable.HashSet
import scala.collection.mutable

/**
 * DAG is static, it defines the dependencies for nodes.
 * Flow wraps in DAG and provide dynamic properties like submit task, feedback to completed task, etc.
 * Flow is also a Task, as it can start, and finish.
 *
 *
 *
 */
class Flow() {


}
