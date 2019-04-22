package uk.co.massimocarli.recyclerviewtest.model

import java.util.*

/**
 * ToDo Entity
 */
data class ToDo(
  val id: Int,
  val name: String,
  val description: String?,
  val dueDate: Date,
  var completed: Boolean = false
)

val MODEL = Array<ToDo>(100) {
  ToDo(it, "Task #$it", "This is the task #$it", Date(), it % 2 == 0)
}
