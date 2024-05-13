import celebService from '../service/celebService.js';

const imagePath = 'https://image.tmdb.org/t/p/w500/';
const noPosterBG = 'resources/no-poster.png';
const imdbIcon = 'resources/imdb.png';
const websiteIcon = 'resources/website.png';
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
  celebContainer.classList.add('movie-container'); 

  const image = document.createElement('img');
  image.src = `${profile_path ? imagePath + profile_path : noPosterBG}`;
  image.alt = name;

  image.addEventListener('click', async () => {
    openModal(await celebService.fetchCelebDetails(id));
  });

  const titleElement = document.createElement('h5');
  titleElement.classList.add('movie-title'); 
  titleElement.textContent = name;

  celebContainer.appendChild(image);
  celebContainer.appendChild(titleElement);
  item.appendChild(celebContainer);

  return item;
}

function openModal(celeb) {
  const knownForHTML = celeb.known_for_department;
  const homepageLink = celeb.homepage ? `<a href="${celeb.homepage}" target="_blank"><img src="${websiteIcon}" alt="Homepage" class="link-icon"></a>` : '';
  const imdbLink = celeb.imdb_id ? `<a href="https://www.imdb.com/name/${celeb.imdb_id}" target="_blank"><img src="${imdbIcon}" alt="IMDb" class="link-icon"></a>` : '';

  const modalHTML = `
  <div id="celebDetailsModal" class="modal">
    <div class="modal-content">
      <span class="close" id="goBackBtn";>&times;</span>
      <div class="modal-body">
        <div class="moviePosterDiv">
          <img id="moviePoster" src="${celeb.profile_path ? imagePath + celeb.profile_path : noPosterBG}" alt="Celeb Poster">
        </div>
        <div id="movieDetails">
          <div class="titleContainer">
            <h2 id="movieTitle"><strong>${celeb.name}</strong> </h2>
          </div>
          <br>
          <div class="genres">
            <b>Known For Department:</b> ${knownForHTML}
          </div> <br>
          <div id="biography-container" class="biography-container">
            <p class="biography"><b>Biography:</b> ${truncateBiography(celeb.biography)}</p>
          </div>
          <p><b>Birthday:</b> ${celeb.birthday}</p><br>
          <p><b>Place of Birth:</b> ${celeb.place_of_birth}</p>
          <div>
            ${homepageLink} ${imdbLink}
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

function truncateBiography(biography) {
  if (biography.length > 500) {
    return `${biography.slice(0, 500)}...`;
  } else {
    return biography;
  }
}

function truncateOverview(overview) {
  if (overview.length > 500) {
    return `${overview.slice(0, 500)}...`;
  } else {
    return overview;
  }
}


function renderNotFound() {

  const container = document.querySelector('#container');
  container.innerHTML = `
    <div style="display: flex; justify-content: center; align-items: center; height: 100%;">
      <img src="${noResultsImage}" alt="No results found" style="width: 40%;">
    </div>`;
}

export default { render, renderNotFound };