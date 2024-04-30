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

export default { fetchUpcomingMovies };