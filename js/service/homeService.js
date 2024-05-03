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

async function getNowPlayingMovies() {
    try {
        const url = `${apiUrl}movie/now_playing?api_key=${apiKey}`;
        const response = await fetch(url);

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const moviesData = await response.json();


        const moviesWithVideos = await Promise.all(moviesData.results.map(async (movie) => {
            const videoResponse = await fetch(`${apiUrl}movie/${movie.id}/videos?api_key=${apiKey}`);
            if (!videoResponse.ok) {
                throw new Error('Failed to fetch videos');
            }
            const videoData = await videoResponse.json();
            movie.videos = videoData.results;
            return movie;
        }));

        return moviesWithVideos;
        
    } catch (error) {
        console.error(error);
        throw error;
    }
}

export default { fetchUpcomingMovies, fetchUpTrendingTVShows, fetchUpTopRatedMovies, fetchTrendingPeople, getNowPlayingMovies };
