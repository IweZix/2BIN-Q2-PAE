import registerLogin from "../../Forms/register";
import Navigate from '../../Router/Navigate'
import {clearPage, renderPageTitle} from "../../../utils/render";
import {isAuthenticated,} from "../../../utils/auths";
import {login, register} from "./requests/Connexion";

import show from "../../../img/show.png";
import hide from "../../../img/hide.png";

const formConnection = `
    <section>
        <div class="d-flex justify-content-center">
            ${registerLogin}
        </div>
    </section>
`;

const ConnexionPage = () => {
  renderPageTitle("Connectez-vous");
  clearPage();
  
  if (isAuthenticated()) {
    Navigate('/home');
    return;
  }

  const main = document.querySelector("main");
  main.innerHTML = formConnection;
  const l = document.querySelector("#loginForm");
  const r = document.querySelector("#registerForm");
  const showPasswordCheckbox = document.querySelector('#togglePassword');
  showPasswordCheckbox.addEventListener('click', () => {
    const passwordInput = document.querySelector('#passwordLogin');
    const toggleButton = document.querySelector('#togglePassword');
    if (passwordInput.type === 'password') {
      passwordInput.type = 'text';
      toggleButton.innerHTML = `<img src="${hide}">`;
    } else {
      passwordInput.type = 'password';
      toggleButton.innerHTML = `<img src="${show}">`;
    }
  });
  l.addEventListener("submit", (e) => {
    e.preventDefault();
    const email = document.querySelector("#emailLogin").value;
    const password = document.querySelector("#passwordLogin").value;
    const remember = document.querySelector("#remember-me").checked;
    login(email, password, remember);
  });
  const showPasswordCheckboxRegister = document.querySelector('#toggleRegisterPassword');
  showPasswordCheckboxRegister.addEventListener('click', () => {
    const passwordInput = document.querySelector('#password');
    const toggleButton = document.querySelector('#toggleRegisterPassword');
    if (passwordInput.type === 'password') {
      passwordInput.type = 'text';
      toggleButton.innerHTML = `<img src="${hide}">`;
    } else {
      passwordInput.type = 'password';
      toggleButton.innerHTML = `<img src="${show}">`;
    }
  });
  const showPasswordCheckboxConfirmRegister = document.querySelector('#toggleConfirmPassword');
  showPasswordCheckboxConfirmRegister.addEventListener('click', () => {
    const passwordInput = document.querySelector('#passwordConfirm');
    const toggleButton = document.querySelector('#toggleConfirmPassword');
    if (passwordInput.type === 'password') {
      passwordInput.type = 'text';
      toggleButton.innerHTML = `<img src="${hide}">`;
    } else {
      passwordInput.type = 'password';
      toggleButton.innerHTML = `<img src="${show}">`;
    }
  });
  r.addEventListener("submit", (e) => {
    e.preventDefault();
    const name = document.querySelector("#name").value;
    const lastname = document.querySelector("#lastname").value;
    const email = document.querySelector("#email").value;
    const phone = document.querySelector("#phone").value;
    const password = document.querySelector("#password").value;
    const passwordConfirm = document.querySelector("#passwordConfirm").value;

    register(name, lastname, email, phone, password, passwordConfirm);
  });

  const staticPanel = document.querySelector("#panel_static");
  const slidingPanel = document.querySelector("#panel_sliding");
    
  const loginBtn = staticPanel.querySelector("#YesAccount");
  const signupBtn = staticPanel.querySelector("#NoAccount");
    
  const signupContent = slidingPanel.querySelector(".panel_content_signup");
  const loginContent = slidingPanel.querySelector(".panel_content_login");

  loginContent.style.display = 'none';
  loginBtn.addEventListener('click', () => {
    loginContent.style.display = 'none';
    signupContent.style.display = 'block';
    slidingPanel.style.transition = 'left 0.55s cubic-bezier(0.68, -0.55, 0.27, 1.55)';
    slidingPanel.style.left = '-3%';
  });

  signupBtn.addEventListener('click', () => {
    signupContent.style.display = 'none';
    loginContent.style.display = 'block';
    slidingPanel.style.transition = 'left 0.55s cubic-bezier(0.68, -0.55, 0.27, 1.55)';
    slidingPanel.style.left = '50%';
  })
};

export default ConnexionPage;
