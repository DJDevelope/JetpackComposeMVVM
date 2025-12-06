package com.smartbusiness.data.repository


import com.smartbusiness.data.model.GroupDetails
import com.smartbusiness.data.model.MediaItem
import com.smartbusiness.data.model.Member
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupRepositoryImpl @Inject constructor() : GroupRepository {

    // Simulating API call with mock data
    override suspend fun getGroupDetails(groupId: String): GroupDetails {
        delay(1000) // Simulate network delay

        return GroupDetails(
            id = groupId,
            name = "Alice Henry",
            description = "Group description",
            createdBy = "You",
            createdOn = "12/10/2025, 12:10",
            membersCount = 3,
            groupImageUrl = "https://picsum.photos/200/200?random=1",
            mediaItems = listOf(
                MediaItem("1", "https://picsum.photos/150/150?random=2"),
                MediaItem("2", "https://picsum.photos/150/150?random=3"),
                MediaItem("3", "https://picsum.photos/150/150?random=4")
            ),
            members = listOf(
                Member(
                    id = "1",
                    name = "Alice Henry",
                    phone = "+821 343 425",
                    avatarUrl = "https://picsum.photos/100/100?random=5",
                    isAdmin = true,
                    isVerified = true
                ),
                Member(
                    id = "2",
                    name = "Theresa Webb",
                    phone = "+821 343 426",
                    avatarUrl = "https://picsum.photos/100/100?random=6",
                    isAdmin = false,
                    isVerified = true
                ),
                Member(
                    id = "3",
                    name = "Wade Warren",
                    phone = "+821 343 427",
                    avatarUrl = "https://picsum.photos/100/100?random=7",
                    isAdmin = false,
                    isVerified = true
                ),
                Member(
                    id = "4",
                    name = "Albert Flores",
                    phone = "+821 343 428",
                    avatarUrl = "https://picsum.photos/100/100?random=8",
                    isAdmin = false,
                    isVerified = false
                )
            )
        )
    }
}