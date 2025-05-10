package com.example.musicapp.Model

data class Playlist(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val coverImageUrl: String = "",
    val createdBy: String = "",
    val createdAt: String = "",
    val totalSongs: Int = 0,
    val isPublic: Boolean = true,
    val songs: Map<String, PlaylistSong> = mapOf()
)

data class PlaylistSong(
    val position: Int = 0,
    val addedAt: String = ""
)