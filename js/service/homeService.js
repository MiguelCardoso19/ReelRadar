const apiKey = 'a7d624ec76cac151d6355506514023eb';
const apiUrl = 'https://api.themoviedb.org/3/';

async function fetchUpcomingMovies() {
    try {
        const response = await fetch(`${apiUrl}movie/upcoming?api_key=${apiKey}`);

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const moviesData = await response.json();
        return moviesData.results;

    } catch (ex) {
        console.error(ex);
        throw ex;
    }
}

async function fetchUpTopRatedMovies() {
    try {
        const response = await fetch(`${apiUrl}movie/top_rated?api_key=${apiKey}`);

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const moviesData = await response.json();
        return moviesData.results;

    } catch (ex) {
        console.error(ex);
        throw ex;
    }
}

async function fetchUpTrendingTVShows() {
    try {
        const response = await fetch(`${apiUrl}trending/tv/week?api_key=${apiKey}`);

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const tvShowsData = await response.json();
        return tvShowsData.results;

    } catch (ex) {
        console.error(ex);
        throw ex;
    }
}

async function fetchTrendingPeople() {
    try {
        const response = await fetch(`${apiUrl}trending/person/week?api_key=${apiKey}`);

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const peopleData = await response.json();
        return peopleData.results;

    } catch (ex) {
        console.error(ex);
        throw ex;
    }
}

export default { fetchUpcomingMovies, fetchUpTrendingTVShows, fetchUpTopRatedMovies, fetchTrendingPeople };
