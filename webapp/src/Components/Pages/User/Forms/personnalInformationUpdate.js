/**
 * formPersonalInformation
 * @param firstname user's firstname
 * @param lastname user's lastname
 * @param inscriptionDate user's registration date
 * @param num user's phone number
 * @param academicYear user's academic year
 * @returns {string} formPersonalInformation
 */
const formPersonalInformation = (firstname, lastname, inscriptionDate, num, academicYear) =>
    `
    <div >
        <article id="allInfoOfOne" style="position: absolute; margin: 3% 0 0 2%; width:100%">
            <div class="header">
                <h2>Informations Personnelles</h2>
                <div class="d-flex justify-content-end">
                    <button type="button" class="btn btn-danger" id="cancel">Annuler</button>
                    <button type="button" class="btn btn-success" id="accept">Accepter</button>
                </div>
            </div>    
            
            <div>
                <form id="personalInfo">
                    <div>
                        <div class="row">
                            <div class="col">
                                <h5 style=" margin: 5% 0 0 1%;"><strong>Prénom : </strong></h5>
                            </div>
                            <div class="col" style="margin-top:2%; font-size:1.2rem;">
                                <input type="text" class="form-control" id="firstName" value="${firstname}">
                            </div>
                        </div>

                        <div class="row">
                            <div class="col">
                                <h5 style=" margin: 5% 0 0 1%;"><strong>Nom : </strong></h5>
                            </div>
                            <div class="col" style="margin-top:2%; font-size:1.2rem;">
                                <input type="text" class="form-control" id="lastName" value="${lastname}">
                            </div>
                        </div>

                        <div class="row">
                            <div class="col">
                                <h5 style=" margin: 5% 0 0 1%;"><strong>Numéro de téléphone : </strong></h5>
                            </div>
                            <div class="col" style="margin-top:2%; font-size:1.2rem;">
                                <input type="text" class="form-control" id="phone" value="${num}">
                            </div>
                        </div>

                        <div class="row">
                            <div class="col">
                                <h5 style=" margin: 5% 0 0 1%;"><strong>Date d'inscription : </strong></h5>
                            </div>
                            <div class="col" style="margin-top:2%; font-size:1.2rem;">
                                ${inscriptionDate}
                            </div>
                        </div>

                        <div class="row">
                            <div class="col">
                                <h5 style=" margin: 5% 0 0 1%;"><strong>Année académique : </strong></h5>
                            </div>
                            <div class="col" style="margin-top:2%; font-size:1.2rem;">
                                ${academicYear}
                            </div>
                        </div>           
                    </div>    
                </form>
            </div>
        </article>  
    </div>   
`;


module.exports = formPersonalInformation;
