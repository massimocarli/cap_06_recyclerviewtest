package uk.co.massimocarli.recyclerviewtest.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uk.co.massimocarli.recyclerviewtest.R
import uk.co.massimocarli.recyclerviewtest.model.MODEL
import uk.co.massimocarli.recyclerviewtest.model.ToDo

class SimpleRecyclerViewFragment : Fragment() {

  val model: MutableList<ToDo> = mutableListOf()
  lateinit var todoAdapter: ToDoAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    model.addAll(MODEL)
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
    todoAdapter = ToDoAdapter(model)
    val linearLayoutManager = LinearLayoutManager(context).apply {
      orientation = RecyclerView.VERTICAL
      scrollToPosition(0)
    }
    view.findViewById<RecyclerView>(R.id.recyclerView).apply {
      adapter = todoAdapter
      layoutManager = linearLayoutManager
    }
    return view
  }
}

/**
 * This is the ViewHolder implementation
 */
class ToDoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

  val taskDoneImage: ImageView
  val todoName: TextView
  val todoDescription: TextView

  init {
    taskDoneImage = view.findViewById(R.id.taskDoneImage)
    todoName = view.findViewById(R.id.todoName)
    todoDescription = view.findViewById(R.id.todoDescription)
  }

  fun bind(item: ToDo) {
    todoName.text = item.name
    todoDescription.text = item.description
    taskDoneImage.setImageResource(
      if (item.completed) R.drawable.ic_check_black_24dp
      else R.drawable.ic_crop_square_black_24dp
    )
  }
}


/**
 * This is the Adapter
 */
open class ToDoAdapter(
  val model: List<ToDo>
) : RecyclerView.Adapter<ToDoViewHolder>() {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ToDoViewHolder {
    val itemLayout = LayoutInflater
      .from(parent.context)
      .inflate(
        R.layout.todo_list_item,
        parent,
        false
      )
    return ToDoViewHolder(itemLayout)
  }

  override fun getItemCount(): Int =
    model.size

  override fun onBindViewHolder(
    holder: ToDoViewHolder,
    position: Int
  ) = holder.bind(model[position])
}
