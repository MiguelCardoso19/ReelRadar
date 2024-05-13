import celebService from '../service/celebService.js';
import celebView from '../view/celebView.js';

const nextBtn = document.getElementById('nextBtn');
const prevBtn = document.getElementById('prevBtn');

async function init() {
    let allCelebs = []; 
    let currentPage = 1;


    async function fetchAndRenderNextPage() {
        const nextPageCelebs = await celebService.fetchData(null, currentPage);
        allCelebs.push(...nextPageCelebs.results);
        celebView.render(allCelebs);
        currentPage++;
        if (currentPage > 1) {
            document.getElementById('prevBtn').style.display = 'inline-block';
        }
    }


    async function fetchAndRenderPreviousPage() {
        if (currentPage > 1) {
            currentPage--;
            const previousPageCelebs = await celebService.fetchData(null, currentPage);
            allCelebs.splice(-previousPageCelebs.results.length);
            celebView.render(allCelebs);
        }

        if (currentPage === 2) {
            document.getElementById('prevBtn').style.display = 'none';
        }
    }


    nextBtn.addEventListener('click', fetchAndRenderNextPage);
    prevBtn.addEventListener('click', fetchAndRenderPreviousPage);

    const form = document.getElementById('form');
    const search = document.getElementById('search');

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const searchInput = search.value.trim();

        if (searchInput) {
            let searchResults = await celebService.fetchData(searchInput);

            if (searchResults.results.length === 0) {
                nextBtn.style.visibility = 'hidden';
                prevBtn.style.visibility = 'hidden';
                celebView.renderNotFound();
            } else {
                nextBtn.style.visibility = 'visible';
                prevBtn.style.visibility = 'visible';
                allCelebs = searchResults.results; 
                celebView.render(allCelebs);
            }
        } else {
            nextBtn.style.visibility = 'visible';
            prevBtn.style.visibility = 'visible';
            allCelebs = await celebService.fetchData(null, 1); 
            celebView.render(allCelebs.results);
        }
    });


    await fetchAndRenderNextPage();


    if (currentPage === 2) {
        prevBtn.style.display = 'none';
    }
}

export default { init };