import data.FileTodoDataSource
import data.InMemoryTodoDataSource
import data.LocalTodoRepository
import domain.TodoError
import domain.map
import domain.onError
import domain.onSuccess
import domain.repository.TodoRepository
import domain.usecase.AddTodoUseCase
import domain.usecase.CompleteTodoUseCase
import domain.usecase.GetTodosUseCase
import domain.usecase.RemoveTodoUseCase
import java.io.File

fun main() {
    val inMemoryDataSource = InMemoryTodoDataSource()
    val fileDataSource = FileTodoDataSource(File("src/main/resources/todo.json"))
    val todoRepository: TodoRepository = LocalTodoRepository(fileDataSource)

    val addTodoUseCase = AddTodoUseCase(todoRepository)
    val getTodosUseCase = GetTodosUseCase(todoRepository)
    val removeTodoUseCase = RemoveTodoUseCase(todoRepository)
    val completeTodoUseCase = CompleteTodoUseCase(todoRepository)

    while (true) {
        println("1. Add Todo")
        println("2. Remove Todo")
        println("3. Complete Todo")
        println("4. List Todos")
        println("5. Exit")
        when (readlnOrNull()?.toIntOrNull()) {
            1 -> {
                println("Enter description:")
                val description = readlnOrNull().orEmpty()
                val newTodo = addTodoUseCase(description)
                println("Added: $newTodo")
            }

            2 -> {
                println("Enter ID of todo to remove:")
                val id = readlnOrNull()?.toLongOrNull()
                id?.run {
                    removeTodoUseCase(id).map {

                    }.onSuccess {
                        println("removed todo with ID: $id")
                    }.onError {
                        when (it) {
                            TodoError.NON_EXISTENT_ID -> println("Invalid ID")
                        }
                    }
                }
            }


            3 -> {
                println("Enter ID of todo to complete:")
                val id = readlnOrNull()?.toLongOrNull()
                if (id != null) {
                    completeTodoUseCase(id)
                    println("Completed todo with ID: $id")
                } else {
                    println("Invalid ID")
                }
            }

            4 -> {
                val todos = getTodosUseCase()
                todos.ifEmpty { println("No saved todos") }
                todos.forEach {
                    println(
                        """
                        ID: ${it.id}
                        Text: ${it.description}
                        IsCompleted: ${it.isCompleted}
                        ==============================
                    """.trimIndent()
                    )
                }
            }

            5 -> return

            else -> println("Invalid option")
        }
    }
}