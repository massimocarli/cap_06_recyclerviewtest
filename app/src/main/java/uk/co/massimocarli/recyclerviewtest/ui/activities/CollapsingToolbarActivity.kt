package uk.co.massimocarli.recyclerviewtest.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uk.co.massimocarli.recyclerviewtest.R
import uk.co.massimocarli.recyclerviewtest.model.MODEL
import uk.co.massimocarli.recyclerviewtest.model.ToDo
import uk.co.massimocarli.recyclerviewtest.ui.fragments.ToDoAdapter

class CollapsingToolbarActivity : AppCompatActivity() {

  val model: MutableList<ToDo> = mutableListOf()
  lateinit var todoAdapter: ToDoAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_collapsing)
    model.addAll(MODEL)
    todoAdapter = ToDoAdapter(model)
    val linearLayoutManager = LinearLayoutManager(this).apply {
      orientation = RecyclerView.VERTICAL
      scrollToPosition(0)
    }
    findViewById<RecyclerView>(R.id.recyclerView).apply {
      adapter = todoAdapter
      layoutManager = linearLayoutManager
    }
  }
}
