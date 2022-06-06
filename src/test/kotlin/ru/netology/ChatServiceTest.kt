package ru.netology

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class ChatServiceTest {

    @Before
    fun setUp() {
        ChatService.chatClear()
    }

    @Test
    fun createChatTrue() {
        val expected = 1
        val actual = ChatService.createChat(666, 777, "Текст")
        assertEquals(expected, actual)
    }

    @Test
    fun createChatAddMessage() {
        val expected = 1
        ChatService.createChat(666, 777, "Текст первое сообщение")
        val actual = ChatService.createChat(666, 777, "Текст второе сообщение")
        assertEquals(expected, actual)
    }

    @Test
    fun sendMessageTrue() {
        val expected = 2
        ChatService.createChat(666, 777, "Текст первое сообщение")
        ChatService.sendMessage(1, 777, "Второе сообщение")
        val actual = ChatService.messageCount(1)
        assertEquals(expected, actual)
    }

    @Test
    fun sendMessageFalseChatId() {
        val expected = 1
        ChatService.createChat(666, 777, "Текст первое сообщение")
        ChatService.sendMessage(2, 777, "Второе сообщение")
        val actual = ChatService.messageCount(1)
        assertEquals(expected, actual)
    }

    @Test
    fun sendMessageFalseUserId() {
        val expected = 1
        ChatService.createChat(666, 777, "Текст первое сообщение")
        ChatService.sendMessage(1, 888, "Второе сообщение")
        val actual = ChatService.messageCount(1)
        assertEquals(expected, actual)
    }

    @Test
    fun sendMessageFalseChatIdUserId() {
        val expected = 1
        ChatService.createChat(666, 777, "Текст первое сообщение")
        ChatService.sendMessage(2, 888, "Второе сообщение")
        val actual = ChatService.messageCount(1)
        assertEquals(expected, actual)
    }

    @Test
    fun deleteMessageTrue() {
        ChatService.createChat(666, 777, "Текст первое сообщение")
        ChatService.createChat(666, 777, "Текст второе сообщение")
        val actual = ChatService.deleteMessage(1, 1)
        assertTrue(actual)
    }

    @Test
    fun deleteMessageTrueDeleteChat() {
        val expected = 0
        ChatService.createChat(666, 777, "Текст первое сообщение")
        ChatService.deleteMessage(1, 1)
        val actual = ChatService.chatsCount()
        assertEquals(expected, actual)
    }

    @Test
    fun deleteMessageFalseChatId() {
        ChatService.createChat(666, 777, "Текст первое сообщение")
        ChatService.createChat(666, 777, "Текст второе сообщение")
        val actual = ChatService.deleteMessage(2, 1)
        assertFalse(actual)
    }

    @Test
    fun deleteMessageFalseMessageId() {
        ChatService.createChat(666, 777, "Текст первое сообщение")
        ChatService.createChat(666, 777, "Текст второе сообщение")
        val actual = ChatService.deleteMessage(1, 3)
        assertFalse(actual)
    }

    @Test
    fun getUnreadChatsCount() {
        val expected = 2
        ChatService.createChat(666, 777, "Текст первое сообщение")
        ChatService.createChat(888, 999, "Текст первое сообщение")
        ChatService.getMessage(2, 2, 10,666)
        ChatService.createChat(444, 555, "Текст первое сообщение")
        val actual = ChatService.getUnreadChatsCount(888)
        assertEquals(expected, actual)
    }

    @Test
    fun getMessageTrue() {
        val expected = 3
        ChatService.createChat(666, 777, "Текст первое сообщение")
        ChatService.sendMessage(1, 777, "Второе сообщение")
        ChatService.sendMessage(1, 666, "Третье сообщение")
        ChatService.sendMessage(2, 666, "Не добавится, т.к. не тот чат")
        ChatService.sendMessage(1, 666, "Четвертое сообщение")
        ChatService.sendMessage(1, 666, "Пятое сообщение")
        val actual = ChatService.getMessage(1, 2, 4,777).size
        assertEquals(expected, actual)
    }

    @Test
    fun getMessageEmptyList() {
        val expected = 0
        ChatService.createChat(666, 777, "Текст первое сообщение")
        ChatService.sendMessage(1, 777, "Второе сообщение")
        ChatService.sendMessage(1, 666, "Третье сообщение")
        ChatService.sendMessage(2, 666, "Не добавится, т.к. не тот чат")
        ChatService.sendMessage(1, 666, "Четвертое сообщение")
        ChatService.sendMessage(1, 666, "Пятое сообщение")
        val actual = ChatService.getMessage(2, 1, 4,666).size
        assertEquals(expected, actual)
    }

    @Test
    fun deleteChatTrue() {
        ChatService.createChat(666, 777, "Текст первое сообщение")
        ChatService.createChat(555, 444, "Текст первое сообщение")
        ChatService.createChat(333, 222, "Текст первое сообщение")
        val actual = ChatService.deleteChat(2, 444)
        assertTrue(actual)
    }

    @Test
    fun deleteChatFalseNotChatId() {
        ChatService.createChat(666, 777, "Текст первое сообщение")
        ChatService.createChat(555, 444, "Текст первое сообщение")
        ChatService.createChat(333, 222, "Текст первое сообщение")
        val actual = ChatService.deleteChat(4, 444)
        assertFalse(actual)
    }

    @Test
    fun deleteChatFalseNotUserId() {
        ChatService.createChat(666, 777, "Текст первое сообщение")
        ChatService.createChat(555, 444, "Текст первое сообщение")
        ChatService.createChat(333, 222, "Текст первое сообщение")
        val actual = ChatService.deleteChat(2, 777)
        assertFalse(actual)
    }

    @Test
    fun messageCountTrue() {
        val expected = 5
        ChatService.createChat(666, 777, "Текст первое сообщение")
        ChatService.sendMessage(1, 777, "Второе сообщение")
        ChatService.sendMessage(1, 666, "Третье сообщение")
        ChatService.sendMessage(2, 666, "Не добавится, т.к. не тот чат")
        ChatService.sendMessage(1, 666, "Четвертое сообщение")
        ChatService.sendMessage(1, 666, "Пятое сообщение")
        val actual = ChatService.messageCount(1)
        assertEquals(expected, actual)
    }

    @Test
    fun messageCountFalse() {
        val expected = 0
        ChatService.createChat(666, 777, "Текст первое сообщение")
        ChatService.sendMessage(1, 777, "Второе сообщение")
        ChatService.sendMessage(1, 666, "Третье сообщение")
        ChatService.sendMessage(2, 666, "Не добавится, т.к. не тот чат")
        ChatService.sendMessage(1, 666, "Четвертое сообщение")
        ChatService.sendMessage(1, 666, "Пятое сообщение")
        val actual = ChatService.messageCount(2)
        assertEquals(expected, actual)
    }
}