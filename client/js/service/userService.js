const apiUrl = 'http://localhost:8080/api/user';

async function deleteUser(userDeleteData) {
  try {
    const response = await fetch(`${apiUrl}/delete`, {
      method: 'DELETE',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(userDeleteData),
    });

    if (!response.ok) {
      throw new Error('Failed to delete user.');
    }
    return await response.text();
  } catch (error) {
    console.error('Error deleting user:', error);
    throw error;
  }
}

async function updateUser(username, updateData) {
  try {
    const response = await fetch(`${apiUrl}/update/${username}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(updateData),
    });

    if (!response.ok) {
      throw new Error('Failed to update user.');
    }
    return await response.text();
  } catch (error) {
    console.error('Error updating user:', error);
    throw error;
  }
}

async function getUserDetails(username) {
  try {
    const response = await fetch(`${apiUrl}/details/${username}`, {
      method: 'GET',
    });

    if (!response.ok) {
      throw new Error('Failed to fetch user details.');
    }
    return await response.json();
  } catch (error) {
    console.error('Error fetching user details:', error);
    throw error;
  }
}

export default { deleteUser, updateUser, getUserDetails };
