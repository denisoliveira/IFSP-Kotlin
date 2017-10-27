package com.denioliveira.ifsp.service

import com.denioliveira.ifsp.domain.Book
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BookService {

    @GET("{code}")
    fun get(@Path("code") code: String): Call<Book>
}