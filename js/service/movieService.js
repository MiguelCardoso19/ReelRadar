const apiKey = 'a7d624ec76cac151d6355506514023eb';
const apiUrl = 'https://api.themoviedb.org/3/';

async function fetchData(query = '', page = 1) {
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

    const json = await response.json();
    return json;

  } catch(ex) {
    console.error(ex);
    throw ex;
  }
}

export default { fetchData };