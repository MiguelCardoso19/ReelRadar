const apiKey = 'a7d624ec76cac151d6355506514023eb';
const apiUrl = 'https://api.themoviedb.org/3/';

async function fetchData(query = '', page = 1) {
  try {
    let url = apiUrl + 'discover/person?api_key=' + apiKey;

    if (query) {
      url = apiUrl + 'search/person?api_key=' + apiKey + '&query=' + encodeURIComponent(query);
    }

    url += '&page=' + page;

    const response = await fetch(url);

    if (!response.ok) {
      throw new Error('Network response was not ok');
    }

    const celebritiesData = await response.json();
    console.error(celebritiesData);
    return celebritiesData; 

  } catch(ex) {
    console.error(ex);
    throw ex;
  }
}

async function fetchCelebDetails(celebrityId) {
  try {
    const response = await fetch(`${apiUrl}person/${celebrityId}?api_key=${apiKey}`);

    if (!response.ok) {
      throw new Error('Network response was not ok');
    }

    const celebrityDetails = await response.json();
    return celebrityDetails;
  } catch(ex) {
    console.error('Error fetching celebrity details:', ex);
    throw ex;
  }
}

export default { fetchData, fetchCelebDetails };