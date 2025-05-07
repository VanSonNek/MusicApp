package com.example.musicapp.Model

data class BaiHat(
    val id: String = "",
    val title: String = "",
    val artist: String = "",
    val imageUrl: String = "",
    val luotNghe: Int
) {
    constructor() : this("", "", "", "", 0)
}
