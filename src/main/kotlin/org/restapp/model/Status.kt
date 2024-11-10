package org.restapp.model

enum class Status(private val value: String) {
    ALIVE("Alive"), DEAD("Dead"), UNKNOWN("unknown")
}