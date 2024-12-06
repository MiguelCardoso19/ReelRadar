import favoriteService from '../service/favoriteService.js';
import favoriteView from '../view/favoriteView.js';

async function fetchAndRenderFavorites(userId) {
  try {
    const favorites = await favoriteService.getFavorites(userId);
    favoriteView.renderFavorites(favorites);
  } catch (error) {
    favoriteView.displayError('Unable to fetch favorites.');
  }
}

async function handleAddFavorite(event) {
  event.preventDefault();
  const userId = document.getElementById('userId').value;
  const favoriteData = favoriteView.getFavoriteFormData();

  try {
    const responseMessage = await favoriteService.addFavorite(userId, favoriteData);
    favoriteView.displaySuccess(responseMessage);
    fetchAndRenderFavorites(userId);
  } catch (error) {
    favoriteView.displayError('Failed to add favorite.');
  }
}

async function handleRemoveFavorite(event) {
  event.preventDefault();
  const userId = document.getElementById('userId').value;
  const deleteRequest = favoriteView.getDeleteRequestData();

  try {
    const responseMessage = await favoriteService.removeFavorite(userId, deleteRequest);
    favoriteView.displaySuccess(responseMessage);
    fetchAndRenderFavorites(userId);
  } catch (error) {
    favoriteView.displayError('Failed to remove favorite.');
  }
}

function init() {
  favoriteView.renderFavoritesSection();

  const addFavoriteForm = document.getElementById('addFavoriteForm');
  const removeFavoriteForm = document.getElementById('removeFavoriteForm');
  const userIdInput = document.getElementById('userId');

  if (addFavoriteForm) {
    addFavoriteForm.addEventListener('submit', handleAddFavorite);
  }
  if (removeFavoriteForm) {
    removeFavoriteForm.addEventListener('submit', handleRemoveFavorite);
  }
  if (userIdInput) {
    userIdInput.addEventListener('change', (event) => {
      fetchAndRenderFavorites(event.target.value);
    });
  }
}

export default { init };
