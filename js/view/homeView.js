import movieService from '../service/movieService.js';

const container = document.getElementById('container');
const imagePath = 'https://image.tmdb.org/t/p/w500/';
const noPosterBG = 'resources/no-poster.png';
const playButton = 'resources/play-button.png'
let modal;

function renderUpcomingMovies(movies) {
  container.innerHTML = '';

  const carouselContainer = document.createElement('div');
  carouselContainer.classList.add('upcoming-movie-slide');

  const owlCarousel = document.createElement('div');
  owlCarousel.classList.add('owl-carousel');
  owlCarousel.id = 'upcoming2-movie-slide';

  const upcomingMoviesHeading = document.createElement('h5');
  upcomingMoviesHeading.textContent = 'UPCOMING MOVIES';
  upcomingMoviesHeading.classList.add('upcoming-movies-heading');
  carouselContainer.appendChild(upcomingMoviesHeading);

  movies.forEach(movie => {
    const slide = createUpcomingMovieSlide(movie);
    owlCarousel.appendChild(slide);
  });

  carouselContainer.appendChild(owlCarousel);
  container.appendChild(carouselContainer);

  $(document).ready(function(){
    $('.owl-carousel').owlCarousel({
      items:10,
      loop:true,
      animateOut: 'slideOutDown',
      animateIn: 'flipInX',
      stagePadding:30,
      smartSpeed:1000,
      margin:10,
      autoplay:true,
      autoplayTimeout:2000,
      autoplayHoverPause:true
    });
  });
}

function createUpcomingMovieSlide(movie) {
  const { id, title, poster_path } = movie;

  const slide = document.createElement('div');
  slide.classList.add('movie-item');

  const movieItemContent = document.createElement('div');
  movieItemContent.classList.add('movie-item-content');

  const image = document.createElement('img');
  image.src = `${poster_path ? imagePath + poster_path : noPosterBG}`;
  image.alt = title;

  image.addEventListener('click', async () => {
    openModal(await movieService.fetchMovieDetails(id));
  });

  movieItemContent.appendChild(image);
  slide.appendChild(movieItemContent);

  return slide;
}

function openModal(movie) {
  const genreStrings = movie.genres.map(genre => genre.name);
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
            <h2 id="movieTitle"><strong>${movie.title}</strong> </h2>
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
          <p class="release-date"><b>Release date:</b> ${movie.release_date}</p><br>
          <p><b>Overview:</b> ${movie.overview}</p><br>
          <p><b>Rating:</b> <span ${getColor(movie.vote_average)}>${movie.vote_average.toFixed(1)}</span></p>
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

export default { renderUpcomingMovies };