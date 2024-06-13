import { clearPage, renderPageTitle } from "../../../utils/render";
import {
  getAuthenticatedUser,
  isAuthenticated, setLocalToken,
} from '../../../utils/auths'
import Navigate from "../../Router/Navigate";

import personnalInformation from './Forms/personnalInformation';
import personnalInformationUpdate from './Forms/personnalInformationUpdate';
import emailAndPassword from './Forms/emailAndPassword';
import emailAndPasswordUpdate from './Forms/emailAndPasswordUpdate';
import Navbar from "../../Navbar/Navbar";
import show from "../../../img/show.png";
import hide from "../../../img/hide.png";

/**
 * User page
 */
const UserPage = async () => {
  renderPageTitle('Profil');
  clearPage();

  const main = document.querySelector('main');

  if (!isAuthenticated()) {
    Navigate('/403');
  } else {
    let user = await getAuthenticatedUser();
    if (!user) {
      Navigate('/404');
    } else {
      user = user.user;
      const date = new Date(user.registrationDate);
      const options = { year: 'numeric', month: 'long', day: 'numeric' };
      const visualDate = date.toLocaleDateString('fr-FR', options);
      const personalInformationHTML = personnalInformation(
          user.firstName, user.lastName, visualDate,
          user.phone, user.academicYear);
      const emailAndPasswordHTML = emailAndPassword(user.email);
      main.innerHTML = `
        <section>
            <div>
                <div id="perso">
                    ${personalInformationHTML}
                </div>
                <div id="emailAndPassword">
                    ${emailAndPasswordHTML}
                </div>
            </div>
        </section>
      `;
      const buttons = document.querySelectorAll('button');
      buttons.forEach((button) => {
        button.addEventListener('click', () => {
          if (button.id === 'personnal') {
            updatePersonalInformationDisplay();
          } else if (button.id === 'emailAndPassword') {
            updateEmailAndPasswordDisplay();
          } else {
            Navigate('/404');
          }
        });
      });
    }
  }
};

/**
 * Function to display the form to update the personal information
 */
async function updatePersonalInformationDisplay() {
  const perso = document.getElementById('perso');

  let user = await getAuthenticatedUser();
  user = user.user;
  const date = new Date(user.registrationDate);
  const options = {year: 'numeric', month: 'long', day: 'numeric'};
  const visualDate = date.toLocaleDateString('fr-FR', options);

  const personnalInformationUpdateHTML = personnalInformationUpdate(
      user.firstName, user.lastName, visualDate,
      user.phone, user.academicYear);
  perso.innerHTML = personnalInformationUpdateHTML;

  const buttons = document.querySelectorAll('button');
  buttons.forEach((button) => {
    button.addEventListener('click', () => {
      if (button.id === 'cancel') {
        UserPage();
      } else if (button.id === 'accept') {
        const firstName = document.getElementById('firstName').value;
        const lastName = document.getElementById('lastName').value;
        const phone = document.getElementById('phone').value;
        updatePersonalInformation(firstName, lastName, phone, user.version)
      } else {
        Navigate('/404');
      }
    });
  });
}

/**
 * Function to update the personal information
 * @param firstName user's firstname
 * @param lastName user's lastname
 * @param phoneNumber user's phone number
 */
async function updatePersonalInformation(fisrtname, lastname, phoneNumber, version) {
  const token = localStorage.getItem('token') || sessionStorage.getItem('token');
  const request = await fetch('/api/auths/update', {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': token
    },
    body: JSON.stringify({
      firstName: fisrtname,
      lastName: lastname,
      phone: phoneNumber,
      version
    })
  });

  if (request.ok) {
    const resultFetch = await request.json();
    setLocalToken(resultFetch.token);
    Navbar();
    window.location.reload();
  } else {
    Navigate('/404');
  }
}

/**
 * Function to display the form to update the email and the password
 */
async function updateEmailAndPasswordDisplay() {
  const emailAndPasswordBalise = document.getElementById('emailAndPassword');
  let user = await getAuthenticatedUser();
  user = user.user;

  const emailAndPasswordUpdateHTML = emailAndPasswordUpdate(user.email);
  emailAndPasswordBalise.innerHTML = emailAndPasswordUpdateHTML;

  // Display or hide the password
  const showLastPassword = document.querySelector('#toggleLastPassword');
  showLastPassword.addEventListener('click', (e) => {
    e.preventDefault();
    const passwordInput = document.querySelector('#lastPassword');
    const toggleButton = document.querySelector('#toggleLastPassword');
    if (passwordInput.type === 'password') {
      passwordInput.type = 'text';
      toggleButton.innerHTML = `<img src="${hide}">`;
    } else {
      passwordInput.type = 'password';
      toggleButton.innerHTML = `<img src="${show}">`;
    }
  });

  // Display or hide the password
  const showNewPassword = document.querySelector('#toggleNewPassword');
  showNewPassword.addEventListener('click', (e) => {
    e.preventDefault();
    const passwordInput = document.querySelector('#newPassword');
    const toggleButton = document.querySelector('#toggleNewPassword');
    if (passwordInput.type === 'password') {
      passwordInput.type = 'text';
      toggleButton.innerHTML = `<img src="${hide}">`;
    } else {
      passwordInput.type = 'password';
      toggleButton.innerHTML = `<img src="${show}">`;
    }
  });

  // Display or hide the password
  const showNewPasswordConfirm = document.querySelector('#toggleNewPasswordConfirm');
  showNewPasswordConfirm.addEventListener('click', (e) => {
    e.preventDefault();
    const passwordInput = document.querySelector('#newPasswordConfirmation');
    const toggleButton = document.querySelector('#toggleNewPasswordConfirm');
    if (passwordInput.type === 'password') {
      passwordInput.type = 'text';
      toggleButton.innerHTML = `<img src="${hide}">`;
    } else {
      passwordInput.type = 'password';
      toggleButton.innerHTML = `<img src="${show}">`;
    }
  });

  const acceptButton = document.getElementById('accept');
  const cancelButton = document.getElementById('cancel');

  acceptButton.addEventListener('click', () => {
    const email = document.getElementById('email').value;
    const lastPassword = document.getElementById('lastPassword').value;
    const newPassword = document.getElementById('newPassword').value;
    const newPasswordConfirmation = document.getElementById('newPasswordConfirmation').value;
    updateEmailAndPassword(email, lastPassword, newPassword, newPasswordConfirmation, user.version);
  });

  cancelButton.addEventListener('click', () => {
    UserPage();
  });
}

/**
 * Function to update the email and the password
 * @param email user's email
 * @param lastPassword user's last password
 * @param newPassword user's new password
 * @param newPasswordConfirmation user's new password confirmation
 * @param version user's version
 */
async function updateEmailAndPassword(email, password, newPassword, newPasswordConfirmation, version) {
  let request;
  if (password === '' && newPassword === '' && newPasswordConfirmation === '') {
    request = await fetch('/api/auths/update', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `${localStorage.getItem('token') || sessionStorage.getItem('token')}`
      },
      body: JSON.stringify({
        email,
        version
      })
    });
  } else {
    request = await fetch('/api/auths/update', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `${localStorage.getItem('token') || sessionStorage.getItem('token')}`
      },
      body: JSON.stringify({
        email,
        password,
        version,
        newPassword,
        newPasswordConfirmation
      })
    });
  }
  if (request.ok) {
    const resultFetch = await request.json();
    setLocalToken(resultFetch.token);
    Navbar();
    window.location.reload();
  } else {
    Navigate('/404');
  }
}

export default UserPage;
