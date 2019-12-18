package domain

import kotlin.coroutines.CoroutineContext

actual val uiDispatcher: CoroutineContext
    get() = IosMainDispatcher

actual val defaultDispatcher: CoroutineContext
    get() = IosMainDispatcher