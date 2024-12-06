import authService from '../service/authService.js';
import authView from '../view/authView.js';

async function handleLogin(event) {
  event.preventDefault();

  const credentials = authView.getLoginFormData();
  try {
    const loginResponse = await authService.login(credentials);
    authView.displaySuccess('Login successful! Redirecting...');
  } catch (error) {
    authView.displayError(error.message);
  }
}

async function handleRegister(event) {
  event.preventDefault();

  const userDetails = authView.getRegisterFormData();
  try {
    const registerResponse = await authService.register(userDetails);
    authView.displaySuccess('Registration successful! Redirecting...');
  } catch (error) {
    authView.displayError(error.message);
  }
}

function init() {
  const loginForm = document.getElementById('loginForm');
  const registerForm = document.getElementById('registerForm');

  if (loginForm) loginForm.addEventListener('submit', handleLogin);
  if (registerForm) registerForm.addEventListener('submit', handleRegister);
}

export default { init };
