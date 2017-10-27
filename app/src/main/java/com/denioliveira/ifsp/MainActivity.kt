package com.denioliveira.ifsp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import com.denioliveira.ifsp.domain.Book
import com.denioliveira.ifsp.network.NetworkFactory
import com.denioliveira.ifsp.persistence.DatabaseHelper
import com.denioliveira.ifsp.service.BookService
import org.jetbrains.anko.button
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select
import org.jetbrains.anko.editText
import org.jetbrains.anko.toast
import org.jetbrains.anko.verticalLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    val database: DatabaseHelper
        get() = DatabaseHelper.Instance(applicationContext)

    var edtName: EditText? = null
    var edtIsbn: EditText? = null
    var edtAuthor: EditText? = null
    var edtStyle: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        // tv_hello.text = "Ola pessoal"

        verticalLayout {
            edtName = editText()
            edtIsbn = editText()
            edtAuthor = editText()
            edtStyle = editText()
            button(context.getString(R.string.btn_api_load)) {
                setOnClickListener {
                    loadBook()
                }
            }
            button(context.getString(R.string.R_btn_db_load)) {
                setOnClickListener {
                    loadFromDB()
                }
            }
        }
    }

    fun loadBook() {
        val bookService = NetworkFactory.createService(BookService::class.java)
        val call = bookService.get("59f262f42f0000392954281f")

        call.enqueue(object : Callback<Book> {
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                val book = response.body()
                edtName?.setText(book.name)
                edtIsbn?.setText("${book.isbn}")
                edtAuthor?.setText(book.author)
                edtStyle?.setText(book.style)

                database.use {
                    insert(
                            Book.TABLE_NAME,
                            Book.COLUMN_ID to 1,
                            Book.COLUMN_NAME to book.name,
                            Book.COLUMN_ISBN to book.isbn,
                            Book.COLUMN_AUTHOR to book.author,
                            Book.COLUMN_STYLE to book.style
                    )
                }
                toast("success")
            }

            override fun onFailure(call: Call<Book>, t: Throwable) {
                toast("failure")
            }
        })
    }

    fun loadFromDB() {
        val books = database.use {
            select(Book.TABLE_NAME).exec {
                parseList(classParser<Book>())
            }
        }

        val book = books.first()
        edtName?.setText(book.name)
        edtIsbn?.setText("${book.isbn}")
        edtAuthor?.setText(book.author)
        edtStyle?.setText(book.style)
    }
}
