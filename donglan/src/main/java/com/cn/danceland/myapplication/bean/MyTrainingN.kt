package com.cn.danceland.myapplication.bean

data class MyTrainingN(
    val code: Int,
    val `data`: DataA,
    val errorMsg: String,
    val success: Boolean
)

data class DataA(
    val content: List<ContentA>,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
)

data class ContentA(
    val content: String,
    val description: String,
    val id: Int,
    val operator_id: Int,
    val pic_path: String,
    val pic_url: String,
    val release_date: String,
    val share_count: Int,
    val status: String,
    val title: String,
    val train_type_id: Int,
    val train_type_name: String,
    val type: String,
    val url: String,
    val watch_count: Int
)