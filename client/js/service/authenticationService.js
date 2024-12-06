const apiUrl = 'http://localhost:8080/api/auth';

async function login(userCredentials) {
  try {
    const response = await fetch(`${apiUrl}/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(userCredentials),
    });

    if (!response.ok) {
      throw new Error('Failed to log in. Please check your credentials.');
    }

    return await response.json();
  } catch (error) {
    console.error('Error during login:', error);
    throw error;
  }
}

async function register(userDetails) {
  try {
    const response = await fetch(`${apiUrl}/register`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(userDetails),
    });

    if (!response.ok) {
      throw new Error('Failed to register. Please try again.');
    }

    return await response.json();
  } catch (error) {
    console.error('Error during registration:', error);
    throw error;
  }
}

export default { login, register };
