import homeService from '../service/homeService.js';
import homeView from '../view/homeView.js';

async function init() {
    try {
        const upcomingMovies = await homeService.fetchUpcomingMovies();
        const trendingTVShows = await homeService.fetchUpTrendingTVShows();
        const topRatedMovies = await homeService.fetchUpTopRatedMovies();
        const nowPlayingMovies = await homeService.getNowPlayingMovies();
        const trendingPeople = await homeService.fetchTrendingPeople();
        homeView.render(upcomingMovies, trendingTVShows, topRatedMovies, trendingPeople, nowPlayingMovies);

    } catch (error) {
        console.error(error);
    }
}

export default { init };