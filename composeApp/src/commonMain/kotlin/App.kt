import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import com.santimattius.kmp.skeleton.RootScreen
import com.santimattius.kmp.skeleton.core.ui.themes.AppThemeContainer
import com.santimattius.kmp.skeleton.di.applicationModules
import org.koin.compose.KoinApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.logger.Level
import org.koin.dsl.koinConfiguration

@OptIn(ExperimentalCoilApi::class, KoinExperimentalAPI::class)
@Composable
fun App(deepLinkUri: String? = null) {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components {
                add(KtorNetworkFetcherFactory())
            }
            .build()
    }

    KoinApplication(
        configuration = koinConfiguration { modules(applicationModules()) },
        logLevel = Level.INFO,
        content = {
            AppThemeContainer {
                RootScreen(deepLinkUri = deepLinkUri)
            }
        })
}