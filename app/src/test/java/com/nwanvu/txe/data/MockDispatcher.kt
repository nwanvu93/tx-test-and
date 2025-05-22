package com.nwanvu.txe.data

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.File

object MockDispatcher {
    /**
     * Return ok response from mock server
     */
    class RequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/users?per_page=20&since=0" -> MockResponse()
                    .setResponseCode(200)
                    .setBody(getJson("users-first-20.json"))

                "/users/nwanvu93" -> MockResponse()
                    .setResponseCode(200)
                    .setBody(getJson("user-nwanvu93.json"))

                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    /**
     * Return error response from mock server
     */
    class ErrorDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return MockResponse().setResponseCode(400)
        }
    }

    private fun getJson(path: String): String {
        val uri = this.javaClass.classLoader?.getResource(path) ?: return ""
        val file = File(uri.path)
        return String(file.readBytes())
    }
}