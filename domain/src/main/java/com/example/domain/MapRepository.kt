package com.example.domain

interface MapRepository {
    suspend fun getPoints(): List<Points>
}