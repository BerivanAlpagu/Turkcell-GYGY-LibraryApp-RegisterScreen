package com.turkcell.libraryappv2.data.repository

import kotlinx.coroutines.delay
import kotlin.random.Random

class AuthRepository {

    suspend fun signIn(email: String, password: String):  Result<Unit> = runCatching {
        delay(timeMillis = 2000) // dışarıya istek atar gibi

        val isSucces =Random.nextBoolean()
        if(isSucces)
            Unit
        else
            throw Exception("Fake login failed")
    }

    suspend fun signUp(name: String, email: String, password: String): Result<Unit> = runCatching {
        delay(timeMillis = 2000) // dışarıya istek atar gibi
        
        // Simülasyon: Eğer daha önce kayıtlı bir email girildiyse (örneğin test@test.com) hata fırlat
        if (email == "test@test.com") {
            throw Exception("Bu e-posta adresi zaten kayıtlı!")
        }

        val isSuccess = Random.nextBoolean()
        if(isSuccess)
            Unit
        else
            throw Exception("Fake register failed")
    }
}
