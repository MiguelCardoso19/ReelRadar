import movieService from '../service/movieService.js';
import movieView from '../view/movieView.js';

let timeoutId;

async function init() {
  const movies = await movieService.fetchData();
  movieView.render(movies);
  
  const searchInput = document.getElementById('searchInput');
  searchInput.addEventListener('input', handleSearchInput);
}

async function handleSearchInput(event) {
  clearTimeout(timeoutId);

  const searchQuery = event.target.value.trim();

  if (!searchQuery) {
    const allmovies = await movieService.fetchData();
    movieView.render(allMovies);
    return;
  }

  timeoutId = setTimeout(async () => {
    const movies = await movieService.fetchData(searchQuery);

    movieView.render(movies);
  }, 300); 
}

export default { init };
