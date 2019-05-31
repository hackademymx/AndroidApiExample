package com.hackademy.hackademyapiconsumption

import com.google.gson.annotations.SerializedName

data class UserApiResponse(
    val results: List<UserInfo>,
    val info: Any
)

data class UserInfo(
    @SerializedName("name")
    val username: UserName,
    val email: String,
    val phone: String
)

data class UserName(
    val title: String,
    val first: String,
    val last: String
)