package com.hasanmuslemani.tvtracker.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.hasanmuslemani.tvtracker.data.repository.TVSearchRepositoryImpl
import com.hasanmuslemani.tvtracker.domain.model.TVSearch
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

val viewModel = MainActivityViewModel(TVSearchRepositoryImpl())

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if(viewModel.state.value.error.isNotBlank()) {
                Text("ERROR!!!!")
            }
            else if(viewModel.state.value.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            else {
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
                .padding(10.dp)) {
            Box(modifier = Modifier
                .background(Color(27, 40, 70))
                .layoutId("box"))
            GlideImage(
                modifier = Modifier
                    .layoutId("image"),
                failure = {
                    Text(text = "image request failed.")
                },
                circularReveal = CircularReveal(duration = 250),
                imageModel = "https://m.media-amazon.com/images/M/MV5BMDU2ZWJlMjktMTRhMy00ZTA5LWEzNDgtYmNmZTEwZTViZWJkXkEyXkFqcGdeQXVyNDQ2OTk4MzI@._V1_.jpg")
            Text(
                text = tvSearch.title ?: "N/A",
                modifier = Modifier
                    .layoutId("title")
                    .padding(10.dp))
        }
    }
}