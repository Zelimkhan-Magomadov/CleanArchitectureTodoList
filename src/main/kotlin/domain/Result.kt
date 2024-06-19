package domain

typealias RootError = Error

sealed interface Result<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : Result<D, E>
    data class Error<out D, out E : RootError>(val error: E) : Result<D, E>
}

inline fun <D, E : RootError> Result<D, E>.onSuccess(action: (D) -> Unit): Result<D, E> {
    if (this is Result.Success) {
        action(this.data)
    }
    return this
}

inline fun <D, E : RootError> Result<D, E>.onError(action: (E) -> Unit): Result<D, E> {
    if (this is Result.Error) {
        action(this.error)
    }
    return this
}