import movieService from '../service/movieService.js';
import movieView from '../view/movieView.js';

async function init() {
  let currentPage = 1; 
  let allMovies = await movieService.fetchMovies(null, currentPage);
  movieView.renderMovies(allMovies);
  
  const form = document.getElementById('form');
  const search = document.getElementById('search');

  form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const searchInput = search.value.trim();
    
    if (searchInput) {
      let searchResults = await movieService.fetchMovies(searchInput);

      if (searchResults.length === 0) {
        movieView.renderNotFound();

      } else {
        movieView.renderMovies(searchResults);
      }
      
    } else {
      movieView.renderMovies(allMovies);
    }
  });

  async function fetchAndRenderNextPage() {
    currentPage++;
    const nextPageMovies = await movieService.fetchMovies(null, currentPage);
    movieView.renderMovies(nextPageMovies);
    document.getElementById('prevBtn').style.display = 'inline-block';
  }

  async function fetchAndRenderPreviousPage() {
    if (currentPage > 1) {
      currentPage--;
      const previousPageMovies = await movieService.fetchMovies(null, currentPage);
      movieView.renderMovies(previousPageMovies);
    }

    if (currentPage === 1) {
      document.getElementById('prevBtn').style.display = 'none';
    }
  }

  const nextBtn = document.getElementById('nextBtn');
  nextBtn.addEventListener('click', fetchAndRenderNextPage);

  const prevBtn = document.getElementById('prevBtn');
  prevBtn.addEventListener('click', fetchAndRenderPreviousPage);

  prevBtn.style.display = 'none';
}

export default { init };