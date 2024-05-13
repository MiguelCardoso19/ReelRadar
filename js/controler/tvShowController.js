import tvShowService from '../service/tvShowService.js';
import tvShowView from '../view/tvShowView.js';

const loadMoreBtn = document.getElementById('nextBtn');
const loadLessBtn = document.getElementById('prevBtn');

let currentPage = 1;
let allTVShows = [];

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

          let searchResults = await tvShowService.fetchTVShows(searchInput);

            if (searchResults.length === 0) {

              loadMoreBtn.style.visibility = 'hidden';
                loadLessBtn.style.visibility = 'hidden';
                tvShowView.renderNotFound();
            } else {

              loadMoreBtn.style.visibility = 'visible';
                loadLessBtn.style.visibility = 'visible';

                allTVShows = searchResults;
                tvShowView.renderTVShows(allTVShows);
            }
        } else {

          loadMoreBtn.style.visibility = 'visible';
            loadLessBtn.style.visibility = 'visible';


            allTVShows = await tvShowService.fetchTVShows(null, 1);
            tvShowView.renderTVShows(allTVShows);
        }
    });
}

async function fetchAndRenderNextPage() {
    const nextPageTVShows = await tvShowService.fetchTVShows(null, currentPage);
    allTVShows.push(...nextPageTVShows);
    tvShowView.renderTVShows(allTVShows);
    currentPage++;

    if (currentPage > 2) {
        loadLessBtn.style.display = 'inline-block';
    }
}

async function fetchAndRenderPreviousPage() {
    if (currentPage > 1) {

      allTVShows.splice(-20); 


      tvShowView.renderTVShows(allTVShows);
        currentPage--;

        if (currentPage === 2) {
            loadLessBtn.style.display = 'none';
        }
    }
}

export default { init };