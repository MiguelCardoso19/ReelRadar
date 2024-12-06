const apiUrl = 'http://localhost:8080/api/favorites';

const favoriteService = {
  async getFavorites(userId) {
    try {
      const response = await fetch(`${apiUrl}/show/${userId}`);
      if (!response.ok) {
        throw new Error('Failed to fetch favorites');
      }
      return await response.json();
    } catch (error) {
      console.error('Error fetching favorites:', error);
      throw error;
    }
  },

  async addFavorite(userId, favoriteData) {
    try {
      const response = await fetch(`${apiUrl}/add/${userId}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(favoriteData),
      });
      if (!response.ok) {
        throw new Error('Failed to add favorite');
      }
      return await response.text();
    } catch (error) {
      console.error('Error adding favorite:', error);
      throw error;
    }
  },

  async removeFavorite(userId, deleteRequest) {
    try {
      const response = await fetch(`${apiUrl}/remove/${userId}`, {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(deleteRequest),
      });
      if (!response.ok) {
        throw new Error('Failed to remove favorite');
      }
      return await response.text();
    } catch (error) {
      console.error('Error removing favorite:', error);
      throw error;
    }
  },
};

export default favoriteService;
