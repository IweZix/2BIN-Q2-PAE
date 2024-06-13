import {
  clearToken
} from '../../../utils/auths';
import Navbar from '../../Navbar/Navbar';
import Navigate from '../../Router/Navigate';

/**
 * Logout component
 */
const Logout = () => {
  clearToken();
  Navbar();
  Navigate('/');
  window.location.reload();
};

export default Logout;
