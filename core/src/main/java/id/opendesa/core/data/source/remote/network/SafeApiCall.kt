package id.opendesa.core.data.source.remote.network

import com.google.android.gms.common.api.ApiException
import id.opendesa.core.data.source.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ProtocolException
import java.net.SocketTimeoutException

interface SafeApiCall {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (e: ApiException) {
                Resource.Failure(
                    isNetworkError = false,
                    errorCode = e.statusCode,
                    errorMessage = e.message.orEmpty()
                )
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        try {
                            Resource.Failure(false, throwable.code(), throwable.message.orEmpty())
                        }
                        catch (e: Exception) {
                            Resource.Failure(false, throwable.code(), null)
                        }
                    }
                    is ProtocolException -> {
                        Resource.Failure(false, -1, null)
                    }
                    is SocketTimeoutException -> {
                        Resource.Failure(
                            true,
                            408,
                            "Terdapat masalah pada internet kamu, pastikan koneksi kamu aman."
                        )
                    }
                    else -> {
                        Resource.Failure(
                            true,
                            -1,
                            "Terdapat masalah pada internet kamu, pastikan koneksi kamu aman."
                        )
                    }
                }
            }
        }
    }
}