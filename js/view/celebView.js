const imagePath = 'https://image.tmdb.org/t/p/w500/';
let modal;

function render(celebrities, searchQuery = '') {
  const container = document.querySelector('#container');
  container.innerHTML = ''; 

  const list = document.createElement('ul');
  list.classList.add('movie-list');

  const filteredCelebrities = searchQuery ? celebrities.filter(celeb => celeb.name.toLowerCase().includes(searchQuery.toLowerCase())) : celebrities;

  filteredCelebrities.forEach(({ name, known_for_department, popularity, profile_path }) => {
    const item = document.createElement('li');
    item.classList.add('movie-item');

    const movieContainer = document.createElement('div');
    movieContainer.classList.add('movie-container'); 

    const image = document.createElement('img');
    image.src = `${imagePath}${profile_path}`;
    image.alt = name;

    image.addEventListener('click', () => {
      openModal({ name, known_for_department, popularity, profile_path });
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
        <img id="celebProfile" src="${imagePath}${celeb.profile_path}" alt="Celebrity Profile">
        </div>
          <div id="celebDetails">
          <h2 id="celebName"><strong>${celeb.name}</strong></h2><br><br>
            <p><b>Known for:</b> ${celeb.known_for_department}</p><br>
            <p><b>Popularity:</b> ${celeb.popularity}</p>
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

export default { render };
