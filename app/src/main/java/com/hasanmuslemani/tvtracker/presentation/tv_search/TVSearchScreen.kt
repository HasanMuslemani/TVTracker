package com.hasanmuslemani.tvtracker.presentation.tv_search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hasanmuslemani.tvtracker.common.Constants
import com.hasanmuslemani.tvtracker.data.repository.TVSearchRepositoryImpl
import com.hasanmuslemani.tvtracker.domain.model.TVSearch
import com.hasanmuslemani.tvtracker.presentation.Screen
import com.hasanmuslemani.tvtracker.presentation.common.SearchBar
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun TVSearchScreen(
    navController: NavController
) {
    val viewModel: TVSearchViewModel = hiltViewModel()
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = Color(0xFF001529)) {
                SearchBar(
                    placeholder = "Search TV Show...",
                    onTextChange = {
                        viewModel.searchDebounced(it)
                    },
                    onSearchClicked = {
                        viewModel.getTVSearches(it)
                    }
                )
            }
        }
    ) {
        Box(
            modifier = Modifier.background(Color(19, 28, 48))
        ) {
            val state = viewModel.state.value
            if (state.error.isNotBlank()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        state.error,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 24.sp
                    )
                }
            } else if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(state.tvSearches) { tvSearch ->
                        TVSearchItem(tvSearch) {
                            navController.navigate(Screen.TVDetailsScreen.route + "/${tvSearch.id}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TVSearchItem(tvSearch: TVSearch, onClick: () -> Unit) {
    val constraints = ConstraintSet {
        val box = createRefFor("box")
        val image = createRefFor("image")
        val title = createRefFor("title")
        val firstAirDate = createRefFor("firstAirDate")

        constrain(box) {
            top.linkTo(title.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
            bottom.linkTo(parent.bottom)
        }
        constrain(title) {
            top.linkTo(parent.top, 20.dp)
            start.linkTo(image.end)
            end.linkTo(parent.end, margin = 10.dp)
            width = Dimension.fillToConstraints
        }
        constrain(image) {
            start.linkTo(parent.start, 10.dp)
            bottom.linkTo(box.bottom, 10.dp)
            width = Dimension.value(80.dp)
            height = Dimension.value(115.dp)
        }
        constrain(firstAirDate) {
            top.linkTo(title.bottom)
            start.linkTo(image.end, margin = 10.dp)
            end.linkTo(parent.end, margin = 10.dp)
            width = Dimension.fillToConstraints
        }
    }

    ConstraintLayout(
        constraints,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .background(Color(27, 40, 70))
                .layoutId("box")
        )

        Card(
            modifier = Modifier.layoutId("image")
        ) {
            GlideImage(
                circularReveal = CircularReveal(duration = 250),
                imageModel = Constants.BASE_IMAGE_URL + tvSearch.imagePath
            )
        }
        Text(
            text = tvSearch.title ?: "No title",
            fontSize = 18.sp,
            color = Color.White,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
                .layoutId("title")
                .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 5.dp)
        )
        Text(
            "First Aired: " + (tvSearch.firstAired ?: "Unknown date"),
            fontSize = 15.sp,
            color = Color.Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .layoutId("firstAirDate")
        )
    }
}