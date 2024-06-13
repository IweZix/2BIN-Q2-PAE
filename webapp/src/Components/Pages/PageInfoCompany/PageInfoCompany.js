import {Modal} from "bootstrap";
import {
  getCompanyById, getAllContactForTheCompany, blacklistedCompanies
} from '../HomePage/Requests/Teacher/Teacher';
import {getAuthenticatedUser} from '../../../utils/auths'
import {clearPage} from "../../../utils/render";
import Navigate from "../../Router/Navigate";
import address from "../../../img/address.png";
import phone from "../../../img/phone.png";
import email from "../../../img/email.png";
import blocked from "../../../img/block.png";
import accepted from "../../../img/accepted.png";
import refused from "../../../img/refused.png";
import started from "../../../img/started.png";
import onHold from "../../../img/onHold.png";
import admitted from "../../../img/admitted.png";
import Unsupervised from "../../../img/Unsupervised.png";


const HomePageListAllInformationForOneCompany = async (id) => {
  clearPage();

  const main = document.querySelector('main');
      main.innerHTML = `
        <div id="informationsForOneCompany"></div>
        <br>
        <div id="tableAllContact"></div>
      `;
      await DisplayformOneCompany(id);
      await DisplayAllContactFormOneCompany(id);
};

async function DisplayformOneCompany(number) {
  const company = await getCompanyById(number);
  const informationsForOneCompany = document.getElementById("informationsForOneCompany");

  let blackList;
  if (company.blacklist) {
    blackList = "Entreprise blacklistée";
  }else {
    blackList = `<button type="button" class="btn btn-primary" id="blacklisterCompany" data-company-id="${company.companyId}" data-company-version="${company.version}">blacklister</button>`;
  }

  informationsForOneCompany.innerHTML = `
    <div id ="informationsForOneCompany">
      <article id="allInfoOfOne">
        <div id="tableauCompany" class="header">
          <h2 id="nameCompanyTitle"></h2>
          <div class="d-flex justify-content-end">
            <p style="font-weight: bold; color: red;">${blackList}</p>
          </div>  
        </div> 
  
        <div>
          <ul>
            <div class="infos">
              <img src="${address}" class="info_image">
              <div>${company.address}</div>
            </div>
        
            <div class="infos">
              <img src="${phone}" class="info_image">
              <div id="phoneNumber">${company.phoneNumber ? company.phoneNumber : ""}</div>
            </div>

            <div class="infos">
              <img src="${email}" class="info_image">
              <div id="email">${company.email ? company.email : ""}</div>
            </div>

            <div class="infos">
              <img src="${blocked}" class="info_image">
              <div id="blackList">${company.motivationBlacklist ? company.motivationBlacklist : ""}</div>
            </div>
          </ul>
        </div>
      </article>
    </div>`;

  document.querySelector("#nameCompanyTitle").innerText = `${company.tradeName} ${company.designation ? company.designation : ""}`;


  const phoneNumberLi = document.getElementById("phoneNumber");
  if (!company.phoneNumber) {
    phoneNumberLi.parentNode.style.display = "none";
  }

  const emailLi = document.getElementById("email");
  if (!company.email) {
    emailLi.parentNode.style.display = "none";
  }

  const blackListLi = document.getElementById("blackList");
  if (!company.motivationBlacklist) {
    blackListLi.parentNode.style.display = "none";
  }
  if (!company.blacklist) {
    attachBlacklistEventListeners();
  }
 
}

async function DisplayAllContactFormOneCompany(id) {

  const table = document.getElementById("tableAllContact");
  const contacts = await getAllContactForTheCompany(id);
  // HERE

  let tableHTML = '';
  
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
        <td scope="row">${contact.studentName}</td>
        <td scope="row">${contact.studentFirstName}</td>
        <td scope="row">${contact.studentEmail}</td>
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
            <th scope="col">Nom étudiant</th>
            <th scope="col">Prénom étudiant</th>
            <th scope="col">Email étudiant</th>
            <th scope="col">État du contact</th>
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

function attachBlacklistEventListeners() {
  const table = document.querySelector('#blacklisterCompany');
  table.addEventListener('click', async (event) => {
    const user = await getAuthenticatedUser();
    const {companyId} = event.target.dataset;
    const {companyVersion} = event.target.dataset;

    // modal pour demander la raison
    const modalHtml = `
              <div class="modal" id="declineModal">
                  <div class="modal-dialog">
                      <div class="modal-content">
                          <div class="modal-header">
                              <h5 class="modal-title">Decline Reason</h5>
                              <button type="button" class="close" data-dismiss="modal">&times;</button>
                          </div>
                          <div class="modal-body">
                              <input type="text" id="declineReason" placeholder="Enter decline reason">
                          </div>
                          <div class="modal-footer">
                              <button type="button" class="btn btn-primary" id="submitDecline">Submit</button>
                          </div>
                      </div>
                  </div>
              </div>
          `;
    document.body.insertAdjacentHTML('beforeend', modalHtml);
    const declineModal = new Modal(document.getElementById('declineModal'));
    declineModal.show();
    document.querySelector('#declineModal .close').addEventListener('click',
        () => {
          declineModal.hide();
        });

    document.querySelector('#submitDecline').addEventListener('click',
        async () => {
          const reason = document.querySelector('#declineReason').value;
          const response = await blacklistedCompanies(companyId,
              user.user.userId, reason, companyVersion);
          document.querySelector('#declineReason').value = '';
          if (response) {
            declineModal.hide();
            await HomePageListAllInformationForOneCompany(companyId);

          } else {
            declineModal.hide();
            Navigate('/403');
          }
        });
  });
}


export default HomePageListAllInformationForOneCompany;