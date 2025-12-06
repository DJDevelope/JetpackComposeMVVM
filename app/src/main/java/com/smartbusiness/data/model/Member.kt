package com.smartbusiness.data.model

data class Member(
    val id: String,
    val name: String,
    val phone: String,
    val avatarUrl: String,
    val isAdmin: Boolean,
    val isVerified: Boolean
)