package domain.usecase

import domain.model.Todo
import domain.repository.TodoRepository

class AddTodoUseCase(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(description: String): Todo {
        val todo = Todo(description = description)
        return todoRepository.addTodo(todo)
    }
}