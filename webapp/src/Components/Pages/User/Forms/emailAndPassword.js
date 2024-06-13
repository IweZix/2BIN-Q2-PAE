/**
 * emailAndPassword
 * @param email user's email
 * @returns {string} emailAndPassword
 */
const emailAndPassword = (email) => `
    <div>
        <article id="allInfoOfOne" style="position: absolute; margin: 3% 0 0 51%; width:100%">
            <div class="header">
                <h2>Émail & Mot de passe</h2>
                <div class="d-flex justify-content-end">
                    <button type="button" class="btn btn-primary" id="emailAndPassword">Modifier</button>
                </div>
            </div>    
            
            <div>
                <form id="personalInfo">
                    <div>
                        <div class="row">
                            <div class="col">
                                <h5 style=" margin: 5% 0 0 1%;"><strong>Émail : </strong></h5>
                            </div>
                            <div class="col" style="margin-top:2%; font-size:1.2rem;">
                                ${email}
                            </div>
                        </div>

                        <div class="row">
                            <div class="col">
                                <h5 style=" margin: 5% 0 0 1%;"><strong>Mot de passe : </strong></h5>
                            </div>
                            <div class="col" style="margin-top:2%; font-size:1.2rem;">
                                ********
                            </div>
                        </div>         
                    </div>    
                </form>
            </div>
        </article>  
    </div>    
`;


module.exports = emailAndPassword;