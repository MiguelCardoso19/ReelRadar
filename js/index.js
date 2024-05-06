import router from '/js/router.js';

addEventListener('DOMContentLoaded', () => {
  router.init();

  const navbar = document.getElementById('anchors');

  window.addEventListener('hashchange', () => {

    updateActiveLink();
  });

  updateActiveLink();

  window.addEventListener('scroll', function() {
    if (window.scrollY > 0) {
      navbar.classList.add('sticky');
    } else {
      navbar.classList.remove('sticky');
    }
  });
});

function updateActiveLink() {
  const currentPage = getCurrentPage();
  const activeLink = document.querySelector('#anchors a.active');

  if (activeLink) {
    activeLink.classList.remove('active');
  }

  document.getElementById(currentPage).classList.add('active');
}

function getCurrentPage() {
  const hash = window.location.hash;
  switch (hash) {
    case '#/movies':
      return 'films-href';
    case '#/tvShows':
      return 'tvShows-href';
    case '#/celebs':
      return 'celebs-href';
    default:
      return 'home-href';
  }
}

addEventListener('DOMContentLoaded', () => {
  const searchIcon = document.getElementById('searchIcon');
  const searchForm = document.getElementById('form');

  searchForm.style.display = 'none';


  searchIcon.addEventListener('click', (event) => {
    event.stopPropagation(); 
    searchForm.style.display = 'block';
    searchIcon.style.display = 'none';
  });

  document.addEventListener('click', (event) => {
    const isClickInsideForm = searchForm.contains(event.target);
    if (!isClickInsideForm) {
      searchForm.style.display = 'none';
      searchIcon.style.display = 'block';
    }
  });

  searchIcon.style.width = '25px'; 
  searchIcon.style.height = '25px'; 
  
});