const apiKey = 'a7d624ec76cac151d6355506514023eb';
const apiUrl = 'https://api.themoviedb.org/3/';


async function fetchMovies(query = '', page = 1) {
  try {
    let url = apiUrl + 'discover/movie?api_key=' + apiKey;

    if (query) {
      url = apiUrl + 'search/movie?api_key=' + apiKey + '&query=' + encodeURIComponent(query);
    }

    url += '&page=' + page;

    const response = await fetch(url);

    if (!response.ok) {
      throw new Error('Network response was not ok');
    }

    const moviesData = await response.json();
    return moviesData.results;

  } catch(ex) {
    console.error(ex);
    throw ex;
  }
}

async function fetchMovieDetails(movieId) {
  try {
    const response = await fetch(`${apiUrl}movie/${movieId}?api_key=${apiKey}&append_to_response=videos`);

    if (!response.ok) {
      throw new Error('Network response was not ok');
    }

    const movieDetails = await response.json();
    return movieDetails;
  } catch(ex) {
    console.error('Error fetching movie details:', ex);
    throw ex;
  }
}

export default { fetchMovies, fetchMovieDetails };