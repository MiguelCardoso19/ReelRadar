import tvShowService from '../service/tvShowService.js';
import tvShowView from '../view/tvShowView.js';

let timeoutId;

async function init() {
  const tvShows = await tvShowService.fetchData();
  tvShowView.render(tvShows);
  
  const searchInput = document.getElementById('searchInput');
  searchInput.addEventListener('input', handleSearchInput);
}

async function handleSearchInput(event) {
  clearTimeout(timeoutId);

  const searchQuery = event.target.value.trim();

  if (!searchQuery) {
    const allTvShows  = await tvShowService.fetchData();
    tvShowView.render(allTvShows );
    return;
  }

  timeoutId = setTimeout(async () => {
    const tvShows  = await tvShowService.fetchData(searchQuery);

    tvShowView.render(tvShows);
  }, 300); 
}

export default { init };