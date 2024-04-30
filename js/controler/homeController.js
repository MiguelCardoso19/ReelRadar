import homeService from '../service/homeService.js';
import homeView from '../view/homeView.js';

async function init() {
    const upcomingMovies = await homeService.fetchUpcomingMovies();
    homeView.renderUpcomingMovies(upcomingMovies);

}

export default { init };