package ru.netology

data class Message(
    val id: Int,
    val ownerId: Int,
    val text: String,
    val isRead: Boolean = false
)