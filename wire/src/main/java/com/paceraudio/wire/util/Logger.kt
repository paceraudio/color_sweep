package com.paceraudio.wire.util

interface ILogger {
    fun d(tag: String, message: String)
    fun e(tag: String, message: String)
}

class ConsoleLogger : ILogger {
    override fun d(tag: String, message: String) {
        println("$tag: $message")
    }

    override fun e(tag: String, message: String) {
        println("$tag: $message")
    }
}