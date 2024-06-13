import { clearPage } from "../../../utils/render";
import {
  getAuthenticatedUser,
  isAuthenticated,
} from '../../../utils/auths'
import Navigate from "../../Router/Navigate";

const ADMIN = 'administratif';

const HomePageAdmin = async () => {
  clearPage();

  if (!isAuthenticated()) {
    Navigate('/403');
  } else {
    const user = await getAuthenticatedUser();
    const role = user.user.utilisateurType;
    if (role !== ADMIN) {
      Navigate('/403');
    } else {
      await HomePageAdminListener();
    }
  }
};

async function HomePageAdminListener() {
  const main = document.querySelector('main');
  main.innerHTML = 'Welcome to the admin page';
}

export default HomePageAdmin;
