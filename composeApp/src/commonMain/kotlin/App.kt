
import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import com.santimattius.kmp.skeleton.RootScreen
import com.santimattius.kmp.skeleton.core.ui.themes.AppThemeContainer
import com.santimattius.kmp.skeleton.di.applicationModules
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.koinConfiguration

@OptIn(ExperimentalCoilApi::class, KoinExperimentalAPI::class)
@Composable
fun App() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components {
                add(KtorNetworkFetcherFactory())
            }
            .build()
    }

    KoinMultiplatformApplication(
        config = koinConfiguration { modules(applicationModules()) }
    ) {
        AppThemeContainer {
            RootScreen()
        }
    }
}