package domain.usecase

import domain.model.Todo
import domain.repository.TodoRepository

class GetTodosUseCase(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(): List<Todo> {
        return todoRepository.getAllTodos()
    }
}