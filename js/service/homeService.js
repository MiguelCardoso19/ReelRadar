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

async function fetchMovieImagesById(movieId) {

    const fanartApiKey = '010e8ef690f7501efdfde289e2f92425';

    const searchUrl = `https://webservice.fanart.tv/v3/movies/${movieId}?api_key=${fanartApiKey}`;

    try {
        const response = await fetch(searchUrl);

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();

        if (data.moviebackground && data.moviebackground.length > 0) {
            const images = data.moviebackground.map(image => image.url);

            return images;
        } else {
            return null;
        }
    } catch (error) {
        console.error('Error fetching movie images:', error);
        return null;
    }
}



async function getNowPlayingMovies() {
    try {

        const nowPlayingUrl = `${apiUrl}movie/now_playing?api_key=${apiKey}`;
        const response = await fetch(nowPlayingUrl);

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const moviesData = await response.json();

        const moviesWithVideosAndImages = await Promise.all(moviesData.results.map(async (movie) => {
            const videoResponse = await fetch(`${apiUrl}movie/${movie.id}/videos?api_key=${apiKey}`);
            if (!videoResponse.ok) {
                throw new Error('Failed to fetch videos');
            }
            const videoData = await videoResponse.json();
            movie.videos = videoData.results;

            const imdbIdResponse = await fetch(`${apiUrl}movie/${movie.id}/external_ids?api_key=${apiKey}`);
            if (!imdbIdResponse.ok) {
                throw new Error('Failed to fetch IMDb ID');
            }
            const imdbIdData = await imdbIdResponse.json();
            const imdbId = imdbIdData.imdb_id;

            const fanartImages = await fetchMovieImagesById(imdbId);
            movie.images = fanartImages;

            return movie;
        }));


        const filteredMovies = moviesWithVideosAndImages.filter(movie => movie.images !== null);
        return filteredMovies;
        
    } catch (error) {
        console.error(error);
        throw error;
    }
}



export default { fetchUpcomingMovies, fetchUpTrendingTVShows, fetchUpTopRatedMovies, fetchTrendingPeople, getNowPlayingMovies };
