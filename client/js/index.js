import router from '../js/router.js';

const searchIcon = document.getElementById('searchIcon');
const searchForm = document.getElementById('search');
const nextBtn = document.getElementById('nextBtn');
const prevBtn = document.getElementById('prevBtn');
const signinBtn = document.getElementById('signinBtn');
const navbar = document.getElementById('anchors');
const myButton = document.getElementById('myBtn');

addEventListener('DOMContentLoaded', init);
addEventListener('hashchange', updateActiveLink);
window.addEventListener('scroll', handleScroll);
signinBtn.addEventListener('mouseover', handleSigninMouseOver);
signinBtn.addEventListener('mouseout', handleSigninMouseOut);
searchIcon.addEventListener('click', toggleSearchForm);
document.addEventListener('click', closeSearchFormOnOutsideClick);
window.onscroll = toggleBackToTopButtonVisibility;

function init() {
  router.init();
  updateActiveLink();
  configureSearchFormVisibility();
}

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

  configureSearchFormVisibility();

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

function configureSearchFormVisibility() {
  const hash = window.location.hash;
  const homePage = hash === '#/';

  if (['#/movies', '#/tvShows', '#/celebs'].includes(hash)) {
    searchIcon.style.visibility = 'visible';
    searchForm.style.visibility = 'visible';
    nextBtn.style.visibility = 'visible';
    prevBtn.style.visibility = 'visible';
  } else {
    nextBtn.style.visibility = 'hidden';
    prevBtn.style.visibility = 'hidden';
    searchIcon.style.visibility = homePage ? 'hidden' : 'visible';
    searchForm.style.visibility = homePage ? 'hidden' : 'visible';
  }
}

function handleScroll() {
  if (window.scrollY > 0) {
    navbar.classList.add('sticky');
  } else {
    navbar.classList.remove('sticky');
  }
}

function handleSigninMouseOver() {
  signinBtn.innerText = 'Coming Soon';
}

function handleSigninMouseOut() {
  signinBtn.innerText = 'Sign In';
}

function toggleSearchForm(event) {
  event.stopPropagation();
  searchForm.style.display = 'block';
  searchIcon.style.display = 'none';
}

function closeSearchFormOnOutsideClick(event) {
  const isClickInsideForm = searchForm.contains(event.target);
  if (!isClickInsideForm) {
    searchForm.style.display = 'none';
    searchIcon.style.display = 'block';
  }
}

function toggleBackToTopButtonVisibility() {
  if (document.body.scrollTop > 10000 || document.documentElement.scrollTop > 1000) {
    myButton.style.display = "block";
  } else {
    myButton.style.display = "none";
  }
}

function topFunction() {
  document.body.scrollTop = 0;
  document.documentElement.scrollTop = 0;
}
