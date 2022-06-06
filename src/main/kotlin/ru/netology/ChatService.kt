package ru.netology

object ChatService {
    private var chatsIds = 0
    private var messagesIds = 0
    private val chats = mutableListOf<Chat>()

    fun createChat(senderId: Int, recipientId: Int, textMessage: String): Int {
        messagesIds++
        val message = Message(messagesIds, senderId, textMessage)
        val chat: Chat = chats.firstOrNull { chat ->
            chat.users.containsAll(listOf(senderId, recipientId))
        }
            ?.let { chat ->
                chat.messages += message
                return@let chat
            }
            ?: installNextChatId(Chat(-1, senderId, recipientId, messages = mutableListOf(message)))

        chats.removeIf { chat.id == it.id }
        chats.add(chat)
        return chat.id
    }

    fun sendMessage(chatId: Int, senderId: Int, textMessage: String): Int {
        val message = Message(0, senderId, textMessage)
        chats.firstOrNull { chat ->
            chat.id == chatId && chat.users.contains(senderId)
        }
            ?.let { chat ->
                messagesIds++
                chat.messages += message.copy(id = messagesIds)
                return@let chat
            } ?: return 0
        return messagesIds
    }

    private fun installNextChatId(chat: Chat): Chat {
        chatsIds++
        return chat.copy(id = chatsIds)
    }

    fun deleteMessage(chatId: Int, messageId: Int): Boolean {
        val chat = chats.firstOrNull { chat -> chat.id == chatId } ?: return false
        val message = chat.messages.firstOrNull { message -> message.id == messageId } ?: return false
        if (chat.messages.size == 1) chats.remove(chat)
        return chat.messages.remove(message)
    }

    fun getUnreadChatsCount(userId: Int): Int {
        return chats.filter { chat -> chat.messages.any { message -> !message.isRead && message.ownerId != userId } }.size
    }

    fun getMessage(chatId: Int, messageId: Int, countMessage: Int, userId: Int): List<Message> {
        val chat = chats.firstOrNull { chat -> chat.id == chatId } ?: return emptyList()
        val updateMessages = chat.messages
            .filter { message -> message.ownerId != userId }
            .map { message -> message.copy(isRead = true) }
            .filter { message -> message.id >= messageId }
            .take(countMessage)
        val updateChat = chat.copy(messages = updateMessages as MutableList<Message>)
        chats.removeIf { updateChat.id == it.id }
        chats.add(updateChat)
        return updateMessages
    }

    fun deleteChat(chatId: Int, userId: Int): Boolean {
        val chat = chats.firstOrNull { chat -> chat.id == chatId && chat.users.contains(userId) } ?: return false
        return chats.remove(chat)
    }

    fun chatClear() {
        chatsIds = 0
        messagesIds = 0
        chats.clear()
    }

    fun messageCount(chatId: Int): Int {
        val chat = chats.firstOrNull { it.id == chatId } ?: return 0
        return chat.messages.size
    }

    fun chatsCount(): Int {
        return chats.size
    }

}