/**
 * 
 * @param tradeNameCompany the TradeName of the company
 * @param designationCompany the designation of the company
 * @param internshipSupervisorLastName the Last Name of the internship supervisor
 * @param internshipSupervisorFirstName the First Name of the internship supervisor
 * @param internshipSupervisorEmail the Email of the internship supervisor
 * @param internshipProject the internship project
 * @param signatureDate the signature Date
 * @param academicYear the academicYear
 * @returns {string} the form for the stage 
 */
const formStage = (tradeNameCompany, designationCompany, internshipSupervisorLastName, internshipSupervisorFirstName,
  internshipSupervisorEmail , internshipProject, signatureDate, academicYear, internshipSupervisorPhone, stageId, version) => `

  <h2 style="margin-top:1.5%">Informations du Stage chez <strong>${tradeNameCompany} ${designationCompany || ""}</strong></h2> 

    <div style="display: inline-block; margin: 0 1% 0 0; width: 49%; vertical-align: top;">
        <article id="allInfoOfOne">
            <div class="header">
                <h3> Informations du Stage</h3>
            </div>    
            
            <div>
                <form id="personalInfo">
                    <div>

                        <div class="row">
                            <div class="col">
                                <h5 style="margin: 5% 0 0 2%;"><strong>Sujet du Stage :</strong></h5>
                            </div>
                            <div class="col" style="margin: 2% 0 0 5%; font-size:1.2rem;">
                                ${internshipProject !== "" ? internshipProject : "Aucun sujet pour le moment"}
                                <button id="ChangeStageSubject" type="button" class="btn btn-primary" data-stage-id=${stageId} data-stage-version=${version} style="margin-left: 20px;">Modifier</button>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col">
                                <h5 style=" margin: 5% 0 0 2%;"><strong>Date signature de la Convention :</strong></h5>
                            </div>
                            <div class="col" style="margin: 2% 0 0 5%; font-size:1.2rem;">
                                ${signatureDate}
                            </div>
                        </div>
                            
                        <div class="row">
                            <div class="col">
                                <h5 style=" margin: 5% 0 0 2%;"><strong>Année Académique : </strong></h5>
                            </div>
                            <div class="col" style="margin: 2% 0 0 5%; font-size:1.2rem;">
                                ${academicYear}
                            </div>
                        </div>
                    </div>    
                </form>
            </div>
        </article>  
    </div>  

    <div style="display: inline-block; width: 49%; vertical-align: top;">
        <article id="allInfoOfOne">
            <div class="header">
                <h3>Responsable de Stage</h3>
            </div>    
            
            <div>
                <form id="personalInfo">
                    <div>
                        <div class="row">
                            <div class="col">
                                <h5 style=" margin: 5% 0 0 2%;"><strong>Nom : </strong></h5>
                            </div>
                            <div class="col" style="margin: 2% 0 0 1%; font-size:1.2rem;">
                                ${internshipSupervisorLastName}
                            </div>
                        </div>

                        <div class="row">
                            <div class="col">
                                <h5 style=" margin: 5% 0 0 2%;"><strong>Prénom : </strong></h5>
                            </div>
                            <div class="col" style="margin: 2% 0 0 1%; font-size:1.2rem;">
                                ${internshipSupervisorFirstName}
                            </div>
                        </div>

                        <div class="row">
                            <div class="col">
                                <h5 style=" margin: 5% 0 0 2%;"><strong>Émail : </strong></h5>
                            </div>
                            <div class="col" style="margin: 2% 0 0 1%; font-size:1.2rem;">
                                ${internshipSupervisorEmail != null ? internshipSupervisorEmail : 'ne possède pas d\'email'}
                            </div>
                        </div>

                        <div class="row">
                            <div class="col">
                                <h5 style=" margin: 5% 0 0 2%;"><strong>Numéro de téléphone : </strong></h5>
                            </div>
                            <div class="col" style="margin: 2% 0 0 1%; font-size:1.2rem;">
                                ${internshipSupervisorPhone != null ? internshipSupervisorPhone : 'ne possède pas de numéro de téléphone'}
                            </div>
                        </div>
                        
                    </div>    
                </form>
            </div>
        </article>  
    </div> 
        </article>  
    </div> 
`;


module.exports = formStage;





