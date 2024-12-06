const container = document.querySelector('#container');
let modal;

function renderLoginForm() {
  container.innerHTML = `
    <div class="auth-form">
      <h2>Login</h2>
      <form id="loginForm">
        <label for="email">Email</label>
        <input type="email" id="email" name="email" required />
        
        <label for="password">Password</label>
        <input type="password" id="password" name="password" required />
        
        <button type="submit" id="loginButton">Login</button>
      </form>
      <p>Don't have an account? <span id="showSignup">Sign up</span></p>
    </div>
  `;

  setupEventListeners('login');
}

function renderSignupForm() {
  container.innerHTML = `
    <div class="auth-form">
      <h2>Sign Up</h2>
      <form id="registerForm">
        <label for="name">Full Name</label>
        <input type="text" id="name" name="name" required />
        
        <label for="email">Email</label>
        <input type="email" id="email" name="email" required />
        
        <label for="password">Password</label>
        <input type="password" id="password" name="password" required />
        
        <button type="submit" id="signupButton">Sign Up</button>
      </form>
      <p>Already have an account? <span id="showLogin">Login</span></p>
    </div>
  `;

  setupEventListeners('signup');
}

function displaySuccess(message) {
  openModal(message, false);
}

function displayError(message) {
  openModal(message, true);
}

function openModal(message, isError = false) {
  const modalHTML = `
    <div id="authModal" class="modal">
      <div class="modal-content">
        <span class="close" id="closeModal">&times;</span>
        <p class="${isError ? 'error-message' : 'success-message'}">${message}</p>
      </div>
    </div>
  `;

  document.body.insertAdjacentHTML('beforeend', modalHTML);
  modal = document.getElementById('authModal');

  const closeModal = document.getElementById('closeModal');
  closeModal.onclick = closeAuthModal;

  window.onclick = event => {
    if (event.target === modal) {
      closeAuthModal();
    }
  };
}

function closeAuthModal() {
  if (modal) {
    modal.remove();
    modal = null;
  }
}

function getLoginFormData() {
  const email = document.getElementById('email').value;
  const password = document.getElementById('password').value;
  return { email, password };
}

function getRegisterFormData() {
  const name = document.getElementById('name').value;
  const email = document.getElementById('email').value;
  const password = document.getElementById('password').value;
  return { name, email, password };
}

function setupEventListeners(formType) {
  if (formType === 'login') {
    const loginForm = document.getElementById('loginForm');
    loginForm.addEventListener('submit', event => {
      event.preventDefault();
      document.dispatchEvent(new Event('auth:login'));
    });

    const showSignup = document.getElementById('showSignup');
    showSignup.addEventListener('click', renderSignupForm);
  } else if (formType === 'signup') {
    const registerForm = document.getElementById('registerForm');
    registerForm.addEventListener('submit', event => {
      event.preventDefault();
      document.dispatchEvent(new Event('auth:register'));
    });

    const showLogin = document.getElementById('showLogin');
    showLogin.addEventListener('click', renderLoginForm);
  }
}

export default {
  renderLoginForm,
  renderSignupForm,
  displaySuccess,
  displayError,
  getLoginFormData,
  getRegisterFormData,
};
