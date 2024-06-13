import { clearPage } from '../../../utils/render';
import Navigate from '../../Router/Navigate';

const ise = `
    <section class="page_404 text-center">
      <div class="container text-center">
        <div class="row">
          <div class="">
            <div class="text-center"> 
              <div class="four_zero_four_bg">
                <h1 class="text-center ">404</h1>
              </div>
              <div class="contant_box_404">
                <h3 class="h2">On dirait que vous êtes perdu</h3>
                <p>La page que vous recherchez n'est pas disponible !</p>
                <button id="tryAgain" class="btn btn-warning">
                    Laissez moi encore essayer
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    <style>
      .page_404{ 
        padding:40px 0; 
        background:#fff; 
        font-family: 'Arvo', serif;
      }
      
      .page_404  img{ 
        width:100%;
        }
      
      .four_zero_four_bg{
        background-image: url(https://cdn.dribbble.com/users/285475/screenshots/2083086/dribbble_1.gif);
        height: 400px;
        background-position: center;
      }
       
       
      .four_zero_four_bg h1{
        font-size:80px;
      }
       
      .four_zero_four_bg h3{
        font-size:80px;
      }
       
       .link_404{ 
          color: #fff!important;
          padding: 10px 20px;
          background: #39ac31;
          margin: 20px 0;
          display: inline-block;
       }
       
      .contant_box_404{
        margin-top:-50px;
      }
    </style>
`;

/**
 * Listener for 404 Not Found
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