import { getUserById} from '../HomePage/HomePageMethods';
import { getAllContactAndInforStudent, getStageForTheStudent } from '../HomePage/Requests/Teacher/Teacher';
import {clearPage} from "../../../utils/render";
import phone from "../../../img/phone.png";
import email from "../../../img/email.png";
import year from "../../../img/calendar.png";
import accepted from "../../../img/accepted.png";
import refused from "../../../img/refused.png";
import started from "../../../img/started.png";
import onHold from "../../../img/onHold.png";
import admitted from "../../../img/admitted.png";
import Unsupervised from "../../../img/Unsupervised.png";



const HomePageListAllInformationForOneStudent = async (id) => {
  clearPage();

  const main = document.querySelector('main');
      main.innerHTML = `
        <div id="informationsForOneStudent"></div>
        <br>
        <div id="tableAllStudent"></div>
        <br>
        <div id="tableInternship"></div>
      `;
      await DisplayformOneStudent(id);
      await DisplayAllContactFormOneStudent(id);
      await DisplayAllInformationForInternship(id);
};

async function DisplayformOneStudent(id) {
  const student = await getUserById(id);
  const informationsForOneStudent= document.getElementById("informationsForOneStudent");


  informationsForOneStudent.innerHTML = `
    <div id ="informationsForOneStudent">
      <article id="allInfoOfOne">
        <div id="tableauStudent" class="header">
          <h2 id="nameStudentTitle"></h2>
          <div class="d-flex justify-content-end">
          </div>  
        </div> 
  
        <div>
          <ul>
            <div class="infos">
              <img src="${phone}" class="info_image">
              <div id="phoneNumber">${student.phone}</div>
            </div>

            <div class="infos">
              <img src="${email}" class="info_image">
              <div id="email">${student.email}</div>
            </div>

            <div class="infos">
              <img src="${year}" class="info_image">
              <div id="blackList">${student.academicYear}</div>
            </div>
          </ul>
        </div>
      </article>
    </div>`;

  document.querySelector("#nameStudentTitle").innerText = `${student.firstName} ${student.lastName}`;
 
}

async function DisplayAllContactFormOneStudent(id) {

  const table = document.getElementById("tableAllStudent");
  const contacts = await getAllContactAndInforStudent(id);

  let tableHTML = '';
  
  if (contacts.length === 0) {
    table.innerHTML = `<p style="text-align: center;">L'étudiant n'a pas de contact.</p>`;
  }else {
contacts.forEach(contact => {

    let contactStatusStyle = '';
   let statusIcon = '';
   if (contact.contactStatus === "accepte") {
      contactStatusStyle = 'font-weight:bold; color:green;';
      statusIcon = `<img src="${accepted}" alt="Accepté" style="width: 20px; height: 20px;"/>`;
    }
    if (contact.contactStatus === "refuse") {
      contactStatusStyle = 'font-weight:bold; color:red;';
      statusIcon = `<img src="${refused}" alt="Refusé" style="width: 20px; height: 20px;"/>`;
    }
    if (contact.contactStatus === "suspendu") {
      contactStatusStyle = 'font-weight:bold; color:#404040;';
      statusIcon = `<img src="${onHold}" alt="Refusé" style="width: 20px; height: 20px;"/>`;
    }
    if (contact.contactStatus === "initie") {
      contactStatusStyle = 'font-weight:bold; color: #5167f0;';
      statusIcon = `<img src="${started}" alt="Initié" style="width: 20px; height: 20px;"/>`;
    }
    if (contact.contactStatus === "pris") {
      contactStatusStyle = 'font-weight:bold; color: #f0a254;';
      statusIcon = `<img src="${admitted}" alt="Pris" style="width: 20px; height: 20px;"/>`;
    }
    if (contact.contactStatus === "plus suivi") {
      contactStatusStyle = 'font-weight:bold; color:#737272;';
      statusIcon = `<img src="${Unsupervised}" alt="Plus_suivis" style="width: 20px; height: 20px;"/>`;
    }

    tableHTML += `
      <tr>
        <td scope="row">${contact.companyTradeName}</td>
        <td scope="row">${contact.companyDesignation ? contact.companyDesignation : "/"}</td>
        <td scope="row">${contact.companyPhone}</td>
        <td scope="row" style="${contactStatusStyle}">
          ${statusIcon || "" }  
          ${contact.contactStatus ? contact.contactStatus : ""}
        </td>
        <td scope="row">${contact.meetingType ? contact.meetingType : "/"}</td>
        <td scope="row">${contact.declineReason ? contact.declineReason : "/"}</td>
      </tr>`;

      
    
  });

  table.innerHTML = `
   <h2>Liste des contacts</h2>
    <div>
      <article id="tableAll">
      <table class="table">
        <thead>
          <tr>
            <th scope="col">Nom entreprise</th>
            <th scope="col">Appellation entreprise</th>
            <th scope="col">Téléphone</th>
            <th scope="col">État</th>
            <th scope="col">Type de rencontre</th>
            <th scope="col">Raison du refus</th>
          </tr>
        </thead>

        <tbody id="tableauContact">
          ${tableHTML} 
        </tbody>
      </table>
      </article>
    </div>`;
  }
  
}

async function DisplayAllInformationForInternship(id) {

  const table = document.getElementById("tableInternship");
  const stage = await getStageForTheStudent(id);
  let tableHTML = '';

  if (stage.stageId <=0) {
    table.innerHTML ='';
  }else {
    let date = new Date(stage.signatureDate);
    date = date.toLocaleDateString();
  tableHTML = `

      <tr>
        <td scope="row">${stage.internshipProject}</td>
        <td scope="row">${stage.tradeNameCompany}</td>
        <td scope="row">${stage.academicYear}</td>
        <td scope="row">${stage.internshipSupervisorLastName}</td>
        <td scope="row">${stage.internshipSupervisorFirstName}</td>
        <td scope="row">${stage.internshipSupervisorPhone}</td>
        <td scope="row">${date}</td>
      </tr>`;

      
 

  table.innerHTML = `
  <h2>Informations du stage chez ${stage.tradeNameCompany}</h2>
    <div>
      <article id="tableAll">
      <table class="table">
        <thead>
          <tr>
            <th scope="col">Sujet du stage</th>
            <th scope="col">Nom entreprise</th>
            <th scope="col">Année académique</th>
            <th scope="col">Nom responsable Stage</th>
            <th scope="col">Prénom responsable Stage</th>
            <th scope="col">Téléphone Responsable</th>
            <th scope="col">Date Signature</th>
          </tr>
        </thead>

        <tbody id="tableauContact">
          ${tableHTML} 
        </tbody>
      </table>
      </article>
    </div>`;
  }
}




export default HomePageListAllInformationForOneStudent;