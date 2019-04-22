package uk.co.massimocarli.recyclerviewtest.ui.activities

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
import uk.co.massimocarli.recyclerviewtest.R
import uk.co.massimocarli.recyclerviewtest.model.MODEL
import uk.co.massimocarli.recyclerviewtest.model.ToDo
import uk.co.massimocarli.recyclerviewtest.ui.fragments.ToDoAdapter

class ExpandingToolbarActivity : AppCompatActivity() {

  val model: MutableList<ToDo> = mutableListOf()
  lateinit var todoAdapter: ToDoAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_expanding)
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
    findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbar).apply {
      title = getString(R.string.app_name)
      setBackgroundColor(Color.BLUE)
      setCollapsedTitleTextColor(Color.WHITE)
      setExpandedTitleColor(Color.RED)
    }
  }
}
