import tvShowService from '../service/tvShowService.js';
import tvShowView from '../view/tvShowView.js';

async function init() {
  let currentPage = 1;
  let allTVShows = await tvShowService.fetchTVShows(null, currentPage);
  tvShowView.renderTVShows(allTVShows);

  const form = document.getElementById('form');
  const search = document.getElementById('search');

  form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const searchInput = search.value.trim();

    if (searchInput) {
      let searchResults = await tvShowService.fetchTVShows(searchInput);

      if (searchResults.length === 0) {
        tvShowView.renderNotFound();
      } else {
        tvShowView.renderTVShows(searchResults);
      }
    } else {
      tvShowView.renderTVShows(allTVShows);
    }
  });

  async function fetchAndRenderNextPage() {
    currentPage++;
    const nextPageTVShows = await tvShowService.fetchTVShows(null, currentPage);
    tvShowView.renderTVShows(nextPageTVShows);
    document.getElementById('prevBtn').style.display = 'inline-block';
  }

  async function fetchAndRenderPreviousPage() {
    if (currentPage > 1) {
      currentPage--;
      const previousPageTVShows = await tvShowService.fetchTVShows(null, currentPage);
      tvShowView.renderTVShows(previousPageTVShows);
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