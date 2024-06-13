import { clearPage } from '../../../utils/render';
import Navigate from '../../Router/Navigate';

const ise = `
    <div class="error text-center">
        <h1>403</h1>
        <p>Oups, tu as essayer d'avoir accès à un endroit qui ne t'es pas destiné</p>
        <p>&#58;&#40;</p>
        <button id="tryAgain" class="btn btn-warning">
            <img class="icon" id="reload" src="https://htmlacademy.ru/assets/icons/reload-6x-white.png">
            Laisse moi encore essayer
        </button>
    </div>
    `;

/**
 * Listener for 403 Forbidden
 */
const listenerInternalServerError = () => {
    clearPage();
    const main = document.querySelector('main');
    main.innerHTML = ise;
    const tryAgain = document.querySelector('#tryAgain');
    tryAgain.addEventListener('click', () => {
        Navigate('/')
    });
}

export default listenerInternalServerError;