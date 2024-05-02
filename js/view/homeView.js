import movieService from '../service/movieService.js';
import tvShowService from '../service/tvShowService.js';
import celebService from '../service/celebService.js';

const container = document.getElementById('container');
const imagePath = 'https://image.tmdb.org/t/p/w500/';
const noPosterBG = 'resources/no-poster.png';
const playButton = 'resources/play-button.png';
const backgroundImage = 'imagesForTests/black-banner.png';
let modal;

function render(upcomingMovies, trendingTVShows, topRatedMovies, trendingPeople) {
  container.innerHTML = '';

const elementsWrapperDiv = document.createElement('div');
elementsWrapperDiv.classList.add('elements-wrapper');


const section = document.createElement('section');
section.classList.add('custom-section');
section.style.backgroundImage = `url(${backgroundImage})`;


elementsWrapperDiv.appendChild(section);

container.appendChild(elementsWrapperDiv);


  const shadow = document.createElement('div');
  shadow.classList.add('shadow');
  section.appendChild(shadow);

  const movieCarouselContainer = document.createElement('div');
  movieCarouselContainer.classList.add('upcoming-carousel');

  const movieOwlCarousel = document.createElement('div');
  movieOwlCarousel.classList.add('owl-carousel');
  movieOwlCarousel.id = 'upcoming-movie-carousel';

  const movieHeading = document.createElement('h5');
  movieHeading.textContent = 'UPCOMING MOVIES';
  movieHeading.id = 'upcoming-movies-heading';
  movieCarouselContainer.appendChild(movieHeading);

  upcomingMovies.forEach(movie => {
    const slide = createMovieSlide(movie);
    movieOwlCarousel.appendChild(slide);
  });

  movieCarouselContainer.appendChild(movieOwlCarousel);
  container.appendChild(movieCarouselContainer);

  const tvCarouselContainer = document.createElement('div');
  tvCarouselContainer.classList.add('upcoming-carousel');

  const tvOwlCarousel = document.createElement('div');
  tvOwlCarousel.classList.add('owl-carousel');
  tvOwlCarousel.id = 'trending-tv-carousel';

  const tvHeading = document.createElement('h5');
  tvHeading.textContent = 'TRENDING TV SHOWS';
  tvHeading.id = 'trending-tvshows-heading';
  tvCarouselContainer.appendChild(tvHeading);

  trendingTVShows.forEach(tvShow => {
    const slide = createTVShowSlide(tvShow);
    tvOwlCarousel.appendChild(slide);
  });

  tvCarouselContainer.appendChild(tvOwlCarousel);
  container.appendChild(tvCarouselContainer);

  const topRatedCarouselContainer = document.createElement('div');
  topRatedCarouselContainer.classList.add('upcoming-carousel');

  const topRatedOwlCarousel = document.createElement('div');
  topRatedOwlCarousel.classList.add('owl-carousel');
  topRatedOwlCarousel.id = 'top-rated-movie-carousel';

  const topRatedHeading = document.createElement('h5');
  topRatedHeading.textContent = 'TOP RATED MOVIES';
  topRatedHeading.id = 'top-rated-movies-heading';
  topRatedCarouselContainer.appendChild(topRatedHeading);

  topRatedMovies.forEach(movie => {
    const slide = createMovieSlide(movie);
    topRatedOwlCarousel.appendChild(slide);
  });

  topRatedCarouselContainer.appendChild(topRatedOwlCarousel);
  container.appendChild(topRatedCarouselContainer);

  const peopleCarouselContainer = document.createElement('div');
  peopleCarouselContainer.classList.add('upcoming-carousel');

  const peopleOwlCarousel = document.createElement('div');
  peopleOwlCarousel.classList.add('owl-carousel');
  peopleOwlCarousel.id = 'trending-people-carousel';

  const peopleHeading = document.createElement('h5');
  peopleHeading.textContent = 'TRENDING PEOPLE';
  peopleHeading.id = 'trending-people-heading';
  peopleCarouselContainer.appendChild(peopleHeading);

  trendingPeople.forEach(person => {
    const slide = createPersonSlide(person);
    peopleOwlCarousel.appendChild(slide);
  });

  peopleCarouselContainer.appendChild(peopleOwlCarousel);
  container.appendChild(peopleCarouselContainer);

  $(document).ready(function(){
    $('.owl-carousel').owlCarousel({
      items: 8,
      loop: true,
      margin: 10,
      autoplay: true,
      autoplayTimeout: 2000,
      autoplayHoverPause: true
    });
  });
}

function createMovieSlide(movie) {
  const { id, title, poster_path } = movie;

  const slide = document.createElement('div');
  slide.classList.add('movie-item');

  const movieItemContent = document.createElement('div');
  movieItemContent.classList.add('movie-item-content');

  const image = document.createElement('img');
  image.src = `${poster_path ? imagePath + poster_path : noPosterBG}`;
  image.alt = title;
  image.style.width = '90%';

  image.addEventListener('click', async () => {
    openModal(await movieService.fetchMovieDetails(id));
  });

  movieItemContent.appendChild(image);
  slide.appendChild(movieItemContent);

  return slide;
}

function createTVShowSlide(tvShow) {
  const { id, name, poster_path } = tvShow;

  const slide = document.createElement('div');
  slide.classList.add('tv-show-item');
  slide.classList.add('movie-item'); 

  const tvShowItemContent = document.createElement('div');
  tvShowItemContent.classList.add('tv-show-item-content'); 
  tvShowItemContent.classList.add('movie-item-content'); 

  const image = document.createElement('img');
  image.src = `${poster_path ? imagePath + poster_path : noPosterBG}`;
  image.alt = name;
  image.style.width = '90%';

  image.addEventListener('click', async () => {
    openModal(await tvShowService.fetchTVShowDetails(id));
  });

  tvShowItemContent.appendChild(image);
  slide.appendChild(tvShowItemContent);

  return slide;
}

function createPersonSlide(person) {
  const { id, name, profile_path } = person;

  const slide = document.createElement('div');
  slide.classList.add('person-item');
  slide.classList.add('movie-item'); 

  const personItemContent = document.createElement('div');
  personItemContent.classList.add('person-item-content'); 
  personItemContent.classList.add('movie-item-content'); 

  const image = document.createElement('img');
  image.src = `${profile_path ? imagePath + profile_path : noPosterBG}`;
  image.alt = name;
  image.style.width = '90%';

  image.addEventListener('click', async () => {
    openCelebModal(await celebService.fetchCelebDetails(id));
  });

  personItemContent.appendChild(image);
  slide.appendChild(personItemContent);

  return slide;
}

function openCelebModal(celeb) {
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
          <div id="biography-container" class="biography-container">
            <p class="biography"><b>Biography:</b> ${truncateBiography(celeb.biography)}</p>
          </div>
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

function openModal(item) {
  const genreStrings = item.genres.map(genre => genre.name);
  const genreHTML = genreStrings.map(genre => `<span>${genre}</span>`).join('');

  const modalHTML = `
  <div id="movieDetailsModal" class="modal">
    <div class="modal-content">
      <span class="close">&times;</span>
      <div class="modal-body">
        <div class="moviePosterDiv">
          <img id="moviePoster" src="${item.poster_path ? imagePath + item.poster_path : noPosterBG}" alt="Movie Poster">
        </div>
        <div id="movieDetails">
          <div class="titleContainer">
            <h2 id="movieTitle"><strong>${item.title || item.name}</strong> </h2>
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
          <p class="release-date"><b>Release date:</b> ${item.release_date || item.first_air_date}</p><br>
          <div id="biography-container" class="biography-container">
            <p class="biography"><b>Overview:</b> ${truncateOverview(item.overview)}</p>
          </div>
          <p><b>Rating:</b> <span ${getColor(item.vote_average)}>${item.vote_average.toFixed(1)}</span></p>
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
              ${item.videos.results.length > 0 ? generateVideoLinks(item.videos.results) : ''}
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

export default { render };