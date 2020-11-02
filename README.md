# Movie Reviews Android app

The objective is to build an Android app with the next requirements:

* Download a list of movie reviews from https://api.nytimes.com/svc/movies/v2/reviews/picks.json API.
* Show a list of movie reviews with the next information sorted by date (from more recent to less recent by publication date):
  * Movie review headline.
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