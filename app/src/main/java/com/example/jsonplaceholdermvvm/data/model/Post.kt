package com.example.jsonplaceholdermvvm.data.model

// Maps directly to one JSON object returned by https://jsonplaceholder.typicode.com/posts
// e.g. { "userId": 1, "id": 1, "title": "...", "body": "..." }
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)
