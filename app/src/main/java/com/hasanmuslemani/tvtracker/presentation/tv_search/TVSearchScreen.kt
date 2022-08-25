package com.hasanmuslemani.tvtracker.presentation.tv_search

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hasanmuslemani.tvtracker.common.Constants
import com.hasanmuslemani.tvtracker.data.repository.TVSearchRepositoryImpl
import com.hasanmuslemani.tvtracker.data.repository.TVShowDetailsRepositoryImpl
import com.hasanmuslemani.tvtracker.domain.model.TVSearch
import com.hasanmuslemani.tvtracker.presentation.MainActivity
import com.hasanmuslemani.tvtracker.presentation.MainActivityViewModel
import com.hasanmuslemani.tvtracker.presentation.MainActivityViewModelFactory
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.qualifiers.ApplicationContext

@Composable
fun TVSearchScreen(
    viewModel: MainActivityViewModel = viewModel(
        factory = MainActivityViewModelFactory(
            TVSearchRepositoryImpl(), TVShowDetailsRepositoryImpl()
        )
    )
) {
    if (viewModel.state.value.error.isNotBlank()) {
        Text("ERROR!!!!")
    } else if (viewModel.state.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else {
        Toast.makeText(
            LocalContext.current,
            "Vote Count: " + viewModel.detailsState.value,
            Toast.LENGTH_LONG
        ).show()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(19, 28, 48))
        ) {
            items(viewModel.state.value.tvSearches) { tvSearch ->
                TVSearchItem(tvSearch)
            }
        }
    }
}

@Composable
fun TVSearchItem(tvSearch: TVSearch) {
    val constraints = ConstraintSet {
        val box = createRefFor("box")
        val image = createRefFor("image")
        val title = createRefFor("title")

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
        }
        constrain(image) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            width = Dimension.value(70.dp)
            height = Dimension.value(100.dp)
        }
    }

    ConstraintLayout(
        constraints,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .background(Color(27, 40, 70))
                .layoutId("box")
        )
        GlideImage(
            modifier = Modifier
                .layoutId("image"),
            failure = {
                Text(text = "image request failed.")
            },
            circularReveal = CircularReveal(duration = 250),
            imageModel = Constants.BASE_IMAGE_URL + tvSearch.imagePath
        )
        Text(
            text = tvSearch.title ?: "N/A",
            modifier = Modifier
                .layoutId("title")
                .padding(10.dp)
        )
    }
}