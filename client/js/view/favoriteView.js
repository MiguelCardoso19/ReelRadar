const favoriteView = {
    renderFavoritesSection() {
      const appContainer = document.getElementById('app');
      appContainer.innerHTML = `
        <div id="favoriteApp">
          <h2>User Favorites</h2>
          
          <label for="userId">User ID:</label>
          <input type="text" id="userId" placeholder="Enter User ID" />
  
          <h3>Favorites List</h3>
          <ul id="favoriteList"></ul>
  
          <h3>Add Favorite</h3>
          <form id="addFavoriteForm">
            <label for="favoriteType">Type:</label>
            <input type="text" id="favoriteType" placeholder="e.g., Movie, TVShow, Person" />
  
            <label for="favoriteValue">Value:</label>
            <input type="text" id="favoriteValue" placeholder="Enter favorite value" />
  
            <button type="submit">Add Favorite</button>
          </form>
  
          <h3>Remove Favorite</h3>
          <form id="removeFavoriteForm">
            <label for="deleteFavoriteType">Type:</label>
            <input type="text" id="deleteFavoriteType" placeholder="e.g., Movie, TVShow, Person" />
  
            <label for="deleteFavoriteValue">Value:</label>
            <input type="text" id="deleteFavoriteValue" placeholder="Enter value to remove" />
  
            <button type="submit">Remove Favorite</button>
          </form>
  
          <div id="favoriteMessageContainer"></div>
        </div>
      `;
    },
  
    getFavoriteFormData() {
      const type = document.getElementById('favoriteType').value;
      const value = document.getElementById('favoriteValue').value;
      return { type, value };
    },
  
    getDeleteRequestData() {
      const type = document.getElementById('deleteFavoriteType').value;
      const value = document.getElementById('deleteFavoriteValue').value;
      return { type, value };
    },
  
    renderFavorites(favorites) {
      const favoriteList = document.getElementById('favoriteList');
      favoriteList.innerHTML = '';
  
      favorites.forEach((favorite) => {
        const listItem = document.createElement('li');
        listItem.textContent = `${favorite.type}: ${favorite.value}`;
        favoriteList.appendChild(listItem);
      });
    },
  
    displaySuccess(message) {
      const messageContainer = document.getElementById('favoriteMessageContainer');
      messageContainer.innerHTML = `<p class="success">${message}</p>`;
    },
  
    displayError(message) {
      const messageContainer = document.getElementById('favoriteMessageContainer');
      messageContainer.innerHTML = `<p class="error">${message}</p>`;
    },
  };
  
  export default favoriteView;
  