package ca.glong.komodo.state

import kotlinx.coroutines.flow.Flow

/**
 * Wrapper class that is able to handle different state
 *
 * @param <T> data to wrap</T>
 * */
sealed class State<T, E> {
    class Created<T, E> : State<T, E>()

    class Loading<T, E> : State<T, E>()

    data class Success<T, E>(val data: T) : State<T, E>()

    data class Error<T, E>(val error: E) : State<T, E>()

    class Completed<T, E> : State<T, E>()
}