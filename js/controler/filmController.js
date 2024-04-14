import filmService from '../service/filmService.js';
import filmView from '../view/filmView.js';

let timeoutId;

async function init() {
  const films = await filmService.fetchData();
  filmView.render(films);
  
  const searchInput = document.getElementById('searchInput');
  searchInput.addEventListener('input', handleSearchInput);
}

async function handleSearchInput(event) {
  clearTimeout(timeoutId);

  const searchQuery = event.target.value.trim();

  if (!searchQuery) {
    const allFilms = await filmService.fetchData();
    filmView.render(allFilms);
    return;
  }

  timeoutId = setTimeout(async () => {
    const films = await filmService.fetchData(searchQuery);

    filmView.render(films);
  }, 300); 
}

export default { init };
