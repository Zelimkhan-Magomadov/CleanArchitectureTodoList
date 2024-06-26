package data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import domain.Result
import domain.model.Todo
import domain.TodoError
import java.io.File

class FileTodoDataSource(
    private val file: File
) : TodoDataSource {
    private val gson = Gson()
    private val type = object : TypeToken<List<Todo>>() {}.type

    init {
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }

        if (!file.exists()) {
            file.createNewFile()
            file.writeText(gson.toJson(emptyList<Todo>()))
        }
    }

    private fun readFromFile(): MutableList<Todo> {
        val content = file.readText().ifEmpty { return mutableListOf() }
        return gson.fromJson(content, type)
    }

    private fun writeToFile(todo: List<Todo>) {
        file.writeText(gson.toJson(todo))
    }

    override fun addTodo(todo: Todo): Todo {
        val todos = readFromFile().toMutableList()
        val newTodo = todo.copy(id = (todos.maxOfOrNull { it.id } ?: 0) + 1)
        todos.add(newTodo)
        writeToFile(todos)
        return newTodo
    }

    override fun getAllTodos(): List<Todo> {
        return readFromFile()
    }

    override fun getTodoById(id: Long): Result<Todo, TodoError> {
        val todos = readFromFile()
        val todoIndex = todos.indexOfFirst { it.id == id }
        return if (todoIndex == -1) {
            Result.Error(TodoError.NON_EXISTENT_ID)
        } else {
            Result.Success(todos[todoIndex])
        }
    }

    override fun completeTodo(id: Long) {
        val todo = readFromFile().toMutableList()
        val index = todo.indexOfFirst { it.id == id }
        if (index != -1) {
            val item = todo[index]
            todo[index] = item.copy(isCompleted = true)
            writeToFile(todo)
        }
    }

    override fun removeTodo(id: Long): Result<Todo, TodoError> {
        val todos = readFromFile().toMutableList()
        val todoToRemove = todos.find { it.id == id }
        val isTodoRemoved = todos.remove(todoToRemove)
        return if (isTodoRemoved) {
            writeToFile(todos)
            Result.Success(todoToRemove!!)
        } else {
            Result.Error(TodoError.NON_EXISTENT_ID)
        }
    }
}