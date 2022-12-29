package id.opendesa.core.data.source

sealed class Resource<out T> {
    data class Success<T>(val value:T): Resource<T>()
    data class Cached<T>(val value: T): Resource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorMessage: String?
    ): Resource<Nothing>()

    object Loading: Resource<Nothing>()
    object Empty: Resource<Nothing>()
}