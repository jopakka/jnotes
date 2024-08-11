package joonas.niemi.jnotes.core.common.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val jDispatcher: JDispatchers)

enum class JDispatchers {
    Default,
    IO,
}
