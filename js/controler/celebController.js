import celebService from '../service/celebService.js';
import celebView from '../view/celebView.js';

async function init() {
  try {
    let currentPage = 1; 
    let allCelebs = await celebService.fetchData(null, currentPage); 
    celebView.render(allCelebs.results); 

    const form = document.getElementById('form');
    const search = document.getElementById('search');

    form.addEventListener('submit', async (e) => {
      e.preventDefault();

      const searchInput = search.value.trim();

      if (searchInput) {
        let searchResults = await celebService.fetchData(searchInput);

        if (searchResults.results.length === 0) {
          celebView.renderNotFound();
        } else {
          celebView.render(searchResults.results); 
        }
      } else {
        currentPage = 1; 
        allCelebs = await celebService.fetchData(null, currentPage);
        celebView.render(allCelebs.results); 
      }
    });

    async function fetchAndRenderNextPage() {
      currentPage++;
      const nextPageCelebs = await celebService.fetchData(null, currentPage);
      celebView.render(nextPageCelebs.results); 
    }

    async function fetchAndRenderPreviousPage() {
      if (currentPage > 1) {
        currentPage--;
        const previousPageCelebs = await celebService.fetchData(null, currentPage);
        celebView.render(previousPageCelebs.results); 
      }
    }

    const nextBtn = document.getElementById('nextBtn');
    nextBtn.addEventListener('click', fetchAndRenderNextPage);

    const prevBtn = document.getElementById('prevBtn');
    prevBtn.addEventListener('click', fetchAndRenderPreviousPage);

    prevBtn.style.display = 'none';
  } catch (error) {
    console.error(error);
  }
}

export default { init };
