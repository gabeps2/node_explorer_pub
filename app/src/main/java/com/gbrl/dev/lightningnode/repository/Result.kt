package com.gbrl.dev.lightningnode.repository

class Result<out R>(
    val value: Any? = null
) {
    val isSuccess = value !is Throwable

    fun toValue(): R? = when {
        isSuccess -> value as R
        else -> null
    }

    fun toException(): Throwable? = when {
        isSuccess.not() -> value as Throwable
        else -> null
    }

    companion object {
        fun <T> success(value: T): Result<T> = Result(value)
        fun <T> failure(throwable: Throwable): Result<T> = Result(throwable)
    }
}

inline fun <T> result(
    block: () -> T
): Result<T> = runCatching {
    Result.success(block())
}.getOrElse {
    Result.failure(it)
}

inline fun <reified T> Result<T>.onSuccess(
    block: (T) -> Unit
): Result<T> {
    if (isSuccess && value is T) {
        block(value)
        return Result.success(value)
    }
    return this
}

inline fun <T> Result<T>.onFailure(
    block: (Throwable) -> Unit
): Result<T> {
    if (isSuccess.not()) {
        block(value as Throwable)
        return Result.failure(value)
    }
    return this
}

inline fun <reified T, R> Result<T>.map(
    transform: (value: T) -> R
): Result<R> =
    if (isSuccess && value is T) {
        result { transform(value) }
    } else {
        Result(value)
    }

inline fun <reified T> Result<T?>.notNull(): Result<T> = map { it ?: throw NullPointerException() }