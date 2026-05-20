import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
	var body: some Scene {
		WindowGroup {
			ContentView()
				.onOpenURL { url in
					// Forward `kmpapp://character/{id}` deep links to the Kotlin layer.
					// MainViewControllerKt.setDeepLinkUri updates a MutableStateFlow that
					// triggers Compose recomposition, which passes the URI to Navigation.kt
					// via App(deepLinkUri:) → RootScreen(deepLinkUri:) → Navigation(deepLinkUri:).
					// parseDeepLink() in commonMain handles the URI → Destination conversion.
					MainViewControllerKt.setDeepLinkUri(uri: url.absoluteString)
				}
		}
	}
}