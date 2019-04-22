package uk.co.massimocarli.recyclerviewtest.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uk.co.massimocarli.recyclerviewtest.R
import uk.co.massimocarli.recyclerviewtest.model.MODEL
import uk.co.massimocarli.recyclerviewtest.model.ToDo
import uk.co.massimocarli.recyclerviewtest.ui.custom.LineItemDecoration
import uk.co.massimocarli.recyclerviewtest.ui.util.ToDoDiffUtilCallback

class DiffRecyclerViewFragment : Fragment() {

  val model: MutableList<ToDo> = mutableListOf()
  lateinit var todoAdapter: ToDoSelectableAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    model.addAll(MODEL)
    setHasOptionsMenu(true)
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
    val diffResult = DiffUtil
      .calculateDiff(ToDoDiffUtilCallback(model, newModel))
    swapModel(model, newModel)
    diffResult.dispatchUpdatesTo(todoAdapter);
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
    todoAdapter = ToDoSelectableAdapter(model) { selectedPos, selectedToDo ->
      val newModel = model
        .fold(mutableListOf<ToDo>()) { acc, item ->
          if (item.id == selectedToDo.id) {
            item.completed = !selectedToDo.completed
          }
          acc.add(item)
          acc
        }
      swapModel(model, newModel)
      todoAdapter.notifyItemChanged(selectedPos)
    }
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

  private fun swapModel(oldModel: MutableList<ToDo>, newModel: List<ToDo>) {
    oldModel.clear()
    oldModel.addAll(newModel)
  }
}

typealias OnToDoSelectedListener = (Int, ToDo) -> Unit

/**
 * This is the Adapter
 */
open class ToDoSelectableAdapter(
  val model: List<ToDo>,
  val listener: OnToDoSelectedListener? = null
) : RecyclerView.Adapter<ToDoSelectableViewHolder>() {

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

  override fun getItemCount(): Int =
    model.size

  override fun onBindViewHolder(
    holder: ToDoSelectableViewHolder,
    position: Int
  ) = holder.bind(model[position])
}

class ToDoSelectableViewHolder(
  view: View,
  val listener: OnToDoSelectedListener? = null
) : RecyclerView.ViewHolder(view) {

  private val taskDoneImage: ImageView
  private val todoName: TextView
  private val todoDescription: TextView
  private lateinit var currentItem: ToDo

  init {
    taskDoneImage = view.findViewById(R.id.taskDoneImage)
    todoName = view.findViewById(R.id.todoName)
    todoDescription = view.findViewById(R.id.todoDescription)
    taskDoneImage.setOnClickListener {
      if (listener != null) {
        listener.invoke(adapterPosition, currentItem)
      }
    }
  }

  fun bind(item: ToDo) {
    currentItem = item
    todoName.text = item.name
    todoDescription.text = item.description
    taskDoneImage.setImageResource(
      if (!item.completed) R.drawable.ic_check_black_24dp
      else R.drawable.ic_crop_square_black_24dp
    )
  }
}