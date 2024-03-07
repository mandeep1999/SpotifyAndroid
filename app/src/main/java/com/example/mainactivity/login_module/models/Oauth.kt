package com.example.mainactivity.login_module.models

import com.google.gson.annotations.SerializedName

data class Oauth(

    @SerializedName("access_token")
    val accessToken: String? = null,

    @SerializedName("token_type")
    val tokenType: String? = null
)
