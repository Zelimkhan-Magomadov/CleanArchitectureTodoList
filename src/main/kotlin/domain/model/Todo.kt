package domain.model

data class Todo(
    val id: Long = 0,
    val description: String = "",
    val isCompleted: Boolean = false
)