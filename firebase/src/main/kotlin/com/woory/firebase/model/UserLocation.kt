package com.woory.firebase.model

import com.google.firebase.firestore.GeoPoint

data class UserLocation(
    val id: String = "",
    val location: GeoPoint = GeoPoint(0.0, 0.0)
)