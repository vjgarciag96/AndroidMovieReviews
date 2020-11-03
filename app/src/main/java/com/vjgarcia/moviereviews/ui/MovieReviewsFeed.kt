import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AmbientEmphasisLevels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideEmphasis
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import com.vjgarcia.moviereviews.presentation.MovieReview
import com.vjgarcia.moviereviews.presentation.MovieReviewsFeedViewModel
import com.vjgarcia.moviereviews.ui.core.MovieReviewsScreen
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun MovieReviewsFeed(viewModel: MovieReviewsFeedViewModel) {
    MovieReviewsScreen {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
        ) {
            Text(
                text = "Movie reviews",
                style = MaterialTheme.typography.h1.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(16.dp)
            )
            LazyColumnFor<MovieReview>(
                items = viewModel.movieReviews,
                modifier = Modifier.weight(1f)
            ) { movieReview ->
                MovieReviewRow(
                    title = movieReview.title,
                    image = movieReview.image,
                    publicationDate = movieReview.publicationDate,
                    author = movieReview.author
                )
            }
        }
    }
}

@Composable
fun MovieReviewRow(
    title: String,
    image: String,
    publicationDate: String,
    author: String
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .clickable(onClick = {})
            .padding(16.dp)
    ) {
        Surface(
            modifier = Modifier.preferredSize(60.dp).align(Alignment.CenterVertically),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {
            CoilImage(
                data = image,
                fadeIn = true,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .preferredSize(60.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Column(modifier = Modifier.fillMaxWidth().padding(start = 16.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold)
            Text(text = author, fontSize = 14.sp)
            ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.medium) {
                Text(
                    text = publicationDate,
                    modifier = Modifier.align(AbsoluteAlignment.Right),
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MovieReviewsFeed(viewModel = MovieReviewsFeedViewModel())
}

@Preview
@Composable
fun RowPreview() {
    MovieReviewRow(
        title = "headline",
        image = "https://static01.nyt.com/images/2020/08/21/arts/20cutthroat-art/merlin_175717185_b8fc0d22-6a73-4e05-b6dd-741ba3aae81a-mediumThreeByTwo210.jpg",
        publicationDate = "2020-09-12",
        author = "Víctor J"
    )
}