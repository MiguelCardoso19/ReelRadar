const userView = {
    renderUserSection() {
      const appContainer = document.getElementById('app');
      appContainer.innerHTML = `
        <div id="userApp">
          <h2>User Management</h2>
  
          <h3>Get User Details</h3>
          <form id="getUserDetailsForm">
            <label for="getDetailsUsername">Username:</label>
            <input type="text" id="getDetailsUsername" placeholder="Enter Username" />
            <button type="submit">Get Details</button>
          </form>
          <div id="userDetailsContainer"></div>
  
          <h3>Update User</h3>
          <form id="updateUserForm">
            <label for="updateUsername">Username:</label>
            <input type="text" id="updateUsername" placeholder="Enter Username" />
  
            <label for="updateName">Name:</label>
            <input type="text" id="updateName" placeholder="Enter New Name" />
  
            <label for="updateEmail">Email:</label>
            <input type="email" id="updateEmail" placeholder="Enter New Email" />
  
            <button type="submit">Update User</button>
          </form>
  
          <h3>Delete User</h3>
          <form id="deleteUserForm">
            <label for="deleteUsername">Username:</label>
            <input type="text" id="deleteUsername" placeholder="Enter Username" />
            <button type="submit">Delete User</button>
          </form>
  
          <div id="messageContainer"></div>
        </div>
      `;
    },
  
    getDeleteUserFormData() {
      const username = document.getElementById('deleteUsername').value;
      return { username };
    },
  
    getUpdateUserFormData() {
      const username = document.getElementById('updateUsername').value;
      const name = document.getElementById('updateName').value;
      const email = document.getElementById('updateEmail').value;
  
      return {
        username,
        updateData: { name, email },
      };
    },
  
    getUsernameInput() {
      return document.getElementById('getDetailsUsername').value;
    },
  
    renderUserDetails(userDetails) {
      const userDetailsContainer = document.getElementById('userDetailsContainer');
      userDetailsContainer.innerHTML = `
        <p><strong>Username:</strong> ${userDetails.username}</p>
        <p><strong>Name:</strong> ${userDetails.name}</p>
        <p><strong>Email:</strong> ${userDetails.email}</p>
      `;
    },
  
    displaySuccess(message) {
      const messageContainer = document.getElementById('messageContainer');
      messageContainer.innerHTML = `<p class="success">${message}</p>`;
    },
  
    displayError(message) {
      const messageContainer = document.getElementById('messageContainer');
      messageContainer.innerHTML = `<p class="error">${message}</p>`;
    },
  };
  
  export default userView;
  