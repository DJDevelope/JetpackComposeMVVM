package com.smartbusiness.data.api

import com.smartbusiness.data.model.GroupDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface GroupApiService {
    @GET("groups/{id}/details")
    suspend fun getGroupDetails(@Path("id") groupId: String): GroupDetails
}