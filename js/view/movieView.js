import genreService from '../service/genreService.js'
const imagePath = 'https://image.tmdb.org/t/p/w500/';
const noPosterBG = 'resources/no-poster.png';
const noResultsImage = 'resources/no-results.png';
const FavIconFullStar = 'resources/full-star.png';
const FavIconEmptyStar = 'resources/empty-star.png';
let modal;



function render(movies) {
  const container = document.querySelector('#container');
  container.innerHTML = ''; 

  const list = document.createElement('ul');
  list.classList.add('movie-list');

  movies.forEach(({ title, release_date, overview, vote_average, poster_path, genre_ids }) => {

    const item = document.createElement('li');
    item.classList.add('movie-item');

    const movieContainer = document.createElement('div');
    movieContainer.classList.add('movie-container'); 

    const image = document.createElement('img');
    image.src = `${poster_path ? imagePath + poster_path : noPosterBG}`;
    image.alt = title;

    image.addEventListener('click', () => {
      openModal({ title, release_date, overview, vote_average, poster_path, genre_ids });
    });

    const titleElement = document.createElement('h5');
    titleElement.classList.add('movie-title');
    titleElement.textContent = title;

    movieContainer.appendChild(image);
    movieContainer.appendChild(titleElement);
    item.appendChild(movieContainer);
    list.appendChild(item);
  });

  container.appendChild(list);
}


async function openModal(movie) {
  if (modal) {
    modal.remove();
  }

  const genreStrings = movie.genre_ids.map(id => genreService.getGenreName(id));
  const genreHTML = genreStrings.map(genre => `<span>${genre}</span>`).join('');

  const modalHTML = `
    <div id="movieDetailsModal" class="modal">
      <div class="modal-content">
        <span class="close">&times;</span>
        <div class="modal-body">
          <div class="moviePosterDiv">
            <img id="moviePoster" src="${movie.poster_path ? imagePath + movie.poster_path : noPosterBG}" alt="Movie Poster">
          </div>
          <div id="movieDetails">
            <div class="titleContainer">
              <h2 id="movieTitle"><strong>${movie.title}</strong></h2>
              <div class="favIconContainer">
                <img id="favIcon" src="${FavIconEmptyStar}" alt="Favorite Icon">
              </div>
            </div>
            <br>
            <div class="genres">
            ${genreHTML}
            </div>
            <p class="release-date"><b>Release date:</b> ${movie.release_date}</p><br>
            <p><b>Overview:</b> ${movie.overview}</p><br>
            <p><b>Rating:</b> <span ${getColor(movie.vote_average)}>${movie.vote_average.toFixed(1)}</span></p>
            <div class="buttonDiv">
            <button id="goBackBtn"">Close</button>
            </div>
          </div>

        </div>
      </div>
    </div>
  `;


  document.body.insertAdjacentHTML('beforeend', modalHTML);
  modal = document.getElementById('movieDetailsModal');

  const favIcon = document.getElementById('favIcon'); 
  favIcon.addEventListener('click', toggleStarIcon); 

  const goBackBtn = document.getElementById('goBackBtn');
  goBackBtn.onclick = () => closeModal();

  window.onclick = function(event) {
    if (event.target === modal) {
      closeModal();
    }
  };
}



function toggleStarIcon() {
  const favIcon = document.getElementById('favIcon');
  if (favIcon.src.includes(FavIconEmptyStar)) {
    favIcon.src = FavIconFullStar;
  } else {
    favIcon.src = FavIconEmptyStar;
  }
}

function closeModal() {
  modal.style.display = 'none';
  modal.remove();
  modal = null;
}

function getColor(rating) {
  if (rating >= 6.5) {
    return 'style="color: lightgreen; font-size: 16px;"';
  } else if (rating >= 5) {
    return 'style="color: orange; font-size: 16px;"';
  } else {
    return 'style="color: red; font-size: 16px;"';
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