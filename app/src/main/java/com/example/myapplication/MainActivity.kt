package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
//import androidx.constraintlayout.solver.state.State
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

class MainActivity : AppCompatActivity() {

  //  lateinit var state: androidx.constraintlayout.solver.state.State
    val STATE_KEY = "STATE_KEY"
    private val list = mutableListOf<Todo>()
    private lateinit var adapter: RecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dbHelper = DBHelper(this)
        val editText = findViewById<EditText>(R.id.editTextTextMultiLine2)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // заполнить list из базы
        val allData = dbHelper.getAll()
        list.addAll(allData)

        adapter = RecyclerAdapter(list) {
            // адаптеру передали обработчик удаления элемента
            // TODO: удалить элемент из базы по ID
            val id = list[it].id
            dbHelper.remove(id)

            list.removeAt(it)
            adapter.notifyItemRemoved(it)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


        val buttonAdd = findViewById<Button>(R.id.button)
        buttonAdd.setOnClickListener {
            val title = editText.text.toString()
            val id = dbHelper.addTodo(title)
            list.add(Todo(id, title))
            adapter.notifyItemInserted(list.lastIndex)

        }

    }
}


