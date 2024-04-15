const imagePath = 'https://image.tmdb.org/t/p/w500/';
let modal;

function render(tvShows, searchQuery = '') {
  const container = document.querySelector('#container');
  container.innerHTML = ''; 

  const list = document.createElement('ul');
  list.classList.add('movie-list');

  const filteredTvShows = searchQuery ? tvShows.filter(tvShow => tvShow.name.toLowerCase().includes(searchQuery.toLowerCase())) : tvShows;

  filteredTvShows.forEach(({ name, first_air_date: year, overview, vote_average: rating, poster_path }) => {
    const item = document.createElement('li');
    item.classList.add('movie-item');

    const movieContainer = document.createElement('div');
    movieContainer.classList.add('movie-container'); 

    const image = document.createElement('img');
    image.src = `${imagePath}${poster_path}`;
    image.alt = name;

    image.addEventListener('click', () => {
      openModal({ name, year, overview, rating, poster_path });
    });

    const titleElement = document.createElement('h5');
    titleElement.classList.add('movie-title');
    titleElement.textContent = name;

    movieContainer.appendChild(image);
    movieContainer.appendChild(titleElement);
    item.appendChild(movieContainer);
    list.appendChild(item);
  });

  container.appendChild(list);
}

function openModal(tvShow) {

  if (modal) {
    modal.remove();
  }

  const modalHTML = `
    <div id="movieDetailsModal" class="modal">
      <div class="modal-content">
        <span class="close">&times;</span>
        <div class="modal-body">
        <div class="moviePosterDiv">
        <img id="moviePoster" src="${imagePath}${tvShow.poster_path}" alt="Movie Poster">
        </div>
          <div id="movieDetails">
          <h2 id="movieTitle"><strong>${tvShow.name}</strong></h2><br><br>
            <p><b>First air date:</b> ${tvShow.year}</p><br>
            <p><b>Overview:</b> ${tvShow.overview}</p><br>
            <p><b>Rating:</b> ${tvShow.rating.toFixed(1)}</p>
            <div class="buttonDiv">
            <button id="goBackBtn">Go Back</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  `;

  document.body.insertAdjacentHTML('beforeend', modalHTML);

  modal = document.getElementById('movieDetailsModal');

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

export default { render };
