package com.cn.danceland.myapplication.bean

data class MyTraining(
        val code: Int,
        val `data`: DataL,
        val errorMsg: String,
        val success: Boolean
)

data class DataL(
        val content: List<Content>,
        val last: Boolean,
        val number: Int,
        val numberOfElements: Int,
        val size: Int,
        val totalElements: Int,
        val totalPages: Int
)

data class Content(
        val content: String,
        val delete_remark: String,
        val id: Int,
        val img_url: String,
        val title: String
)