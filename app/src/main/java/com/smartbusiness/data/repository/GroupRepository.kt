package com.smartbusiness.data.repository


import com.smartbusiness.data.model.GroupDetails

interface GroupRepository {
    suspend fun getGroupDetails(groupId: String): GroupDetails
}