import celebService from '../service/celebService.js';
import celebView from '../view/celebView.js';

async function init() {
  const allCelebs = await celebService.fetchData();
  celebView.render(allCelebs);

  const form = document.getElementById('form');
  const search = document.getElementById('search');

  form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const searchInput = search.value.trim();

    if (searchInput) {
      let searchResults = await celebService.fetchData(`https://api.themoviedb.org/3/search/person?query=${searchInput}&api_key=`);
      
      if (searchResults.length === 0) {
        celebView.renderNotFound();

      } else {
        celebView.render(searchResults);
      }

    } else {
      celebView.render(allCelebs);
    }
  });
}

export default { init };
