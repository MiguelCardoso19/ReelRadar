import movieService from '../service/movieService.js';
import movieView from '../view/movieView.js';

async function init() {
  let currentPage = 1; 
  let allMovies = await movieService.fetchData(null, currentPage);
  movieView.render(allMovies.results);
  
  const form = document.getElementById('form');
  const search = document.getElementById('search');

  form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const searchInput = search.value.trim();
    
    if (searchInput) {
      let searchResults = await movieService.fetchData(searchInput);

      if (searchResults.results.length === 0) {
        movieView.renderNotFound();

      } else {
        movieView.render(searchResults.results);
      }
      
    } else {
      movieView.render(allMovies.results);
    }
  });

  async function fetchAndRenderNextPage() {
    currentPage++;
    const nextPageMovies = await movieService.fetchData(null, currentPage);
    movieView.render(nextPageMovies.results);
    document.getElementById('prevBtn').style.display = 'inline-block';
  }

  async function fetchAndRenderPreviousPage() {
    if (currentPage > 1) {
      currentPage--;
      const previousPageMovies = await movieService.fetchData(null, currentPage);
      movieView.render(previousPageMovies.results);
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