const imagePath = 'https://image.tmdb.org/t/p/w500/';
const noPosterBG = 'resources/no-poster.png';
const noResultsImage = 'resources/no-results.png';
let modal;

function render(celebrities) {
  const container = document.querySelector('#container');
  container.innerHTML = ''; 

  const list = document.createElement('ul');
  list.classList.add('movie-list');


  celebrities.forEach(({name, known_for_department, popularity, profile_path}) => {
    const item = document.createElement('li');
    item.classList.add('movie-item');

    const movieContainer = document.createElement('div');
    movieContainer.classList.add('movie-container'); 

    const image = document.createElement('img');
    image.src = `${profile_path ? imagePath + profile_path : noPosterBG}`;
    image.alt = name;

    image.addEventListener('click', () => {
      openModal({name, known_for_department, popularity, profile_path });
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

function openModal(celeb) {

  if (modal) {
    modal.remove();
  }

  const modalHTML = `
    <div id="celebDetailsModal" class="modal">
      <div class="modal-content">
        <span class="close">&times;</span>
        <div class="modal-body">
        <div class="celebProfileDiv">
        <img id="celebProfile" src="${celeb.profile_path ? imagePath + celeb.profile_path : noPosterBG}" alt="Celebrity Profile">
        </div>
          <div id="celebDetails">
          <h2 id="celebName"><strong>${celeb.name}</strong></h2><br><br>
            <p><b>Known for:</b> ${celeb.known_for_department}</p><br>
            <p><b>Popularity:</b> <span ${getColor(celeb.popularity)}>${celeb.popularity.toFixed(1)}</span></p>
            <div class="buttonDiv">
            <button id="goBackBtn">Go Back</button>
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

function getColor(popularity) {
  if(popularity >= 55){
    return 'style="color: lightgreen; font-size: 16px;"';

  } else if(popularity >= 40){
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

export default {render, renderNotFound};