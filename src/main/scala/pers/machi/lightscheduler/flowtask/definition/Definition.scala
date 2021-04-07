package pers.machi.lightscheduler.flowtask.definition

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import pers.machi.lightscheduler.dag.Node

import scala.collection.immutable.{HashMap, HashSet}

case class Definition(id: Int)

case class FlowDefinition(id: Int, flowName: String, taskIdSet: HashSet[String], dependencySet: HashMap[String, String], taskParams: Map[String, Any])

case class TaskDefinition(id: Int, taskName: String, shellCommand: String, taskParams: Map[String, Any])
