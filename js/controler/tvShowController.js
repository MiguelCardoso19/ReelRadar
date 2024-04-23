import tvShowService from '../service/tvShowService.js';
import tvShowView from '../view/tvShowView.js';

async function init() {
  const allTVShows = await tvShowService.fetchData();
  tvShowView.render(allTVShows);

  const form = document.getElementById('form');
  const search = document.getElementById('search');

  form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const searchInput = search.value.trim();

    if (searchInput) {
      let searchResults = await  tvShowService.fetchData(`https://api.themoviedb.org/3/search/tv?query=${searchInput}&api_key=`);
      
      if (searchResults.length === 0) {
        tvShowView.renderNotFound();

      } else {
        tvShowView.render(searchResults);
      } 
      
    } else {
      tvShowView.render(allTVShows);
    }
  });
}

export default { init };