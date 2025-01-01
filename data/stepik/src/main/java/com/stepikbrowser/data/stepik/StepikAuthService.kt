package com.stepikbrowser.data.stepik

import com.google.gson.annotations.SerializedName
import retrofit2.http.*

interface StepikAuthService {
    @FormUrlEncoded
    @POST("oauth2/token/")
    suspend fun getAccessToken(
        @Field("grant_type") grantType: String = "client_credentials",
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): AccessTokenResponse
}
data class AccessTokenResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("expires_in") val expiresIn: Int,
    @SerializedName("token_type") val tokenType: String
)
