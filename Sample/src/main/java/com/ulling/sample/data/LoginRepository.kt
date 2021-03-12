package com.ulling.sample.data

import com.ulling.lib.core.utils.QcLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

sealed class ResultLogIn<out R> {
    data class Success<out T>(val data: T?) : ResultLogIn<T>()
    data class Error(val exception: Exception) : ResultLogIn<Nothing>()
}

class LoginRepository(private val responseParser: LoginResponseParser) {

    private val loginUrl = "https://example.com/login"

    // Function that makes the network request, blocking the current thread
    fun makeLoginRequest(
            jsonBody: String
    ): ResultLogIn<LoginResponse> {
        QcLog.e("makeLoginRequest  ==== ")
        val url = URL(loginUrl)
        (url.openConnection() as? HttpURLConnection)?.run {
            requestMethod = "POST"
            setRequestProperty("Content-Type", "application/json; utf-8")
            setRequestProperty("Accept", "application/json")
            doOutput = true
            outputStream.write(jsonBody.toByteArray())
//            return ResultLogIn.Success(responseParser.parse(inputStream))
            return ResultLogIn.Success(null)
        }
        return ResultLogIn.Error(Exception("Cannot open HttpURLConnection"))
    }

    /**
     * withContext(Dispatchers.IO)는 코루틴 실행을 I/O 스레드로 이동하여
     * 호출 함수를 기본 안전 함수로 만들고 필요에 따라 UI를 업데이트하도록 설정합니다.
     *
     * suspend - 코루틴 내에서 함수가 호출되도록 강제하는 Kotlin의 방법
     *suspend 함수는 코루틴에서 실행
     *
     *
     * withContext(Dispatchers.IO) 안에서 I/O 작업용으로 예약된 스레드
     *
     */
    suspend fun makeLoginRequestC(
            jsonBody: String
    ): ResultLogIn<LoginResponse> {
        QcLog.e("makeLoginRequestC  ==== ")

        // Move the execution of the coroutine to the I/O dispatcher
        return withContext(Dispatchers.IO) {
            QcLog.e("makeLoginRequestC Dispatchers.IO  ==== ")
            // Blocking network request code
            val url = URL(loginUrl)
            (url.openConnection() as? HttpURLConnection)?.run {
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json; utf-8")
                setRequestProperty("Accept", "application/json")
                doOutput = true
                outputStream.write(jsonBody.toByteArray())
//                ResultLogIn.Success(responseParser.parse(inputStream))
                ResultLogIn.Success(null)
            }
            ResultLogIn.Error(Exception("Cannot open HttpURLConnection"))
        }
    }
}