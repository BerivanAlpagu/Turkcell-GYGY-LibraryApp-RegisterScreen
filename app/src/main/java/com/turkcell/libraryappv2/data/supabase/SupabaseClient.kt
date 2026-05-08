package com.turkcell.libraryappv2.data.supabase

import com.turkcell.libraryappv2.BuildConfig
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

val supabase = createSupabaseClient(
    supabaseKey= BuildConfig.SUPABASE_ANON_KEY,
    supabaseUrl = BuildConfig.SUPABASE_URL
){
    install(Postgrest)
    install(Auth)
}