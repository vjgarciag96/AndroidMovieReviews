import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import com.vjgarcia.moviereviews.presentation.MovieReviewCell
import com.vjgarcia.moviereviews.presentation.MovieReviewsFeedViewModel
import com.vjgarcia.moviereviews.ui.core.MovieReviewsScreen
import dev.chrisbanes.accompanist.coil.CoilImage
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun MovieReviewsFeed(viewModel: MovieReviewsFeedViewModel) {
    val showInitialLoading: Boolean by viewModel.showInitialLoading.collectAsState(true)
    val showInitialError: Boolean by viewModel.showInitialError.collectAsState(false)
    val showContent: Boolean by viewModel.showContent.collectAsState(false)
    val movieReviewCells: List<MovieReviewCell> by viewModel.content.collectAsState(emptyList())

    MovieReviewsScreen {
        when {
            showInitialLoading -> InitialLoading()
            showInitialError -> InitialError(onRetryClicked = viewModel::onRetryClicked)
            showContent -> MovieReviewsFeedContent(
                content = movieReviewCells,
                onLoadMoreClicked = viewModel::onLoadMoreClicked,
                onMovieReviewClicked = viewModel::onMovieReviewClicked
            )
        }
    }
}

@Composable
fun InitialLoading() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colors.background)
    ) {
        MovieReviewsFeedTopBar()
        ScrollableColumn(
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colors.background),
        ) {
            repeat(12) {
                MovieReviewLoadingRow()
            }
        }
    }
}

@Composable
fun InitialError(onRetryClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MovieReviewsFeedTopBar()
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentHeight(Alignment.CenterVertically)
        ) {
            Text(
                text = "There was a problem loading movie reviews. " +
                        "Please check your internet connection and retry",
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
            Button(
                onClick = onRetryClicked,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Retry")
            }
        }
    }
}

@Composable
fun MovieReviewsFeedContent(
    content: List<MovieReviewCell>,
    onLoadMoreClicked: () -> Unit,
    onMovieReviewClicked: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        MovieReviewsFeedTopBar()
        LazyColumnFor(
            items = content,
            modifier = Modifier.weight(1f)
        ) { movieReviewCell ->
            when (movieReviewCell) {
                is MovieReviewCell.Content -> MovieReviewRow(
                    id = movieReviewCell.id,
                    title = movieReviewCell.title,
                    image = movieReviewCell.image,
                    publicationDate = movieReviewCell.publicationDate,
                    author = movieReviewCell.author,
                    onClick = onMovieReviewClicked
                )
                MovieReviewCell.LoadMore -> LoadMoreRow(onClick = onLoadMoreClicked)
                MovieReviewCell.LoadingMore -> LoadingMoreRow()
                MovieReviewCell.LoadMoreError -> LoadMoreErrorRow(onClick = onLoadMoreClicked)
            }
        }
    }
}

@Composable
fun MovieReviewsFeedTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Movie reviews",
                style = MaterialTheme.typography.h1.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        backgroundColor = MaterialTheme.colors.surface
    )
}

@Composable
fun MovieReviewRow(
    id: Int,
    title: String,
    image: String,
    publicationDate: String,
    author: String,
    onClick: (Int) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .clickable(onClick = { onClick(id) })
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

@Composable
fun LoadMoreRow(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(16.dp)
    ) {
        Button(
            modifier = Modifier.weight(0.5f),
            onClick = onClick
        ) {
            Text(text = "Load more movie reviews")
        }
    }
}

@Composable
fun LoadingMoreRow() {
    MovieReviewLoadingRow()
}

@Composable
fun LoadMoreErrorRow(onClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colors.background)) {
        Text(
            text = "There was an error loading more movie reviews. " +
                    "Please check your internet connection and try again",
            style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.error),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )
        LoadMoreRow(onClick = onClick)
    }
}

@Composable
fun MovieReviewLoadingRow(skeletonColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)) {
    Row(
        Modifier
            .fillMaxWidth()
            .preferredHeight(100.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.background)
            .clickable(onClick = {})
            .padding(16.dp)
    ) {
        Surface(
            modifier = Modifier.preferredSize(60.dp).align(Alignment.CenterVertically),
            shape = CircleShape,
            color = skeletonColor
        ) {}
        Column(modifier = Modifier.fillMaxWidth().padding(start = 16.dp)) {
            Row(
                modifier = Modifier
                    .size(width = 200.dp, height = 12.dp)
                    .background(skeletonColor)
            ) {}
            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(width = 100.dp, height = 10.dp)
                    .background(skeletonColor)
            ) {}
            Row(
                modifier = Modifier
                    .align(AbsoluteAlignment.Right)
                    .padding(top = 8.dp)
                    .size(width = 80.dp, height = 8.dp)
                    .background(skeletonColor)
            ) {}
        }
    }
}

@Preview("initial loading")
@Composable
fun InitialLoadingPreview() {
    InitialLoading()
}


@Preview
@Composable
fun InitialErrorPreview() {
    InitialError(onRetryClicked = {})
}

@Preview("feed content")
@Composable
fun MovieReviewsFeedContentPreview() {
    MovieReviewsFeedContent(
        content = listOf(
            MovieReviewCell.Content(
                id = 1,
                title = "headline 1",
                image = "https://static01.nyt.com/images/2020/08/21/arts/20cutthroat-art/merlin_175717185_b8fc0d22-6a73-4e05-b6dd-741ba3aae81a-mediumThreeByTwo210.jpg",
                publicationDate = "2020-09-12",
                author = "Víctor J."
            ),
            MovieReviewCell.LoadMore,
            MovieReviewCell.LoadingMore,
            MovieReviewCell.LoadMoreError
        ),
        onLoadMoreClicked = {},
        onMovieReviewClicked = {}
    )
}