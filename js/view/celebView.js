import celebService from '../service/celebService.js';

const imagePath = 'https://image.tmdb.org/t/p/w500/';
const noPosterBG = 'resources/no-poster.png';

const noResultsImage = 'resources/no-results.png';
let modal;

function render(celebs) {
  const container = document.querySelector('#container');
  container.innerHTML = '';

  const list = document.createElement('ul');
  list.classList.add('movie-list'); 

  celebs.forEach(celeb => {
    const item = createCelebItem(celeb);
    list.appendChild(item);
  });

  container.appendChild(list);
}

function createCelebItem(celeb) {
  const { id, name, profile_path } = celeb;
  const item = document.createElement('li');
  item.classList.add('movie-item'); 

  const celebContainer = document.createElement('div');
  celebContainer.classList.add('movie-container'); // Changed to 'movie-container'

  const image = document.createElement('img');
  image.src = `${profile_path ? imagePath + profile_path : noPosterBG}`;
  image.alt = name;

  image.addEventListener('click', async () => {
    openModal(await celebService.fetchCelebDetails(id));
  });

  const titleElement = document.createElement('h5');
  titleElement.classList.add('movie-title'); // Changed to 'movie-title'
  titleElement.textContent = name;

  celebContainer.appendChild(image);
  celebContainer.appendChild(titleElement);
  item.appendChild(celebContainer);

  return item;
}

function openModal(celeb) {
  const knownForHTML = celeb.known_for_department;
  const homepageLink = celeb.homepage ? `<a href="${celeb.homepage}" target="_blank">Homepage</a>` : '';
  const imdbLink = celeb.imdb_id ? `<a href="https://www.imdb.com/name/${celeb.imdb_id}" target="_blank">IMDb</a>` : '';
  
  const modalHTML = `
  <div id="celebDetailsModal" class="modal">
    <div class="modal-content">
      <span class="close">&times;</span>
      <div class="modal-body">
        <div class="moviePosterDiv">
          <img id="moviePoster" src="${celeb.profile_path ? imagePath + celeb.profile_path : noPosterBG}" alt="Celeb Poster">
        </div>
        <div id="movieDetails">
          <div class="titleContainer">
            <h2 id="movieTitle"><strong>${celeb.name}</strong> </h2>
            <div class="close-container-custom" id="goBackBtn">
              <div class="leftright-custom"></div>
              <div class="rightleft-custom"></div>
              <label class="label-custom">close</label>
            </div>
          </div>
          <br>
          <div class="genres">
            <b>Known For Department:</b> ${knownForHTML}
          </div>
          <p class="biography"><b>Biography:</b> ${celeb.biography}</p><br>
          <p><b>Birthday:</b> ${celeb.birthday}</p><br>
          <p><b>Place of Birth:</b> ${celeb.place_of_birth}</p><br>
          <div>
            <b>Links:</b> ${homepageLink} ${imdbLink}
          </div>
        </div>
      </div>
    </div>
  </div>
  `;

  document.body.insertAdjacentHTML('beforeend', modalHTML);
  modal = document.getElementById('celebDetailsModal');

  const goBackBtn = document.getElementById('goBackBtn');
  goBackBtn.onclick = () => closeModal();

  window.onclick = function(event) {
    if (event.target === modal) {
      closeModal();
    }
  };
}


function closeModal() {
  modal.style.display = 'none';
  modal.remove();
  modal = null;
}

function renderNotFound() {
  const container = document.querySelector('#container');
  container.innerHTML = `
    <div style="display: flex; justify-content: center; align-items: center; height: 100%;">
      <img src="${noResultsImage}" alt="No results found" style="width: 40%;">
    </div>`;
}

export default { render, renderNotFound };