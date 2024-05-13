const apiKey = 'a7d624ec76cac151d6355506514023eb';
const apiUrl = 'https://api.themoviedb.org/3/';

async function fetchTVShows(query = '', page = 1) {
  try {
    let url = apiUrl + 'discover/tv?api_key=' + apiKey;

    if (query) {
      url = apiUrl + 'search/tv?api_key=' + apiKey + '&query=' + encodeURIComponent(query);
    }

    url += '&page=' + page;

    const response = await fetch(url);

    if (!response.ok) {
      throw new Error('Network response was not ok');
    }

    const tvShowsData = await response.json();
    return tvShowsData.results;
  } catch(ex) {
    console.error(ex);
    throw ex;
  }
}

async function fetchTVShowDetails(tvShowId) {
  try {
    const response = await fetch(`${apiUrl}tv/${tvShowId}?api_key=${apiKey}&append_to_response=videos`);

    if (!response.ok) {
      throw new Error('Network response was not ok');
    }

    const tvShowDetails = await response.json();
    return tvShowDetails;
  } catch(ex) {
    console.error('Error fetching TV show details:', ex);
    throw ex;
  }
}

export default { fetchTVShows, fetchTVShowDetails };