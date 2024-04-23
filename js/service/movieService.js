async function fetchData() {
  let response = null;
  const apiKey = 'a7d624ec76cac151d6355506514023eb';
  const searchInput = document.getElementById('searchInput').value;
  let apiUrl = 'https://api.themoviedb.org/3/discover/movie';

  if (searchInput) {
    apiUrl = `https://api.themoviedb.org/3/search/movie`;
  }

  try {
    response = await fetch(`${apiUrl}?api_key=${apiKey}&query=${searchInput}`);

    if (!response.ok) {
      throw new Error('Network response was not ok');
    }

    const json = await response.json();
    console.log(json.results);
    return json.results;
    
  } catch(ex) {
    console.error(ex);
    throw ex;
  }
}

export default { fetchData };