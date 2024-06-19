package domain.usecase

import domain.repository.TodoRepository

class CompleteTodoUseCase(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(id: Long) {
        return todoRepository.completeTodo(id)
    }
}