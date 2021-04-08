package pers.machi.cannotschedule.flow.definition

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import pers.machi.cannotschedule.dag.Node

import scala.collection.immutable.{HashMap, HashSet}

class Definition(id: Int)

case class FlowDefinition(id: Int,
                          flowName: String,
                          taskIdSet: HashSet[String],
                          dependencySet: HashMap[String, String],
                          taskParams: Map[String, Any]) extends Definition(id)

case class TaskDefinition(id: Int,
                          taskName: String,
                          todoString: String,
                          taskParams: Map[String, Any]) extends Definition(id)
