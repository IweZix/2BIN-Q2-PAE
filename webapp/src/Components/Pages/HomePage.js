import {clearPage, renderPageTitle} from "../../utils/render";
import {
  clearToken,
  getAuthenticatedUser,
  isAuthenticated,
} from '../../utils/auths'
import Navigate from "../Router/Navigate";

const STUDENT = 'étudiant';
const TEACHER = 'professeur';
const ADMIN = 'administratif';

const HomePage = async () => {
  renderPageTitle('Accueil');
  clearPage();

  if (!isAuthenticated()) {
    Navigate('/403');
  } else {
    const user = await getAuthenticatedUser();
    if (!user) {
      clearToken();
      Navigate('/connexion');
    }
    const role = user.user.utilisateurType;
    if (role === STUDENT) {
      Navigate('/etudiant');
    } else if (role === TEACHER) {
      Navigate('/professeur');
    } else if (role === ADMIN) {
      Navigate('/list');
    } else {
      Navigate('/403');
    }
  }
};

export default HomePage;
