/* eslint-disable no-promise-executor-return */
// eslint-disable-next-line no-unused-vars
import {Modal} from 'bootstrap';
import Navigate from '../../Router/Navigate';
import {clearPage} from '../../../utils/render';
import Navbar from '../../Navbar/Navbar';
import formContact from '../../Forms/contact';
import formStage from '../../Forms/stage';
import {getAuthenticatedUser} from '../../../utils/auths';
import {
  accepteContact,
  createNewCompany,
  createStage,
  declineContact,
  getAllCompanys,
  getAllNotBlacklistedCompanys,
  getAllContacts,
  notFollowContact,
  startContact,
  takeContact,
  getStageInfo,
  changeInternshipProject,
  getAllInternshipSupervisor
} from './HomePageMethods';

import distance from '../../../img/distance.png';
import enEntreprise from '../../../img/placement.png';
import accepted from '../../../img/accepted.png';
import refused from "../../../img/refused.png";
import started from "../../../img/started.png";
import onHold from "../../../img/onHold.png";
import admitted from "../../../img/admitted.png";
import Unsupervised from "../../../img/Unsupervised.png";

const STUDENT = 'étudiant';

const STARTED = 'initie';
const ADMITTED = 'pris';

const form = `
    <section>
        <div>
            ${formContact}
        </div>
    </section>
    <section>
        <div id="stage">
        </div>
    </section>
`;

const HomePageStudent = async () => {
  clearPage();
  Navbar();

  const user = await getAuthenticatedUser();
  const role = user.user.utilisateurType;
  if (role !== STUDENT) {
    Navigate('/403');
  } else {
    await HomePageStudentListener();
    await ModalContact();
  }
};

async function HomePageStudentListener() {
  const main = document.querySelector("main");
  main.innerHTML = form;
  const tableBody = document.querySelector("#tableau");

  const contacts = await getAllContacts();

  const stage = await getStageInfo();
  if (stage.stage.stageId === 0) {
    const stageHTML = document.getElementById('stage')
    stageHTML.style.textAlign = 'center';
    stageHTML.style.marginTop = '2%';
    stageHTML.innerHTML = 'Aucun stage accepté pour le moment.';
  }
  else {
    const stages = stage.stage;
    let date = new Date(stages.signatureDate);
    date = date.toLocaleDateString();
    const stageInfo = formStage(stages.tradeNameCompany, stages.designationCompany, stages.internshipSupervisorLastName,
        stages.internshipSupervisorFirstName, stages.internshipSupervisorEmail,
        stages.internshipProject, date, stages.academicYear, stages.internshipSupervisorPhone, stages.stageId, stages.version);
    document.getElementById('stage').innerHTML = stageInfo;
  }
  if (contacts.length === 0) {
    const rowNoData = document.createElement('tr');
    const cellNoData = document.createElement('td');
    cellNoData.textContent = 'Aucun contact pour le moment.';
    cellNoData.colSpan = 8;
    cellNoData.style.textAlign = 'center'; 
    rowNoData.appendChild(cellNoData);
    tableBody.appendChild(rowNoData);
  } else {
    contacts.forEach((contact) => {

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


      let buttonsHTML = '';
      if (contact.contactStatus === STARTED) {
        buttonsHTML = `
                    <button class="btn btn-primary take" id="tale" data-contact-id="${contact.companyId}" data-contact-version="${contact.version}">Prendre</button>
                    <button class="btn btn-primary notFollow" id="notFollow" data-contact-id="${contact.companyId}" data-contact-version="${contact.version}">Plus suivis</button>
                `;
      } else if (contact.contactStatus === ADMITTED) {
        buttonsHTML = `
                    <button class="btn btn-primary notFollow" id="notFollow" data-contact-id="${contact.companyId}" data-contact-version="${contact.version}">Plus suivis</button>
                    <button class="btn btn-primary accept" id="accept" data-contact-id="${contact.companyId}" data-contact-version="${contact.version}" data-contact-name="${contact.companyTradeName}" data-contact-tradeNameDesignation="${contact.companyTradeNameDesignation}">Accepter</button>
                    <button class="btn btn-primary decline" id="decline" data-contact-id="${contact.companyId}" data-contact-version="${contact.version}">Refuser</button>
                `;
      }

      tableBody.innerHTML += `
                <tr>
                    <td scope="row">${contact.companyTradeName}</td>
                    <td scope="row">${contact.companyDesignation ? contact.companyDesignation : "/"}</td>
                    <td scope="row">${contact.companyPhone ? contact.companyPhone : "/"}</td>
                    <td scope="row" style="${contactStatusStyle}">
                        ${statusIcon || "" }
                        ${contact.contactStatus}
                    </td>
                    <td scope="row">${contact.meetingType ? contact.meetingType : "/"}</td>
                    <td scope="row">${contact.declineReason ? contact.declineReason : "/"}</td>
                    <td scope="row">${buttonsHTML}</td>
                </tr>
            `;
    });
  }
  const takeButtons = document.querySelectorAll('.take');
  const notFollowButtons = document.querySelectorAll('.notFollow');
  const acceptButtons = document.querySelectorAll('.accept');
  const declineButtons = document.querySelectorAll('.decline');

  takeButtons.forEach((button) => {
    button.addEventListener('click', async (e) => {
      e.preventDefault();
      const {contactId} = e.target.dataset;
      const {contactVersion} = e.target.dataset;
      // Create a modal with two buttons for the meeting options
      const modalHtml = `
            <div class="modal" id="meetingModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Choisissez le lieu de rencontre </h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body" id="but">
                            <button class="neumorphic" id="remoteMeeting" data-contact-id="${contactId}" data-contact-version="${contactVersion}">
                                <img src="${distance}">
                                <span>À distance</span>
                            </button>
                            <button class="neumorphic" id="inPersonMeeting" data-contact-id="${contactId}" data-contact-version="${contactVersion}">
                                <img src="${enEntreprise}">
                                <span>En entreprise</span>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        `;
      document.body.insertAdjacentHTML('beforeend', modalHtml);
      // Show the modal
      const meetingModal = new Modal(document.getElementById('meetingModal'));
      meetingModal.show();
      // Get the buttons
      const remoteMeetingButton = document.getElementById('remoteMeeting');
      const inPersonMeetingButton = document.getElementById('inPersonMeeting');

      // Add event listeners to the buttons
      const remoteMeetingListener = async () => {
        await takeContact(contactId, "distance", contactVersion);
        await new Promise(resolve => setTimeout(resolve, 100));
        meetingModal.hide();
        await HomePageStudentListener();
        await ModalContact();
      };
      const inPersonMeetingListener = async () => {
        await takeContact(contactId, "en entreprise", contactVersion);
        await new Promise(resolve => setTimeout(resolve, 100));
        meetingModal.hide();
        await HomePageStudentListener();
        await ModalContact();
      };
      remoteMeetingButton.addEventListener('click', remoteMeetingListener);
      inPersonMeetingButton.addEventListener('click', inPersonMeetingListener);
      document.querySelector('#meetingModal .btn-close').addEventListener('click',
          () => {
            meetingModal.hide();
            remoteMeetingButton.removeEventListener('click',
                remoteMeetingListener);
            inPersonMeetingButton.removeEventListener('click',
                inPersonMeetingListener);
          });
      // Add event listeners to the meeting option buttons
      document.getElementById('remoteMeeting').addEventListener('click', () => {
        remoteMeetingButton.removeEventListener('click', remoteMeetingListener);
        inPersonMeetingButton.removeEventListener('click',
            inPersonMeetingListener);
      });
      document.getElementById('inPersonMeeting').addEventListener('click',
          () => {
            remoteMeetingButton.removeEventListener('click',
                remoteMeetingListener);
            inPersonMeetingButton.removeEventListener('click',
                inPersonMeetingListener);
          });
    });
  });

  notFollowButtons.forEach((button) => {
    button.addEventListener('click', async (e) => {
      e.preventDefault();
      const {dataset: {contactId}} = e.target;
      const {contactVersion} = e.target.dataset;
      const result = await notFollowContact(contactId, contactVersion);
      if (result) {
        await new Promise(resolve => setTimeout(resolve, 100));
        await HomePageStudentListener();
        await ModalContact();
      } else {
        alert('Une erreur est survenue');
      }
    });
  });

  async function openAcceptModal(contactName, contactId, contactVersion, companyTradeNameDesignation) {
    const modalStage = `
            <div class="modal" id="stageModal" tabindex="-1" role="dialog">
                <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Création Du Stage</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="form-group mb-3">
                                <label for="stageCompany" class="h5">Nom de l'entreprise</label>
                                <input type="text" class="form-control" id="stageCompany" value="${companyTradeNameDesignation}" placeholder="${companyTradeNameDesignation}" data-contact-id="${contactId}" data-contact-version="${contactVersion}" data-contact-name="${contactName}" data-contact-companyTradeNameDesignation="${companyTradeNameDesignation}" readonly>
                            </div>
                            <div class="form-group mb-3">
                                <label for="stageResp" class="h5">Responsable du stage</label>
                                <input type="text" list="internshipSupervisorOptions" class="form-control" id="stageResp" placeholder="Responsable du stage">
                                <datalist id="internshipSupervisorOptions"></datalist>
                                <button data-bs-dismiss="modal" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalInternshipSupervisorForm" style="margin-top: 5px">Ajouter un responsable</button>
                            </div>
                            <div class="form-group mb-3">
                                <label for="stageSubject" class="h5">Sujet du stage</label>
                                <input type="text" class="form-control" id="stageSubject" placeholder="Sujet du stage">
                            </div>
                            <div class="form-group mb-3">
                                <label for="stageDate" class="h5">Date de Signature Convention</label>
                                <input type="date" class="form-control" id="stageDate" placeholder="Date de Signature">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" id="submitStage">Enregistrer</button>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="modal fade modal-lg" id="modalInternshipSupervisorForm" data-bs-backdrop="static" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="exampleModalLabel">Ajouter un responsable</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body w-100">
                            <div class="row justify-content-between">
                                <div id="modalBody-title" class="col-sm-4 col-form-label mt-2">
                                    <label for="internshipSupervisorName">Nom du responsable *:</label>
                                </div>
                                <div id="modalBody-input" class="col-sm-8 col-form-label">
                                    <input type="text" class="form-control mb-3" id="internshipSupervisorLastName" placeholder="Nom du responsable" required>
                                </div>
                            </div>
                           <div class="row justify-content-between">
                                <div id="modalBody-title" class="col-sm-4 col-form-label mt-2">
                                    <label for="internshipSupervisorLastName">Prénom du responsable *:</label>
                                </div>
                                <div id="modalBody-input" class="col-sm-8 col-form-label">
                                    <input type="text" class="form-control mb-3" id="internshipSupervisorFirstName" placeholder="Prénom du responsable" required>
                                </div>
                            </div>
                            <div class="row justify-content-between">
                                <div id="modalBody-title" class="col-sm-4 col-form-label mt-2">
                                    <label for="phone">Numéro de téléphone *:</label>
                                </div>
                                <div id="modalBody-input" class="col-sm-8 col-form-label">
                                    <input type="text" class="form-control mb-3" id="phone" placeholder="Numéro de télephone" required>
                                </div>
                            </div>
                            <div class="row justify-content-between">
                                <div id="modalBody-title" class="col-sm-4 col-form-label mt-2">
                                    <label for="internshipSupervisorEmail">Email *:</label>
                                </div>
                                <div id="modalBody-input" class="col-sm-8 col-form-label">
                                    <input type="email" class="form-control mb-3" id="internshipSupervisorEmail" placeholder="Email" required>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <div class="col" style="text-align:left;">
                                <p>* Champs obligatoires</p> 
                            </div>

                            <div>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fermer</button>
                            <button id="addInternshipSupervisor" type="button" class="btn btn-primary">Ajouter le responsable</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    `;

    document.body.insertAdjacentHTML('beforeEnd', modalStage);
    const stageModal = new Modal(document.getElementById('stageModal'));
    stageModal.show();

    const internshipsupervisors = await getAllInternshipSupervisor(contactId);
    if (internshipsupervisors) {
        internshipsupervisors.sort((a, b) => {
            a.lastname.localeCompare(b.lastname);
            a.firstname.localeCompare(b.firstname);
            return a.lastname.localeCompare(b.lastname);
        });
        
        internshipsupervisors.forEach((internshipSup) => {
            const option = document.createElement('option');
            option.value = `${internshipSup.lastname}_${internshipSup.firstname}`;
            document.getElementById('internshipSupervisorOptions').appendChild(option);
        });
    }

    // Add event listener to the submit button
    document.getElementById('submitStage').addEventListener('click',
        async () => {
          const tradeNameCompany = document.getElementById(
              'stageCompany').value;
          const internshipSupervisorName = document.getElementById(
              'stageResp').value;
              const sliptIntershipSupervisor = internshipSupervisorName.split('_');
              const internshipSupervisorLastName = sliptIntershipSupervisor[0];
              const internshipSupervisorFirstName = sliptIntershipSupervisor[1];
          const internshipProject = document.getElementById(
              'stageSubject').value;
          const dateSignature = document.getElementById('stageDate').value;
          const result = await createStage(tradeNameCompany,
              internshipSupervisorLastName, internshipSupervisorFirstName, internshipProject, dateSignature);
          if (result) {
            await accepteContact(contactId, contactVersion);
            await new Promise(resolve => setTimeout(resolve, 100));
            await HomePageStudentListener();
            await ModalContact();
            stageModal.hide();
            document.getElementById('stageCompany').value = '';
            document.getElementById('stageResp').value = '';
            document.getElementById('stageSubject').value = '';
            document.getElementById('stageDate').value = '';
          } else {
            alert('Une erreur est survenue');
          }
        });
    document.querySelector('#stageModal .btn-close').addEventListener('click',
        () => {
          document.getElementById('stageModal').remove();
          stageModal.hide();
        });

    document.getElementById('addInternshipSupervisor').addEventListener('click', async () => {
      const internshipSupervisorFirstName = document.getElementById('internshipSupervisorFirstName').value;
      const internshipSupervisorLastName = document.getElementById('internshipSupervisorLastName').value;
      const phone = document.getElementById('phone').value;
      const internshipSupervisorEmail = document.getElementById('internshipSupervisorEmail').value;

      if (!internshipSupervisorFirstName || !internshipSupervisorLastName || !phone || !internshipSupervisorEmail) {
        const errorMessage = document.createElement('p');
        errorMessage.textContent = 'Veuillez remplir tous les champs obligatoires, ceux marqués par *';
        errorMessage.style.color = 'red';
        document.getElementById('modalInternshipSupervisorForm').querySelector(
            '.modal-body').appendChild(errorMessage);
        return;
      }
      // Create a new internship supervisor
      const request = await fetch('/api/internshipsupervisor/create', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem('token')
              || sessionStorage.getItem('token'),
        },
        body: JSON.stringify({
          "lastname": internshipSupervisorLastName,
          "firstname": internshipSupervisorFirstName,
          "email": internshipSupervisorEmail,
          "phone": phone,
          "company": contactId,
        }),
      });
      
      if (request.ok) {
        
        // close modal et open previous modal
        const option = document.createElement('option');
        option.value = `${internshipSupervisorLastName}_${internshipSupervisorFirstName}`;
        document.getElementById('internshipSupervisorOptions').appendChild(option);
        document.getElementById('modalInternshipSupervisorForm').remove();
        document.querySelector('.modal-backdrop').remove();
        document.getElementById('stageResp').value = `${internshipSupervisorLastName}_${internshipSupervisorFirstName}`;
        stageModal.show();

      } else {
        alert('Une erreur est survenue');
      }
    });
  }

  acceptButtons.forEach((button) => {
    button.addEventListener('click', (e) => {
      e.preventDefault();
      const {contactId, contactVersion, contactName, contactTradenamedesignation} = button.dataset;
      openAcceptModal(contactName, contactId, contactVersion, contactTradenamedesignation);
    });
  });

  declineButtons.forEach((button) => {
    button.addEventListener('click', async (e) => {
      e.preventDefault();
      const {dataset: {contactId}} = e.target;
      const {contactVersion} = e.target.dataset;
      const modalHtml = `
            <div class="modal" id="declineModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Raison du refus</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <textarea rows="5" id="declineReason" placeholder="Entrer la raison du refus" style="width:100%;"></textarea>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" id="submitDecline">Enregistrer</button>
                        </div>
                    </div>
                </div>
            </div>
        `;
      document.body.insertAdjacentHTML('beforeend', modalHtml);
      const declineModal = new Modal(document.getElementById('declineModal'));
      declineModal.show();
      document.querySelector('#declineModal .btn-close').addEventListener('click',
          () => {
            declineModal.hide();
          });

      // Define the submitDeclineListener function before using it
      const submitDeclineListener = async () => {
        const declineReason = document.getElementById('declineReason').value;
        const result = await declineContact(contactId, declineReason,
            contactVersion);
        if (result) {
          await new Promise(resolve => setTimeout(resolve, 100));
          declineModal.hide();
          await HomePageStudentListener();
          await ModalContact();
        } else {
          const errorMessage = document.createElement('p');
          errorMessage.textContent = 'Attention il y a un probleme.';
          document.getElementById('declineModal').querySelector(
              '.modal-body').appendChild(errorMessage);
        }
        // Remove the event listener after submitting the decline reason
        document.getElementById('submitDecline').removeEventListener('click',
            submitDeclineListener);
      };

      // Add the event listener using the named function
      document.getElementById('submitDecline').addEventListener('click',
          submitDeclineListener);
    });
  });

  if (stage.stage.stageId !== 0) {
    document.getElementById('ChangeStageSubject').addEventListener('click', () => {
        const modalStageInfo = `
            <div class="modal" id="internshipProjectModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Sujet du stage</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <input type="text" class="form-control" id="internshipProjectinput" placeholder="Sujet du stage">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" id="submitInternshipProject">Enregistrer</button>
                        </div>
                    </div>
                </div>
            </div>
        `;
        document.body.insertAdjacentHTML('beforeend', modalStageInfo);
        const internshipProjectModal = new Modal(document.getElementById('internshipProjectModal'));
        internshipProjectModal.show();
        document.getElementById('submitInternshipProject').addEventListener('click', async () => {
            const internshipProject = document.getElementById('internshipProjectinput').value;
            const button = document.getElementById('ChangeStageSubject');
            const dataIdStage = button.getAttribute('data-stage-id');
            const dataVersion = button.getAttribute('data-stage-version');
          console.log(dataIdStage)
            const result = await changeInternshipProject(internshipProject, dataIdStage, dataVersion);
            if (result) {
                await HomePageStudentListener();
                internshipProjectModal.hide();
                document.getElementById('internshipProjectinput').value = '';
            } else {
                alert('Une erreur est survenue lors du submit du sujet du stage');
            }
        });
        document.querySelector('#internshipProjectModal .btn-close').addEventListener('click', () => {
            document.getElementById('internshipProjectModal').remove();
            internshipProjectModal.hide();
        });
    });

  }
}

async function ModalContact() {
  const contacts = await getAllContacts();
  const acceptedContacts = contacts.filter(
      (contact) => contact.contactStatus === "accepte");
  if (acceptedContacts.length === 0) {
    const modalContact = `
      <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addContact">Ajouter un contact</button>
      
          <div class="modal fade modal-lg" id="addContact" data-bs-backdrop="static" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
              <div class="modal-dialog modal-dialog-centered" role="document">
                  <div class="modal-content">
                      <div class="modal-header">
                          <h1 class="modal-title fs-5" id="exampleModalLabel">Ajouter un contact</h1>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>
                      <div class="modal-body w-100">
                          <div class="row justify-content-between">
                              <div id="modalBody-title" class="col-sm-4 mt-1 d-flex align-items-center justify-content-center" style="margin-top:1%;">
                                  <label >Nom entreprise : </label>
                              </div>   
                              <div id="modalBody-input" class="col-sm-8 col-form-label" style="display: flex; align-items: center;">    
                                  <input class="form-control" list="companyOptions" id="companyInput" placeholder="Sélectionner une entreprise...">
                                  <datalist id="companyOptions"> </datalist>
                                  <button data-bs-dismiss="modal" type="button" data-bs-toggle="modal" data-bs-target="#modalCompanyForm" style="margin-left: 5px; color: grey; font-weight: bold; background-color: transparent; border: none; font-size: 2em; display: flex; align-items: center; justify-content: space-between;padding-bottom: 15px;">+</button>
                              </div>
                          </div> 
                      </div>
                      <div class="modal-footer">
                          <div>    
                              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fermer</button>
                              <button id="addContactButton" type="button" class="btn btn-primary">Ajouter</button>
                          </div>
                      </div>
                      <div>
                          <p id="errorMessageCompanyNull" class="text-danger"></p>
                          <p id="errorMessageCompanyAlreadyContacted" class="text-danger"></p>
                      </div>
                  </div>
              </div>
          </div>           
  
          <div class="modal fade modal-lg" id="modalCompanyForm" data-bs-backdrop="static" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
              <div class="modal-dialog modal-dialog-centered" role="document">
                  <div class="modal-content">
                      <div class="modal-header">
                          <h1 class="modal-title fs-5" id="exampleModalLabel">Ajouter une entreprise</h1>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>
                      <div class="modal-body w-100">
                          <div class="row justify-content-between">
                              <div id="modalBody-title" class="col-sm-4 col-form-label mt-2">
                                  <label for="tradeName">Nom entreprise *:</label>
                              </div>
                              <div id="modalBody-input" class="col-sm-8 col-form-label">
                                  <input type="text" class="form-control mb-3" id="tradeName" placeholder="Nom entreprise" required>
                              </div>
                          </div>
                          <div class="row justify-content-between">
                              <div id="modalBody-title" class="col-sm-4 col-form-label mt-2">
                                  <label for="appellation">Appellation :</label>
                              </div>
                              <div id="modalBody-input" class="col-sm-8 col-form-label">
                                  <input type="text" class="form-control mb-3" id="appellation" placeholder="Appellation">
                              </div>
                          </div>
                          <div class="row justify-content-between">
                              <div id="modalBody-title" class="col-sm-4 col-form-label mt-2">
                                  <label for="address">Adresse *:</label>
                              </div>
                              <div id="modalBody-input" class="col-sm-8 col-form-label">
                                  <input type="text" class="form-control mb-3" id="address" placeholder="Adresse" required>
                              </div>
                          </div>
                          <div class="row justify-content-between">
                              <div id="modalBody-title" class="col-sm-4 col-form-label mt-2">
                                  <label for="telephone">Téléphone :</label>
                              </div>
                              <div id="modalBody-input" class="col-sm-8 col-form-label">
                                  <input type="text" class="form-control mb-3" id="telephone" placeholder="Téléphone">
                              </div>
                          </div>
                          <div class="row justify-content-between">
                              <div id="modalBody-title" class="col-sm-4 col-form-label mt-2">
                                  <label for="email">Email :</label>
                              </div>
                              <div id="modalBody-input" class="col-sm-8 col-form-label">
                                  <input type="email" class="form-control mb-3" id="email" placeholder="Email">
                              </div>
                          </div>
                      </div>
                      <div class="modal-footer">
                        <div class="col" style="text-align:left;">
                            <p>* Champs obligatoires</p> 
                        </div>
                        <div>
                          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fermer</button>
                          <button id="addCompanyButton" type="button" class="btn btn-primary">Ajouter l'entreprise</button>
                        </div>
                      </div>
                      <div>
                          <p id="errorMessageInfoRequired" class="text-danger"></p>
                      </div>
                  </div>
              </div>
          </div>        
       </div>          
   </div>`;

    const modal = document.getElementById('modalContactForm');
    modal.innerHTML = modalContact;

    const companies = await getAllNotBlacklistedCompanys();

    // const companyInput = document.getElementById('companyInput');
    const addContactButton = document.getElementById('addContactButton');

    if (companies) {
      companies.sort(
          (a, b) => a.tradeNameDesignation.localeCompare(b.tradeNameDesignation));

      companies.forEach((company) => {
        const option = document.createElement('option');
        option.value = company.tradeNameDesignation;
        document.getElementById('companyOptions').appendChild(option);
      });
    }

    const addContactModal = new Modal(document.getElementById('addContact'));

    addContactButton.addEventListener('click', async (e) => {
      e.preventDefault();

      const companyName = document.getElementById('companyInput').value;

      if (!companyName) {
        const errorMessage = document.getElementById('errorMessageCompanyNull');
        errorMessage.innerText = "Veuillez sélectionner une entreprise.";
        return;
    }

      const listCompanys = await getAllNotBlacklistedCompanys();
      const selectedCompany = listCompanys.find(
          company => company.tradeNameDesignation === companyName);

      
      const alreadyContacted = await getAllContacts();
      const isAlreadyContacted = alreadyContacted.some(contact => contact.companyId === selectedCompany.companyId);

      if (isAlreadyContacted) {
        const errorMessage = document.getElementById('errorMessageCompanyAlreadyContacted');
        errorMessage.innerText = "Cette entreprise a déjà été contactée.";
      }else {
        const succes = await startContact(selectedCompany.companyId);
      if (succes) {
        addContactModal.hide();
        HomePageStudent();
      } else {
        alert('Erreur lors de la création du contact');
      }
      }
      
    });

    const addCompanyButton = document.getElementById('addCompanyButton');

    const addCompanyModal = new Modal(
        document.getElementById('modalCompanyForm'));

    addCompanyButton.addEventListener('click', async () => {

      const tradeName = document.getElementById('tradeName').value;
      const appellation = document.getElementById('appellation').value;
      const address = document.getElementById('address').value;
      const telephone = document.getElementById('telephone').value;
      const email = document.getElementById('email').value;

      if (!tradeName || !address) {
        const errorMessage = document.getElementById('errorMessageInfoRequired');
        errorMessage.innerText = "Veuillez remplir tous les champs munis d'une *";
        return;
      }

      if (!tradeName || !address) {
        const errorMessage = document.getElementById('errorMessageInfoRequired');
        errorMessage.innerText = "Veuillez remplir tous les champs munis d'une *";
        return;
      }
      let tradeNameAppellation = ``;
      if (!appellation) {
        tradeNameAppellation = `${tradeName}`;
      } else {
        tradeNameAppellation = `${tradeName}_${appellation}`;
      }

      await createNewCompany(tradeName, appellation, address, telephone, email);

      const update = await getAllCompanys();

      const theCompanySelected = update.find(
          company => company.tradeNameDesignation === tradeNameAppellation);          
    

      const succesful = await startContact(theCompanySelected.companyId);
      if (succesful) {
        addCompanyModal.hide();
        HomePageStudent();
      } else {
        alert('Erreur lors de la création de lentreprise');
      }
    });
  }
}

export default HomePageStudent;
