import HomePage from '../Pages/HomePage';
import ConnexionPage from '../Pages/Connexion/ConnexionPage';
import Logout from '../Pages/Connexion/Logout'
import Forbidden from '../Pages/Errors/Forbidden'
import NotFound from "../Pages/Errors/NotFound";
import UserPage from "../Pages/User/UserPage";
import Student from "../Pages/HomePage/HomePageStudent";
import Teacher from "../Pages/HomePage/HomePageTeacher";
import Administrative from "../Pages/HomePage/HomePageAdmin";
import List from "../Pages/User/ListAllUser"

const routes = {
  '/': ConnexionPage,
  '/home': HomePage,
  '/logout': Logout,
  '/403': Forbidden,
  '/404': NotFound,
  '/etudiant': Student,
  '/professeur': Teacher,
  '/admin': Administrative,
  '/user': UserPage,
  '/list' : List
};

export default routes;
