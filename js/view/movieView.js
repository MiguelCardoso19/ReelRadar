import movieService from '../service/movieService.js';

const imagePath = 'https://image.tmdb.org/t/p/w500/';
const noPosterBG = 'resources/no-poster.png';
const playButton = 'resources/play-button.png'
const noResultsImage = 'resources/no-results.png';
let modal;

function renderMovies(movies) {
  const container = document.querySelector('#container');
  container.innerHTML = ''; 

  const list = document.createElement('ul');
  list.classList.add('movie-list');

  movies.forEach(movie => {
    const item = createMovieItem(movie);
    list.appendChild(item);
  });

  container.appendChild(list);
}

function createMovieItem(movie) {
  const { id, title, release_date, overview, vote_average, poster_path, genre_ids } = movie;
  const item = document.createElement('li');
  item.classList.add('movie-item');

  const movieContainer = document.createElement('div');
  movieContainer.classList.add('movie-container'); 

  const image = document.createElement('img');
  image.src = `${poster_path ? imagePath + poster_path : noPosterBG}`;
  image.alt = title;

  image.addEventListener('click', async () => {
    openModal(await movieService.fetchMovieDetails(id));
  });

  const titleElement = document.createElement('h5');
  titleElement.classList.add('movie-title');
  titleElement.textContent = title;

  movieContainer.appendChild(image);
  movieContainer.appendChild(titleElement);
  item.appendChild(movieContainer);
  
  return item;
}

function openModal(movie) {
  const genreStrings = movie.genres.map(genre => genre.name);
  const genreHTML = genreStrings.map(genre => `<span>${genre}</span>`).join('');

  const modalHTML = `
  <div id="movieDetailsModal" class="modal">
    <div class="modal-content movie-modal-content">
      <span class="close" id="goBackBtn">&times;</span>
      <div class="modal-body">
        <div class="moviePosterDiv">
          <img id="moviePoster" src="${movie.poster_path ? imagePath + movie.poster_path : noPosterBG}" alt="Movie Poster" >
        </div>
        <div id="movieDetails">
          <div class="titleContainer">
            <h2 id="movieTitle"><strong>${movie.title}</strong></h2>
      
          </div>
          <br>
          <div class="details-container">
            <div class="genres">
              ${genreHTML}
            </div>
            <div class="single-chart custom-single-chart">
              ${movie.vote_average > 0 ? `
                <div class="circle-rating-genre-container">
                  <svg viewBox="0 0 36 36" class="circular-chart">
                    <path class="circle" 
                      stroke="${getCircleColor(movie.vote_average)}"
                      stroke-dasharray="${calculateStrokeDashArray(movie.vote_average)}, 100"
                      d="M18 2.0845
                        a 15.9155 15.9155 0 0 1 0 31.831
                        a 15.9155 15.9155 0 0 1 0 -31.831"
                    />
                    <text x="18" y="20.35" class="percentage" fill="white">${movie.vote_average.toFixed(1)}</text>
                  </svg>
                </div>`
              : ''}
            </div>
          </div> <br>
          <p class="release-date"><b>Release date:</b> ${movie.release_date}</p>
          <p><b>Overview:</b> ${movie.overview}</p>
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
              ${movie.videos.results.length > 0 ? generateVideoLinks(movie.videos.results) : ''}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
`;


  document.body.insertAdjacentHTML('beforeend', modalHTML);
  modal = document.getElementById('movieDetailsModal');

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
      return '';
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
    
    function calculateStrokeDashArray(rating) {
  return (rating / 10) * 100;
}

function getCircleColor(rating) {
  if (rating >= 6.5) {
    return 'lightgreen';
  } else if (rating >= 5) {
    return 'orange';
  } else {
    return 'red';
  }
}

export default { renderMovies, renderNotFound };