package com.stevecampos.composecleansample.data

import com.google.common.truth.Truth.assertThat
import com.stevecampos.composecleansample.BaseTest
import com.stevecampos.composecleansample.data.remote.api.ApiService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceTest : BaseTest {
    private lateinit var service: ApiService
    private lateinit var server: MockWebServer

    @Before
    override fun setup() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))//We will use MockWebServers url
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private fun enqueueMockResponse(
        fileName: String
    ) {
        javaClass.classLoader?.let {
            val inputStream = it.getResourceAsStream(fileName)
            val source = inputStream.source().buffer()
            val mockResponse = MockResponse()
            mockResponse.setBody(source.readString(Charsets.UTF_8))
            server.enqueue(mockResponse)
        }
    }

    @Test
    fun getUsers_produce_Success_10Items() {
        runBlocking {
            enqueueMockResponse("GetUsersSuccessResponse.json")

            val responseBody = service.getUsers()
            assertThat(responseBody).isNotNull()
            assertThat(responseBody.body()?.size).isEqualTo(10)
        }
    }

    @Test
    fun getPostFromFirstUser_produce_Success_10Post() {
        runBlocking {
            enqueueMockResponse("GetPostsFromFirstUserSuccessResponse.json")
            val responseBody = service.getPosts(userId = 1)
            assertThat(responseBody).isNotNull()
            assertThat(responseBody.body()?.size).isEqualTo(10)
        }
    }

    @Test
    fun getFirstPostFromFirstUser_produce_ExpectedContent() {
        runBlocking {
            enqueueMockResponse("GetPostsFromFirstUserSuccessResponse.json")
            val responseBody = service.getPosts(userId = 1)

            val firstPost = responseBody.body()!!.first()

            assertThat(firstPost.userId).isEqualTo(1)
            assertThat(firstPost.id).isEqualTo(1)
            assertThat(firstPost.title).isEqualTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit")
        }
    }


    @After
    override fun stop() {
        server.shutdown()
    }
}