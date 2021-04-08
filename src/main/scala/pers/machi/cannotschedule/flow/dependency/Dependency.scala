package pers.machi.cannotschedule.flow.dependency

import pers.machi.cannotschedule.dag.Edge
import pers.machi.cannotschedule.flow.task.Task


class Dependency(src: Task, tgt: Task) extends Edge[Task](src, tgt)

