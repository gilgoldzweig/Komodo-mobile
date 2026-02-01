package ca.glong.komodo.shared.infra.state

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow

sealed class ImmutableStateFlow<T, E> : Flow<State<T, E>> {
    protected val state = MutableStateFlow<State<T, E>>(State.Created())

    val current: State<T, E>
        get() = state.value

    override suspend fun collect(collector: FlowCollector<State<T, E>>) {
        state.collect(collector)
    }

    suspend fun collect(
        success: (T) -> Unit = {},
        error: (E) -> Unit = {},
        loading: () -> Unit = {},
        created: () -> Unit = {},
        completed: () -> Unit = {}
    ) {
        state.collect {
            when (it) {
                is State.Success -> success(it.data)
                is State.Error -> error(it.error)
                is State.Loading -> loading()
                is State.Created -> created()
                is State.Completed -> completed()
            }
        }
    }
}

class MutableStateFlow<T, E> : ImmutableStateFlow<T, E>() {
    fun emitCreated() {
        state.value = State.Created()
    }

    fun emitLoading() {
        state.value = State.Loading()
    }

    fun emitError(error: E) {
        state.value = State.Error(error)
    }

    fun emitSuccess(data: T) {
        state.value = State.Success(data)
    }

    fun emitCompleted() {
        state.value = State.Completed()
    }
}

typealias UnitStateFlow = ImmutableStateFlow<Unit, Throwable>
typealias MutableUnitStateFlow = ca.glong.komodo.shared.infra.state.MutableStateFlow<Unit, Throwable>

typealias DefaultStateFlow<T> = ImmutableStateFlow<T, Throwable>
typealias MutableDefaultStateFlow<T> = ca.glong.komodo.shared.infra.state.MutableStateFlow<T, Throwable>
