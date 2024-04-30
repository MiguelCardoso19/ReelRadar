import tvShowService from '../service/tvShowService.js';

const imagePath = 'https://image.tmdb.org/t/p/w500/';
const noPosterBG = 'resources/no-poster.png';
const playButton = 'resources/play-button.png';
const noResultsImage = 'resources/no-results.png';
let modal;

function renderTVShows(tvShows) {
  const container = document.querySelector('#container');
  container.innerHTML = '';

  const list = document.createElement('ul');
  list.classList.add('movie-list');

  tvShows.forEach(tvShow => {
    const item = createTVShowItem(tvShow);
    list.appendChild(item);
  });

  container.appendChild(list);
}

function createTVShowItem(tvShow) {
  const { id, name, first_air_date, overview, vote_average, poster_path, genre_ids } = tvShow;
  const item = document.createElement('li');
  item.classList.add('movie-item');

  const tvShowContainer = document.createElement('div');
  tvShowContainer.classList.add('movie-container');

  const image = document.createElement('img');
  image.src = `${poster_path ? imagePath + poster_path : noPosterBG}`;
  image.alt = name;

  image.addEventListener('click', async () => {
    openModal(await tvShowService.fetchTVShowDetails(id));
  });

  const titleElement = document.createElement('h5');
  titleElement.classList.add('movie-title');
  titleElement.textContent = name;

  tvShowContainer.appendChild(image);
  tvShowContainer.appendChild(titleElement);
  item.appendChild(tvShowContainer);

  return item;
}

function openModal(tvShow) {
  const genreStrings = tvShow.genres.map(genre => genre.name);
  const genreHTML = genreStrings.map(genre => `<span>${genre}</span>`).join('');

  console.log(tvShow.id);

  const modalHTML = `
  <div id="tvShowDetailsModal" class="modal">
    <div class="modal-content">
      <span class="close">&times;</span>
      <div class="modal-body">
        <div class="moviePosterDiv">
          <img id="moviePoster" src="${tvShow.poster_path ? imagePath + tvShow.poster_path : noPosterBG}" alt="TV Show Poster">
        </div>
        <div id="movieDetails">
          <div class="titleContainer">
            <h2 id="movieTitle"><strong>${tvShow.name}</strong> </h2>
            <div class="close-container-custom" id="goBackBtn">
              <div class="leftright-custom"></div>
              <div class="rightleft-custom"></div>
              <label class="label-custom">close</label>
            </div>
          </div>
          <br>
          <div class="genres">
            ${genreHTML}
          </div>
          <p class="release-date"><b>First air date:</b> ${tvShow.first_air_date}</p><br>
          <p><b>Overview:</b> ${tvShow.overview}</p><br>
          <p><b>Rating:</b> <span ${getColor(tvShow.vote_average)}>${tvShow.vote_average.toFixed(1)}</span></p>
          <div class="buttonContainer">
            <div class="favIconContainer">
              <input id="cbx" type="checkbox" />
              <label for="cbx">
                <span class="heart">
                  <span class="ripple"></span>
                  <svg class="unchecked" viewBox="0 0 24 24">
                    <path d="M16.5 3c-1.74 0-3.41.81-4.5 2.09C10.91 3.81 9.24 3 7.5 3 4.42 3 2 5.42 2 8.5c0 3.78 3.4 6.86 8.55 11.54L12 21.35l1.45-1.32C18.6 15.36 22 12.28 22 8.5 22 5.42 19.58 3 16.5 3zm-4.4 15.55l-.1.1-.1-.1C7.14 14.24 4 11.39 4 8.5 4 6.5 5.5 5 7.5 5c1.54 0 3.04.99 3.57 2.36h1.87C13.46 5.99 14.96 5 16.5 5c2 0 3.5 1.5 3.5 3.5 0 2.89-3.14 5.74-7.9 10.05z"></path>
                  </svg>
                  <svg class="checked" viewBox="0 0 24 24">
                    <path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z"></path>
                  </svg>
                </span>
              </label>
            </div>
            <div class="watchTraillerBtn">
              ${tvShow.videos.results.length > 0 ? generateVideoLinks(tvShow.videos.results) : ''}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  `;

  document.body.insertAdjacentHTML('beforeend', modalHTML);
  modal = document.getElementById('tvShowDetailsModal');

  const cbx = document.getElementById('cbx');
  cbx.addEventListener('change', toggleCheckbox);

  const goBackBtn = document.getElementById('goBackBtn');
  goBackBtn.onclick = () => closeModal();

  window.onclick = function(event) {
    if (event.target === modal) {
      closeModal();
    }
  };
}

function toggleCheckbox() {
  const cbx = document.getElementById('cbx');
  const favIcon = document.querySelector('.heart');
  if (cbx.checked) {
    favIcon.classList.add('checked');
    favIcon.classList.remove('unchecked');
  } else {
    favIcon.classList.add('unchecked');
    favIcon.classList.remove('checked');
  }
}

  function generateVideoLinks(videos) {
    const officialTrailer = videos.find(video => {
      const lowerCaseName = video.name.toLowerCase();
      return lowerCaseName.includes('official') && lowerCaseName.includes('trailer');
    });
  
    if (officialTrailer) {
      return `
        <p>
          <a href="https://www.youtube.com/watch?v=${officialTrailer.key}" target="_blank">
            <button class="trailerButton">
              <div class="trailerText">Watch Trailer</div>
              <img src=${playButton} alt="Play Button" class="playButtonIcon">
            </button>
          </a>
        </p>`;
    } else {
      const trailer = videos.find(video => {
        const lowerCaseName = video.name.toLowerCase();
        return lowerCaseName.includes('trailer');
      });
  
      if (trailer) {
        return `
          <p>
            <a href="https://www.youtube.com/watch?v=${trailer.key}" target="_blank">
              <button class="trailerButton">
                <div class="trailerText">Watch Trailer</div>
                <img src=${playButton} alt="Play Button" class="playButtonIcon">
              </button>
            </a>
          </p>`;
      } else {
        const teaser = videos.find(video => video.type === 'Teaser');
  
        if (teaser) {
          return `
            <p>
              <a href="https://www.youtube.com/watch?v=${teaser.key}" target="_blank">
                <button class="trailerButton">
                  <div class="trailerText">Watch Trailer</div>
                  <img src=${playButton} alt="Play Button" class="playButtonIcon">
                </button>
              </a>
            </p>`;
        } else {
          return '';
        }
      }
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

export default { renderTVShows, renderNotFound };