package com.zeemoog.marvelcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.zeemoog.marvelcompose.ui.MarvelApp
import com.zeemoog.marvelcompose.ui.navigation.Navigation

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelApp()
        }
    }
}
