/**
 * Register form to use in ConnexionPage
 * @type {string} registerLogin
 */

import show from "../../img/show.png";

const registerLogin = `
<div style="display: flex;" >
<div id="panel_static" style=" top: 130%; left: 50%; width: 90%; height: 180%;">
    <div class="panel__content left">
        <h1 class="panel__heading">Déjà un compte ?</h1>
        <p class="panel__copy">Connecte-toi pour explorer tes stages et booster ta carrière dès maintenant ! Accède à ton compte!</p>
        <button type="button" id="YesAccount" class="btn btn-secondary">Connexion</button>
    
        
    </div>
    
    <div class="panel__content right">
        <h1 class="panel__heading">Pas encore de compte ?</h1>
        <p class="panel__copy">Inscris-toi maintenant pour explorer de nouveaux horizons lors de tes stages ! Et enrichis ton expérience !</p>
        <button id="NoAccount" type="button" class="btn btn-secondary">S'inscrire</button>
    </div>

    <div id="panel_sliding" style="top: -15px; left: -3%; width: 700px; height: 480px;">
        <div class="panel_content_signup">
            <h1 class="panel__heading">Connexion</h1>
            <form id="loginForm" class="login">
                <div class="m-3 justify-content-center">
                    <label for="emailLogin" class="form-label">Email</label>
                    <input type="email" class="form-control" id="emailLogin" aria-describedby="emailHelp" required>
                </div>

                <div class="m-3">
                    <label for="passwordLogin" class="form-label">Mot de Passe</label>
                    <div class="input-group">
                        <input type="password" class="form-control" id="passwordLogin" required>
                        <button type="button" class="btn btn-outline-secondary" id="togglePassword"><img src="${show}"></button>
                    </div>    
                </div>
                <div style="display: flex; justify-content: center; align-items: center;">
                    <input type="checkbox" id="remember-me" name="remember-me" class="m-2">
                    <label for="remb">Remember me</label>
                </div>
                <br>
                <div style="display: flex; justify-content: center; align-items: center;">
                    <button type="submit" class="btn btn-primary">Connexion</button>
                </div>
                <div style="display: flex; justify-content: center; align-items: center;">
                    <p id="resultatSuccessLogin" class="text-success"></p>
                    <p id="resultatDangerLogin" class="text-danger"></p>
                </div>
            </form>
        </div>  
        
        
        <div class="panel_content_login">
            <h1 class="panel__heading">Inscription</h1>
            <form id="registerForm" class="signup">
                <div class="d-flex justify-content-center">
                    <div class="m-3 justify-content-center">
                        <label for="lastname" class="form-label">Nom</label>
                        <input type="text" class="form-control" id="lastname" aria-describedby="lastnameHelp" required>
                        <div id="messageErreurLastName" class="form-text text-danger"></div>
                    </div>
                    <div class="m-3 justify-content-center">
                        <label for="name" class="form-label">Prénom</label>
                        <input type="text" class="form-control" id="name" aria-describedby="nameHelp" required>
                        <div id="messageErreurname" class="form-text text-danger"></div>
                    </div>
                </div>

                <div class="d-flex justify-content-center">
                    <div class="m-3 justify-content-center">
                        <label for="email" class="form-label">Adresse E-mail</label>
                        <input type="email" class="form-control" id="email" aria-describedby="emailHelp" required>
                        <div id="messageErreurEmailRegister" class="form-text text-danger"></div>
                    </div>
                    <div class="m-3 justify-content-center">
                        <label for="phone" class="form-label">Téléphone</label>
                        <input type="phone" class="form-control" id="phone" aria-describedby="phoneHelp" required>
                        <div id="messageErreurPhone" class="form-text text-danger"></div>
                    </div>
                </div>

                <div class="d-flex justify-content-center">
                    <div class="m-3 justify-content-center">
                        <label for="password" class="form-label">Mot de passe</label>
                        <div class="input-group">
                            <input type="password" class="form-control" id="password" aria-describedby="passwordHelp" required>
                            <button type="button" class="btn btn-outline-secondary" id="toggleRegisterPassword"><img src="${show}"></button>
                        </div>
                        <div id="messageErreurPasswordRegister" class="form-text text-danger"></div>
                    </div>
                    

                    <div class="m-3 justify-content-center">
                        <label for="passwordConfirm" class="form-label">Confirmation mot de passe</label>
                        <div class="input-group">
                            <input type="password" class="form-control" id="passwordConfirm" aria-describedby="passwordConfirmHelp" required>
                            <button type="button" class="btn btn-outline-secondary" id="toggleConfirmPassword"><img src="${show}"></button>
                        </div>
                        <div id="messageErreurPasswordConfirm" class="form-text text-danger"></div>
                    </div>
                </div>
       
                
                <div style="display: flex; justify-content: center; align-items: center; margin-bottom:20%;">
                    <button id="connexion" type="submit" class="btn btn-primary">S'inscrire</button>
                </div>
                <div>
                <p id="resultatSuccesRegister" class="text-success"></p>
                <p id="resultatDangerRegister" class="text-danger"></p>
                </div>
            </form>  
        </div>    
    </div>   
</div>   
</div>
`;

export default registerLogin;