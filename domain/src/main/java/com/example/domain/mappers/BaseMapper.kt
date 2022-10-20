package com.example.domain.mappers

interface BaseMapper<T1, T2> {
    fun createFromList(list: List<T1>): List<T2>
    fun createFromObject(value: T1): T2
}