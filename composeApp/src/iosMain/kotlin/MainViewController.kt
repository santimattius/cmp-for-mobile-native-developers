import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.ComposeUIViewController
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Holds the most recent deep-link URI received from the iOS layer.
 *
 * Swift calls [setDeepLinkUri] whenever the app receives a `kmpapp://` URL via
 * `.onOpenURL`. The [MutableStateFlow] triggers Compose recomposition so that
 * [App] can forward the URI to [Navigation] without recreating the UIViewController.
 */
private val deepLinkUriFlow = MutableStateFlow<String?>(null)

/**
 * Entry point called from Swift/Objective-C to forward a deep-link URI into
 * the Compose layer. Safe to call from the main thread at any time.
 *
 * @param uri The absolute URL string received from the iOS `onOpenURL` handler
 *            (e.g. `kmpapp://character/42`).
 */
fun setDeepLinkUri(uri: String) {
    deepLinkUriFlow.value = uri
}

/** Creates the root [UIViewController] that hosts the Compose hierarchy. */
fun MainViewController() = ComposeUIViewController {
    val deepLinkUri by deepLinkUriFlow.collectAsState()
    App(deepLinkUri = deepLinkUri)
}
