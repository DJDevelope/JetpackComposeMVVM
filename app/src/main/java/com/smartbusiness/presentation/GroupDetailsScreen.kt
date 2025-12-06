package com.smartbusiness.presentation


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.smartbusiness.data.model.GroupDetails

import androidx.compose.material3.Icon
import com.smartbusiness.data.model.MediaItem
import com.smartbusiness.data.model.Member


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDetailsScreen(
    viewModel: GroupDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = androidx.compose.material3.SnackbarHostState()

    LaunchedEffect(uiState.lastAction) {
        uiState.lastAction?.let { action ->
            snackbarHostState.showSnackbar(action)
            viewModel.clearAction()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onBackClicked() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                uiState.errorMessage != null -> {
                    Text(
                        text = "Error: ${uiState.errorMessage}",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Red
                    )
                }
                uiState.group != null -> {
                    GroupDetailsContent(
                        group = uiState.group!!,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun GroupDetailsContent(
    group: GroupDetails,
    viewModel: GroupDetailsViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Group Header
        item {
            GroupHeader(group = group)
        }

        // Media Section
        item {
            MediaSection(
                mediaItems = group.mediaItems,
                onMediaClick = { viewModel.onMediaDocsLinkClicked() }
            )
        }

        // Members Header
        item {
            MembersHeader(membersCount = group.membersCount)
        }

        // Add Members Row
        item {
            AddMembersRow(onClick = { viewModel.onAddMembersClicked() })
        }

        // Members List
        items(group.members) { member ->
            MemberItem(
                member = member,
                onClick = { viewModel.onMemberClicked(member.id) }
            )
        }

        // Footer Actions
        item {
            FooterActions(
                onViewAllClick = { viewModel.onViewAllMembersClicked() },
                onDeleteClick = { viewModel.onDeleteGroupClicked() }
            )
        }
    }
}

@Composable
fun GroupHeader(group: GroupDetails) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Group Image
        AsyncImage(
            model = group.groupImageUrl,
            contentDescription = "Group Image",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Group Name
        Text(
            text = group.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Created Info
        Text(
            text = "Created by ${group.createdBy} on ${group.createdOn}",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Members Count
        Text(
            text = "${group.membersCount} Members",
            fontSize = 14.sp,
            color = Color.DarkGray
        )
    }
}

@Composable
fun MediaSection(
    mediaItems: List<MediaItem>,
    onMediaClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Media Header Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onMediaClick() },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Media",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Media, Docs, Link",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Navigate",
                tint = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Media Thumbnails
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(mediaItems.take(2)) { mediaItem ->
                AsyncImage(
                    model = mediaItem.thumbnailUrl,
                    contentDescription = "Media",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            if (mediaItems.size > 2) {
                item {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = mediaItems[2].thumbnailUrl,
                            contentDescription = "Media",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+${mediaItems.size - 2}",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MembersHeader(membersCount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${String.format("%02d", membersCount)} Members",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = Color.Gray
        )
    }
}

@Composable
fun AddMembersRow(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFE3F2FD)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "Add",
                tint = Color(0xFF2196F3)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "Add Members",
            fontSize = 16.sp,
            color = Color(0xFF2196F3),
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun MemberItem(
    member: Member,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        AsyncImage(
            model = member.avatarUrl,
            contentDescription = member.name,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Name and Phone
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = member.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                if (member.isVerified) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Verified",
                        tint = Color(0xFF2196F3),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = member.phone,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        // Admin Badge
        if (member.isAdmin) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFE8F5E9)
            ) {
                Text(
                    text = "Group Admin",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    fontSize = 12.sp,
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun FooterActions(
    onViewAllClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // View All Members
        TextButton(
            onClick = onViewAllClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "View all Members",
                color = Color(0xFF2196F3),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Delete Group
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onDeleteClick() }
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color(0xFFF44336),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Delete Group",
                color = Color(0xFFF44336),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}