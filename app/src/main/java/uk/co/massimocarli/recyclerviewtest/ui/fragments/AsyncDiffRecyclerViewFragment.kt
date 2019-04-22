package uk.co.massimocarli.recyclerviewtest.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uk.co.massimocarli.recyclerviewtest.R
import uk.co.massimocarli.recyclerviewtest.model.MODEL
import uk.co.massimocarli.recyclerviewtest.model.ToDo
import uk.co.massimocarli.recyclerviewtest.ui.custom.LineItemDecoration

class AsyncDiffRecyclerViewFragment : Fragment() {

  val model: MutableList<ToDo> = mutableListOf()
  lateinit var todoAdapter: ToDoListAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
    model.addAll(MODEL)
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.main_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == R.id.clean_action_id) {
      cleanModel()
      return true
    }
    return super.onOptionsItemSelected(item)
  }

  private fun cleanModel() {
    val newModel = model
      .filter { it.completed }
      .fold(mutableListOf<ToDo>()) { acc, item ->
        acc.add(item)
        acc
      }
    todoAdapter.submitList(newModel)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(
      R.layout.fragment_main,
      container,
      false
    )
    todoAdapter = ToDoListAdapter() { selectedPos, selectedToDo ->
      val newModel = model
        .fold(mutableListOf<ToDo>()) { acc, item ->
          if (item.id == selectedToDo.id) {
            item.completed = !selectedToDo.completed
          }
          acc.add(item)
          acc
        }
      todoAdapter.notifyItemChanged(selectedPos)
    }
    todoAdapter.submitList(model)
    val linearLayoutManager = LinearLayoutManager(context).apply {
      orientation = RecyclerView.VERTICAL
      scrollToPosition(0)
    }
    view.findViewById<RecyclerView>(R.id.recyclerView).apply {
      adapter = todoAdapter
      layoutManager = linearLayoutManager
      addItemDecoration(LineItemDecoration())
    }
    return view
  }
}

class ToDoItemCallback : DiffUtil.ItemCallback<ToDo>() {

  override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean =
    oldItem.id == newItem.id

  override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean =
    oldItem.name == newItem.name &&
        oldItem.dueDate == newItem.dueDate &&
        oldItem.completed == newItem.completed
}


class ToDoListAdapter(
  val listener: OnToDoSelectedListener? = null
) : ListAdapter<ToDo, ToDoSelectableViewHolder>(ToDoItemCallback()) {
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ToDoSelectableViewHolder {
    val itemLayout = LayoutInflater
      .from(parent.context)
      .inflate(
        R.layout.todo_list_item,
        parent,
        false
      )
    return ToDoSelectableViewHolder(itemLayout, listener)
  }

  override fun onBindViewHolder(
    holder: ToDoSelectableViewHolder,
    position: Int
  ) =
    holder.bind(getItem(position));
}