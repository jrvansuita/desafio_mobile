package com.example.toolthirdparty.di

import com.example.toolthirdparty.analytics.AnalyticsWrapper
import com.example.toolthirdparty.analytics.AnalyticsWrapperImpl
import com.example.toolthirdparty.auth.AuthWrapper
import com.example.toolthirdparty.auth.AuthWrapperImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val thirdPartyModule = module {
	singleOf<AnalyticsWrapper>(::AnalyticsWrapperImpl)
	singleOf<AuthWrapper>(::AuthWrapperImpl)
}