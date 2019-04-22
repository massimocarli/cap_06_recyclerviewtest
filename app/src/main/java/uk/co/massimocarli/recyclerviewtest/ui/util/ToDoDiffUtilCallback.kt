package uk.co.massimocarli.recyclerviewtest.ui.util

import androidx.recyclerview.widget.DiffUtil
import uk.co.massimocarli.recyclerviewtest.model.ToDo

class ToDoDiffUtilCallback(
  val oldToDos: List<ToDo>,
  val newToDos: List<ToDo>
) : DiffUtil.Callback() {

  override fun getOldListSize(): Int = oldToDos.size

  override fun getNewListSize(): Int = newToDos.size

  override fun areItemsTheSame(
    oldPos: Int,
    newPos: Int
  ): Boolean = oldToDos[oldPos].id == newToDos[newPos].id

  override fun areContentsTheSame(
    oldPos: Int,
    newPos: Int
  ): Boolean = oldToDos[oldPos].name == newToDos[newPos].name &&
      oldToDos[oldPos].dueDate == newToDos[newPos].dueDate &&
      oldToDos[oldPos].completed == newToDos[newPos].completed

}