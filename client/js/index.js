import router from '../js/router.js';

const searchIcon = document.getElementById('searchIcon');
const searchForm = document.getElementById('search');
const nextBtn = document.getElementById('nextBtn');
const prevBtn = document.getElementById('prevBtn');
const signinBtn = document.getElementById('signinBtn');


addEventListener('DOMContentLoaded', () => {
  router.init();

  function topFunction() {
    document.body.scrollTop = 0; 
    document.documentElement.scrollTop = 0; 
  }

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
  const homePage = hash === '#/';

  switch (hash) {
    case '#/movies':
    case '#/tvShows':
    case '#/celebs':
      searchIcon.style.visibility = 'visible';
      searchForm.style.visibility = 'visible';
      nextBtn.style.visibility = 'visible';
      prevBtn.style.visibility = 'visible';
      break;
    default:
      nextBtn.style.visibility = 'hidden';
      prevBtn.style.visibility = 'hidden';
      searchIcon.style.visibility = homePage ? 'hidden' : 'visible';
      searchForm.style.visibility = homePage ? 'hidden' : 'visible';
      break;
  }

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


signinBtn.addEventListener('mouseover', function() {
  signinBtn.innerText = 'Coming Soon';
});

signinBtn.addEventListener('mouseout', function() {
  signinBtn.innerText = 'Sign In';
});

let mybutton = document.getElementById("myBtn");

window.onscroll = function() {
  scrollFunction();
};

function scrollFunction() {
  if (document.body.scrollTop > 10000 || document.documentElement.scrollTop > 1000) {
    mybutton.style.display = "block";
  } else {
    mybutton.style.display = "none";
  }
}