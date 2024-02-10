package com.example.vaidya.datamodel

data class DataModel(
    val count: Boolean,
    val next: String,
    val previous: Any,
    val results: List<Result>
)