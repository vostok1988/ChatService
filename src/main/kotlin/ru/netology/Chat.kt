package ru.netology

data class Chat(
    val id: Int,
    val senderId: Int,
    val recipientId: Int,
    val messages: MutableList<Message> = mutableListOf<Message>()
) {
    private var countMessage: Int = 0
    val users = listOf<Int>(senderId, recipientId)
}