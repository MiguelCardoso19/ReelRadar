const apiKey = 'a7d624ec76cac151d6355506514023eb';
const apiUrl = 'https://api.themoviedb.org/3/discover/tv?api_key=';

async function fetchData(url = apiUrl) {
  try {
    const response = await fetch(`${url}${apiKey}`);

    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    
    const json = await response.json();
    return json.results;

  } catch (ex) {
    console.error(ex);
    throw ex;
  }
}

export default { fetchData };