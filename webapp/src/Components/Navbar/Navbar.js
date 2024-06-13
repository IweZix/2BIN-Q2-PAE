import {getAuthenticatedUser} from '../../utils/auths';
import logoutUrl from '../../img/logout.svg';
import logo from '../../img/logoSite.png';

const STUDENT = 'étudiant';
const TEACHER = 'professeur';
const ADMIN = 'administratif';

/**
 * Navbar component
 */
const Navbar = async () => {
  const navbar = document.querySelector('#navbarWrapper');

  /**
   * Navbar for anonymous users
   * @type {string} navbarAnonymous
   */
  const navbarAnonymous = `
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
          <a class="navbar-brand" href="#" data-uri="/">
            <img src="${logo}" alt="Nom de votre application" style="height: 50px;">
          </a>
          <button
            class="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span class="navbar-toggler-icon"></span>
          </button>
        </div>
      </nav>
`;

  /**
   * Navbar for connected users
   * @type {string} navbarConnected
   */
  const navbarConnected = `
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand me-auto" href="#" data-uri="/home">
      <img src="${logo}" alt="Nom de votre application" style="height: 50px;">
    </a>
    <button
      class="navbar-toggler"
      type="button"
      data-bs-toggle="collapse"
      data-bs-target="#navbarSupportedContent"
      aria-controls="navbarSupportedContent"
      aria-expanded="false"
      aria-label="Toggle navigation"
    >
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link" aria-current="page" href="#" data-uri="/">Accueil</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#" data-uri="/user">Profil</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#" data-uri="/logout"><img src="${logoutUrl}"></a>
        </li>
      </ul>
    </div>
  </div>
</nav>
  `

  const navbarProfessor = `
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="#" data-uri="/home"> 
      <img src="${logo}" alt="Nom de votre application" style="height: 50px;">
    </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link" aria-current="page" href="#" data-uri="/">Accueil</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#" data-uri="/list">Liste utilisateurs</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#" data-uri="/user">Profil</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#" data-uri="/logout"><img src="${logoutUrl}"></a>
        </li>
      </ul>
    </div>
  </div>
</nav>

  `;


  const user = await getAuthenticatedUser();
  if (!user) {
    navbar.innerHTML = navbarAnonymous;
  } else {
    const role = user.user.utilisateurType;
    if (role === STUDENT) {
      navbar.innerHTML = navbarConnected;
    } else if (role === TEACHER) {
      navbar.innerHTML = navbarProfessor;
    } else if (role === ADMIN) {
      navbar.innerHTML = navbarConnected;
    }
  }
};

export default Navbar;
