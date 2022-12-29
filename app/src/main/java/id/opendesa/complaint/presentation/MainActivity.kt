package id.opendesa.complaint.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import id.opendesa.complaint.presentation.screen.list.ListScreen
import id.opendesa.complaint.presentation.theme.OpenDesaComplaintTheme
import id.opendesa.complaint.utils.Router

@AndroidEntryPoint
@ExperimentalAnimationApi @ExperimentalMaterial3Api @ExperimentalMaterialApi class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenDesaComplaintTheme {
                val navController = rememberAnimatedNavController()

                AnimatedNavHost(navController = navController, startDestination = Router.LIST) {
                    composable(route = Router.LIST, enterTransition = {
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300)
                        )
                    }, exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300)
                        )
                    }, popEnterTransition = {
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(300)
                        )
                    }, popExitTransition = {
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(300)
                        )
                    }) {
                        ListScreen(navController = navController)
                    }
                }
            }
        }
    }
}