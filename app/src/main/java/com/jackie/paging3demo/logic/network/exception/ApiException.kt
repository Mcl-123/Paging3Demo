package com.jackie.paging3demo.logic.network.exception

import com.google.gson.JsonParseException
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * @User: machenglong
 * @Date: 2021/12/23
 * @Describe:
 */
class ApiException(message: String, code: Int, displayMessage: String?) : Exception(message) {

    constructor(code: Int, displayMessage: String?) : this("", code, displayMessage)

    companion object {
        /**
         * 未知错误
         */
        const val UNKNOWN = 1000

        /**
         * 解析错误
         */
        const val PARSE_ERROR = 1001

        /**
         * 网络错误
         */
        const val NETWORK_ERROR = 1002

        /**
         * 协议错误
         */
        const val HTTP_ERROR = 1003

        fun handleException(e: Throwable): ApiException {
            return when (e) {
                is JsonParseException -> ApiException(PARSE_ERROR, e.message)
                is JSONException -> ApiException(PARSE_ERROR, e.message)
                is ParseException -> ApiException(PARSE_ERROR, e.message)
                is ConnectException -> ApiException(NETWORK_ERROR, e.message)
                is UnknownHostException -> ApiException(HTTP_ERROR, e.message)
                is SocketTimeoutException -> ApiException(HTTP_ERROR, e.message)
                else -> ApiException(UNKNOWN, e.message)
            }
        }
    }
}