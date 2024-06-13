/**
 * emailAndPassword
 * @param email user's email
 * @returns {string} emailAndPassword
 */
import show from "../../../../img/show.png";

const emailAndPassword = (email) => `
    <div>
        <article id="allInfoOfOne" style="position: absolute; margin: 3% 0 0 51%; width:100%">
            <div class="header">
                <h2>Émail & Mot de passe</h2>
                <div class="d-flex justify-content-end">
                    <button type="button" class="btn btn-danger" id="cancel">Annuler</button>
                    <button type="button" class="btn btn-success" id="accept">Accepter</button>
                </div>
            </div>    
            
            <div>
                <form id="personalInfo">
                    <div>
                            <div class="row" style="margin-top:2%;">
                                <div class="col">
                                    <h5><strong>Émail :</strong></h5>
                                </div>
                                <div class="col" >
                                    <div id="email" style="font-size:1.2rem;">${email}</div>
                                </div>
                            </div>

                            <div class="row" style="margin-top:2%;">
                                <div class="col">
                                    <h5><strong>Ancien mot de passe</strong></h5>
                                </div>
                                <div class="col input-group">
                                    <input type="password" class="form-control" id="lastPassword" value="" required>
                                    <button type="button" class="btn btn-outline-secondary" id="toggleLastPassword"><img src="${show}"></button>
                                </div>
                            </div>

                            <div class="row" style="margin-top:2%;">
                                <div class="col">
                                    <h5><strong>Nouveau mot de passe :</strong></h5>
                                </div>
                                <div class="col input-group">
                                    <input type="password" class="form-control" id="newPassword" value="" required>
                                    <button type="button" class="btn btn-outline-secondary" id="toggleNewPassword"><img src="${show}"></button>
                                </div>
                            </div>

                            <div class="row" style="margin-top:2%;">
                                <div class="col">
                                    <h5><strong>Confirmation nouveau mot de passe :</strong></h5>
                                </div>
                                <div class="col input-group">
                                    <input type="password" class="form-control" id="newPasswordConfirmation" value="" required>
                                    <button type="button" class="btn btn-outline-secondary" id="toggleNewPasswordConfirm"><img src="${show}"></button>
                                </div>
                            </div>
                    </div>    
                </form>
            </div>

        </article>  
    </div>  
`;


export default emailAndPassword;
