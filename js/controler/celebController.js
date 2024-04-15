import celebService from '../service/celebService.js';
import celebView from '../view/celebView.js';

let timeoutId;

async function init() {
  const celebs = await celebService.fetchData();
  celebView.render(celebs);
  
  const searchInput = document.getElementById('searchInput');
  searchInput.addEventListener('input', handleSearchInput);
}

async function handleSearchInput(event) {
  clearTimeout(timeoutId);

  const searchQuery = event.target.value.trim();

  if (!searchQuery) {
    const allcelebs  = await celebService.fetchData();
    celebView.render(allcelebs );
    return;
  }

  timeoutId = setTimeout(async () => {
    const celebs  = await celebService.fetchData(searchQuery);

    celebView.render(celebs);
  }, 300); 
}

export default { init };