const roleForm = require("../../../Forms/role").default;
const Navigate = require("../../../Router/Navigate").default;
const Navbar = require("../../../Navbar/Navbar").default;
const {clearPage} = require("../../../../utils/render");
const {setLocalToken, setSessionToken} = require("../../../../utils/auths");

/**
 * Regular expression for email validation
 * name.lastname@student.vinci.be
 * name.lastname@vinci.be
 */
const regex = /^[a-zA-Z]+\.[a-zA-Z]+@(student\.)?vinci\.be$/;

/**
 * Roles
 * @type {string}
 */
const ROLE_ADMIN = "administratif";
const ROLE_TEACHER = "professeur";
const ROLE_STUDENT = "étudiant";

/**
 * Log a user into the site
 * @param {String} email user's email
 * @param {String} password user's password
 * @param {Boolean} remember whether to remember the user or not
 */
async function login(email, password, remember) {
  if (!email || !password) {
    const result = document.querySelector("#resultatDangerLogin");
    result.innerText = "Veuillez remplir tous les champs";
    return;
  }
  if (regex.test(email) === false) {
    const result = document.querySelector("#resultatDangerLogin");
    result.innerText = "Email invalide";
    return;
  }

  const request = await fetch('/api/auths/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      email,
      password,
    }),
  });

  if (request.ok) {
    const resultFetch = await request.json();
    if (remember) {
      setLocalToken(resultFetch.token);
    } else {
      setSessionToken(resultFetch.token);
    }
    Navbar();
    Navigate('/home');
  } else {
    const result = document.querySelector("#resultatDangerLogin");
    result.innerText = "Email ou mot de passe incorrect";
  }
}

/**
 * Register a new user
 * @param {String} name user's name
 * @param {String} lastname user's lastname
 * @param {String} email user's email
 * @param {String} phone user's phone
 * @param {String} password user's password
 * @param {String} passwordConfirm password confirmation
 */
async function register(name, lastname, email, phone, password,
    passwordConfirm) {
  if (!name || !lastname || !email || !phone || !password || !passwordConfirm) {
    const result = document.querySelector("#resultatDangerRegister");
    result.innerText = "Veuillez remplir tous les champs";
    return;
  }
  if (regex.test(email) === false) {
    const result = document.querySelector("#resultatDangerRegister");
    result.innerText = "Email invalide";
    return;
  }
  if (password !== passwordConfirm) {
    const result = document.querySelector("#resultatDangerRegister");
    result.innerText = "Vos passwords ne correspondent pas";
    return;
  }
  if (!phone.match(/^\d{4,}/)) {
    const result = document.querySelector("#resultatDangerRegister");
    result.innerText = "Numéro de téléphone trop court";
    return;
  }

  if (email.endsWith("@student.vinci.be")) {
    await registerUser(name, lastname, email, phone, password, ROLE_STUDENT);
    return;
  }

  clearPage();

  const main = document.querySelector("main");
  main.innerHTML = roleForm;
  const admin = document.querySelector("#admin");
  const teacher = document.querySelector("#teacher");

  admin.addEventListener("click", () => {
    registerUser(name, lastname, email, phone, password, ROLE_ADMIN);
  });

  teacher.addEventListener("click", () => {
    registerUser(name, lastname, email, phone, password, ROLE_TEACHER);
  });
}

/**
 * Register a user
 * @param name user's name
 * @param lastname user's lastname
 * @param email user's email
 * @param phone user's phone
 * @param password user's password
 * @param role user's role
 * @returns {Promise<void>} the result of the request
 */
async function registerUser(name, lastname, email, phone, password, role) {
  const request = await fetch('/api/auths/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      firstName: name,
      lastName: lastname,
      email,
      phone,
      password,
      utilisateurType: role,
    }),
  });

  if (request.ok) {
    const result = await request.json();
    setLocalToken(result.token);
    Navbar();
    Navigate('/home');
  } else {
    const result = document.querySelector("#resultatDangerRegister");
    result.innerText = "Email déjà utilisé";
  }
}

module.exports = {
  login,
  register,
}