# Movie Reviews Android app

The objective is to build an Android app with the next requirements:

* Download a list of movie reviews from https://api.nytimes.com/svc/movies/v2/reviews/picks.json API.
* Show a list of movie reviews with the next information sorted by date (from more recent to less recent by publication date):
  * Movie title.
  * Movie image.
  * Movie review publication date.
  * Author of the review.
* Add the option to retrieve more movie reviews and add them to your current movie reviews list.
* Add a button to each movie review cell to delete it. If the button is pressed, the movie review won't be shown anymore in the list.
* Add a button to each movie review cell to mark it as favourite.
* If a movie review cell is pressed, a detailed view of the movie review should be shown with the next information:
  * Movie review headline.
  * Movie image.
  * Movie review publication date.
  * Author of the review.
  * Movie's short summary.
  * Movie Motion Picture Association of America (MPAA) rating.
  * Link to NYTimes review article.
  * Marked as favourite or not.
  
## API details
Additional information about the API we'll use to get app's data can be found at https://developer.nytimes.com/docs/movie-reviews-api/1/overview.

## Resources used while building this app
### Jetpack compose
* Jetpack Compose basics codelab https://developer.android.com/codelabs/jetpack-compose-basics
* Using state in Jetpack Compose codelab https://developer.android.com/codelabs/jetpack-compose-state
* Layouts in Jetpack Compose codelab https://developer.android.com/codelabs/jetpack-compose-layouts
### Kotlin coroutines
* Asynchronous flow https://kotlinlang.org/docs/reference/coroutines/flow.html#asynchronous-flow
* Channels https://kotlinlang.org/docs/reference/coroutines/channels.html
* Share mutable state and concurrency https://kotlinlang.org/docs/reference/coroutines/shared-mutable-state-and-concurrency.html
### Kotlin coroutines core
* StateFlow https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/index.html
