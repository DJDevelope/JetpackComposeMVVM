package com.smartbusiness.data.model

data class GroupDetails(
    val id: String,
    val name: String,
    val description: String,
    val createdBy: String,
    val createdOn: String,
    val membersCount: Int,
    val groupImageUrl: String,
    val mediaItems: List<MediaItem>,
    val members: List<Member>
)