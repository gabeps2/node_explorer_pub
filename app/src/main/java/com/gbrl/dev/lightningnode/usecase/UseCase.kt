package com.gbrl.dev.lightningnode.usecase

import com.gbrl.dev.lightningnode.repository.Result

abstract class UseCase<in P, out T> {
    abstract suspend fun run(params: P): Result<T>

    suspend operator fun invoke(params: P): Result<T> =
        runCatching {
            run(params)
        }.getOrElse {
            Result.failure(it)
        }
}