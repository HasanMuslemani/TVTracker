package com.hasanmuslemani.tvtracker.presentation.tv_details

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hasanmuslemani.tvtracker.common.Constants
import com.hasanmuslemani.tvtracker.data.repository.TVShowDetailsRepositoryImpl
import com.hasanmuslemani.tvtracker.domain.model.TVDetails
import com.hasanmuslemani.tvtracker.presentation.common.BackButton
import com.hasanmuslemani.tvtracker.presentation.common.ExpandableText
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun TVDetailsScreen(
    viewModel: TVDetailsViewModel = viewModel(),
    navController: NavController
) {
    Box(
        modifier = Modifier
            .background(Color(19, 28, 48))
            .fillMaxSize()
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
        } else if (viewModel.state.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        } else {
            val tvDetails = state.tvDetails
            if (tvDetails != null) {
                TVDetailsItem(tvDetails = tvDetails, navController = navController)
            }
        }
    }
}

@Composable
fun TVDetailsItem(tvDetails: TVDetails, navController: NavController) {
    val constraints = ConstraintSet {
        val posterImg = createRefFor("posterImg")
        val backdropImg = createRefFor("backdropImg")
        val name = createRefFor("name")
        val firstAirDate = createRefFor("firstAirDate")
        val status = createRefFor("status")
        val overview = createRefFor("overview")
        val backBtn = createRefFor("backBtn")

        constrain(posterImg) {
            bottom.linkTo(firstAirDate.bottom)
            start.linkTo(backdropImg.start, margin = 10.dp)
            width = Dimension.value(125.dp)
            height = Dimension.value(200.dp)
        }

        constrain(backdropImg) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            height = Dimension.value(250.dp)
        }

        constrain(backBtn) {
            top.linkTo(parent.top, 10.dp)
            start.linkTo(parent.start, 10.dp)
            width = Dimension.value(45.dp)
            height = Dimension.value(45.dp)
        }

        constrain(name) {
            top.linkTo(backdropImg.bottom, margin = 10.dp)
            start.linkTo(posterImg.end, margin = 10.dp)
            end.linkTo(parent.end, margin = 10.dp)
            width = Dimension.fillToConstraints
        }

        constrain(status) {
            top.linkTo(name.bottom)
            start.linkTo(posterImg.end, margin = 10.dp)
            end.linkTo(parent.end, margin = 10.dp)
            width = Dimension.fillToConstraints
        }

        constrain(firstAirDate) {
            top.linkTo(status.bottom)
            start.linkTo(posterImg.end, margin = 10.dp)
            end.linkTo(parent.end, margin = 10.dp)
            width = Dimension.fillToConstraints
        }

        constrain(overview) {
            top.linkTo(posterImg.bottom, margin = 10.dp)
            start.linkTo(parent.start, margin = 10.dp)
            end.linkTo(parent.end, margin = 10.dp)
            width = Dimension.fillToConstraints
        }
    }
    ConstraintLayout(
        constraints,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .layoutId("backdropImg")
                .fillMaxSize()
                .background(Color.White)
        ) {
            GlideImage(
                circularReveal = CircularReveal(duration = 250),
                imageModel = Constants.BASE_IMAGE_URL + tvDetails.backdropPath
            )
        }
        Card(
            modifier = Modifier
                .layoutId("posterImg"),
            elevation = 5.dp
        ) {
            GlideImage(
                circularReveal = CircularReveal(duration = 250),
                imageModel = Constants.BASE_IMAGE_URL + tvDetails.posterPath
            )
        }
        BackButton(
            navController = navController,
            modifier = Modifier
                .layoutId("backBtn")
        )
        Text(
            tvDetails.name ?: "Unknown title",
            fontSize = 22.sp,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .layoutId("name")

        )
        Text(
            tvDetails.status ?: "Unknown status",
            fontSize = 18.sp,
            color = Color.Yellow,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .layoutId("status")
        )
        Text(
            "First Aired: " + (tvDetails.firstAirDate ?: "Unknown date"),
            fontSize = 15.sp,
            color = Color.Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .layoutId("firstAirDate")
        )
        Box(
            modifier = Modifier
                .layoutId("overview")
        ) {
            ExpandableText(
                text = tvDetails.overview ?: "Unknown description",
                fontSize = 18.sp,
                color = Color.White,
                maxLines = 6,
            )
        }

    }
}