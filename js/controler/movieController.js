import movieService from '../service/movieService.js';
import movieView from '../view/movieView.js';

const loadMoreBtn = document.getElementById('nextBtn');
const loadLessBtn = document.getElementById('prevBtn');

let currentPage = 1;
let allMovies = [];

async function init() {
    loadLessBtn.style.display = 'none';

    await fetchAndRenderNextPage();

    loadMoreBtn.addEventListener('click', fetchAndRenderNextPage);
    loadLessBtn.addEventListener('click', fetchAndRenderPreviousPage);

    const form = document.getElementById('form');
    const search = document.getElementById('search');

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const searchInput = search.value.trim();

        if (searchInput) {

            let searchResults = await movieService.fetchMovies(searchInput);

            if (searchResults.length === 0) {

                loadMoreBtn.style.visibility = 'hidden';
                loadLessBtn.style.visibility = 'hidden';
                movieView.renderNotFound();
            } else {

                loadMoreBtn.style.visibility = 'visible';
                loadLessBtn.style.visibility = 'visible';

                allMovies = searchResults;
                movieView.renderMovies(allMovies);
            }
        } else {

          loadMoreBtn.style.visibility = 'visible';
            loadLessBtn.style.visibility = 'visible';

            allMovies = await movieService.fetchMovies(null, 1);
            movieView.renderMovies(allMovies);
        }
    });
}

async function fetchAndRenderNextPage() {
    const nextPageMovies = await movieService.fetchMovies(null, currentPage);
    allMovies.push(...nextPageMovies);
    movieView.renderMovies(allMovies);
    currentPage++;

    if (currentPage > 2) {
        loadLessBtn.style.display = 'inline-block';
    }
}

async function fetchAndRenderPreviousPage() {
    if (currentPage > 1) {

        allMovies.splice(-20); 

        movieView.renderMovies(allMovies);
        currentPage--;

        if (currentPage === 2) {
            loadLessBtn.style.display = 'none';
        }
    }
}

export default { init };