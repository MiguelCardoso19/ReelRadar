import router from '/js/router.js';

addEventListener('DOMContentLoaded', () => {
  router.init();
  window.addEventListener('scroll', function() {
    const navbar = document.getElementById('anchors');
    if (window.scrollY > 0) {
      navbar.classList.add('sticky');
    } else {
      navbar.classList.remove('sticky');
    }
  });
})