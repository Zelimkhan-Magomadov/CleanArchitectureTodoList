package data

import domain.Result
import domain.model.Todo
import domain.TodoError

class InMemoryTodoDataSource : TodoDataSource {
    private val todos = mutableListOf<Todo>()
    private var nextId = 1L

    override fun addTodo(todoItem: Todo): Todo {
        val newItem = todoItem.copy(id = nextId++)
        todos.add(newItem)
        return newItem
    }

    override fun getAllTodos(): List<Todo> {
        return todos.toList()
    }

    override fun completeTodo(id: Long) {
        val index = todos.indexOfFirst { it.id == id }
        if (index != -1) {
            val item = todos[index]
            todos[index] = item.copy(isCompleted = true)
        }
    }

    override fun removeTodo(id: Long): Result<Unit, TodoError> {
        val isRemoved = todos.removeIf { it.id == id }
        return if (isRemoved) {
            Result.Success(Unit)
        } else {
            Result.Error(TodoError.NON_EXISTENT_ID)
        }
    }
}