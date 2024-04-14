import homeView from '../view/homeView.js';
import filmService from '../service/filmService.js';

function init() {
  homeView.render(filmService.getFilm);
};

export default { init };
