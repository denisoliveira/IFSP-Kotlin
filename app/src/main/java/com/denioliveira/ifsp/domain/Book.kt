package com.denioliveira.ifsp.domain

import com.google.gson.annotations.SerializedName

class Book(cid: Int?, cname: String?, cisbn: Int?, cauthor: String?, cstyle: String?) {

    var id: Int? = cid

    @SerializedName("name")
    var name: String? = cname

    @SerializedName("isbn")
    var isbn: Int? = cisbn

    @SerializedName("author")
    var author: String? = cauthor

    @SerializedName("style")
    var style: String? = cstyle

    companion object {
        var TABLE_NAME = "books"
        val COLUMN_ID = "id"
        val COLUMN_NAME = "name"
        val COLUMN_ISBN = "isbn"
        val COLUMN_AUTHOR = "author"
        val COLUMN_STYLE = "style"
    }
}