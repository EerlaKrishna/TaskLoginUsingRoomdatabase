package com.example.userloginapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userloginapp.databinding.ActivityMainBinding
import com.example.userloginapp.databinding.DialogEditTaskBinding
import com.example.userloginapp.task.Task
import com.example.userloginapp.task.TaskAdapter
import com.example.userloginapp.task.TaskRepository
import com.example.userloginapp.task.TaskViewModel
import com.example.userloginapp.task.TaskViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(TaskRepository(UserDatabase.getDatabase(this).taskDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = TaskAdapter(
            onEdit = { task -> showEditDialog(task) },
            onDelete = { task -> taskViewModel.delete(task) }
        )
        binding.recyclerView.adapter = adapter

        taskViewModel.allTasks.observe(this) { tasks ->
            tasks?.let { adapter.submitList(it) }
        }

        binding.btnAddTask.setOnClickListener {
            val title = binding.etTaskTitle.text.toString()
            val description = binding.etTaskDescription.text.toString()
            val task = Task(title = title, description = description)
            taskViewModel.insert(task)
            binding.etTaskTitle.text.clear()
            binding.etTaskDescription.text.clear()
        }
    }

    private fun showEditDialog(task: Task) {
        val dialogBinding = DialogEditTaskBinding.inflate(layoutInflater)
        dialogBinding.etTaskTitle.setText(task.title)
        dialogBinding.etTaskDescription.setText(task.description)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Edit Task")
            .setView(dialogBinding.root)
            .setPositiveButton("Update") { _, _ ->
                val updatedTitle = dialogBinding.etTaskTitle.text.toString()
                val updatedDescription = dialogBinding.etTaskDescription.text.toString()
                val updatedTask = task.copy(title = updatedTitle, description = updatedDescription)
                taskViewModel.update(updatedTask)
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }
}
