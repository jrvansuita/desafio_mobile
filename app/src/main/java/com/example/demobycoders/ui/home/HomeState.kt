package com.example.demobycoders.ui.home

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize


@Parcelize
data class HomeState(
	val position: LatLng
) : Parcelable
