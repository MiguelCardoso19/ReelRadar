import movieService from '../service/movieService.js';
import movieView from '../view/movieView.js';

async function init() {
  const allMovies = await movieService.fetchData();
  movieView.render(allMovies);
  
  const form = document.getElementById('form');
  const search = document.getElementById('search');

  form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const searchInput = search.value.trim();

    if (searchInput) {
      let searchResults = await movieService.fetchData(`https://api.themoviedb.org/3/search/movie?query=${searchInput}&api_key=`);
     
      if (searchResults.length === 0) {
        movieView.renderNotFound();

      } else {
        movieView.render(searchResults);
      }

    } else {
      movieView.render(allMovies);
    }
  });
}

export default { init };
