package domain.usecase

import domain.Result
import domain.TodoError
import domain.repository.TodoRepository

class RemoveTodoUseCase(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(id: Long): Result<Unit, TodoError> {
        return todoRepository.removeTodo(id)
    }
}