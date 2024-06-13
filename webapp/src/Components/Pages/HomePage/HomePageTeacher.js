/* eslint-disable arrow-body-style */
/* eslint-disable prefer-destructuring */
/* eslint-disable no-unused-vars */
import Chart from 'chart.js/auto';
import {Modal} from "bootstrap";
import {clearPage} from "../../../utils/render";
import {getAuthenticatedUser, isAuthenticated,} from '../../../utils/auths'
import Navigate from "../../Router/Navigate";
import {getAllCompanys} from "./HomePageMethods";
import {
  blacklistedCompanies,
  getListCompanies,
  getNbEtudiants
} from "./Requests/Teacher/Teacher";
import HomePageListAllInformationForOneCompany from '../PageInfoCompany/PageInfoCompany';
import orderBy from "../../../img/orderBy.png";

const TEACHER = 'professeur';

const HomePageTeacher = async () => {
  clearPage();

  if (!isAuthenticated()) {
    Navigate('/403');
  } else {
    const user = await getAuthenticatedUser();
    const role = user.user.utilisateurType;
    if (role !== TEACHER) {
      Navigate('/403');
    } else {
      const main = document.querySelector('main');
      main.innerHTML = `
          <div id="graph"></div>
          <br>
          <div id="table"></div>
        `;
      await GraphListener();
      // eslint-disable-next-line no-use-before-define
      await ListAllCompany();
    }
  }
};

async function GraphListener() {
  const graph = document.getElementById('graph')
  graph.innerHTML = `<h2>Nombres d'étudiants ayant trouvé un stage :</h2>`;

  const nbStudents = await getNbEtudiants();

  const studentWithStage = nbStudents.studentWithStage;
  const mapStudentWithStage = new Map(Object.entries(studentWithStage));

  const studentWithoutStage = nbStudents.studentWithoutStage;
  const mapStudentWithoutStage = new Map(Object.entries(studentWithoutStage));

  let years = new Set();
  Object.keys(studentWithStage).forEach(year => years.add(year));
  Object.keys(studentWithoutStage).forEach(year => years.add(year));
  years.add('aucune sélection');

  years = Array.from(years).sort().reverse();

  const selectYear = document.createElement('select');
  years.forEach(year => {
    const option = document.createElement('option');
    option.value = year;
    option.textContent = year;
    selectYear.appendChild(option);
  });
  graph.appendChild(selectYear);

  const ctx = document.createElement('canvas');
  graph.appendChild(ctx);
  let myChart = null;

  function updateChart(selectedYear) {
    if (myChart) {
      myChart.destroy();
    }

    let nbStuWith = mapStudentWithStage.get(selectedYear) || 0
    let nbStuWithout = mapStudentWithoutStage.get(selectedYear) - nbStuWith|| 0

    if (selectedYear === 'aucune sélection') {
      nbStuWith = Object.values(studentWithStage).reduce((acc, val) => acc + val, 0);
      nbStuWithout = Object.values(studentWithoutStage).reduce((acc, val) => acc + val, 0) - nbStuWith;
    }

    myChart = new Chart(ctx, {
      type: 'doughnut',
      data: {
        labels: [`Trouvé : ${nbStuWith || 0}`,
          `Non trouvé : ${nbStuWithout || 0}`],
        datasets: [{
          label: 'Nombre d\'étudiants',
          data: [nbStuWith, nbStuWithout],
          backgroundColor: [
            'rgba(75, 192, 192, 0.2)',
            'rgba(255, 99, 132, 0.2)',
          ],
          borderColor: [
            'rgba(75, 192, 192, 1)',
            'rgba(255, 99, 132, 1)',
          ],
          borderWidth: 1,
        }]
      },
      options: {
        aspectRatio: 4,
        width: 200,
        height: 200,
        layout: {
          padding: {
            left: 0,
            right: 0,
            top: 0,
            bottom: 0
          }
        },
        plugins: {
          legend: {
            position: 'bottom',
          }
        }
      }
    });
  }

  selectYear.addEventListener('change', (event) => {
    const selectedYear = event.target.value;
    updateChart(selectedYear);
  });

  updateChart(years[0]);
}

const handleOneCompany = () => {
  const companyElements = document.querySelectorAll('.companyNameOnly');
  companyElements.forEach(company => {
    company.addEventListener('click', async () => {
      await HomePageListAllInformationForOneCompany(
          company.getAttribute('idValueCompany'));
    });
  });
};

const renderCompany = (Allcompanys, companyByYear, selectedYear) => {
  const board = document.querySelector('#board');
  board.innerHTML = '';
  if (selectedYear !== 'aucune sélection') {
    companyByYear.forEach(company => {
      if (company.academicYear === selectedYear) {
        const blackList = company.blacklist ? 'Oui' : 'Non';
        const infoCompany = `
        <tr>
          <td class="companyNameOnly" idValueCompany="${company.companyId}">${company.tradeName}</td>
          <td>${company.designation !== undefined && company.designation !== null ? company.designation : "/"}</td>
          <td>${company.phoneNumber}</td>
          <td>${company.countStage}</td>
          <td>${blackList === 'Non'
            ? `<button type="button" class="btn btn-primary" id="blacklister" data-company-id="${company.companyId}">blacklister</button>`
            : blackList}</td>
  
        </tr>`;
        board.innerHTML += infoCompany;
      }
    });

  } else {
    Allcompanys.forEach(company => {
      const blackList = company.blacklist ? 'Oui' : 'Non';

      const infoCompany = `
        <tr>
          <td class="companyNameOnly" idValueCompany="${company.companyId}">${company.tradeName}</td>
          <td>${company.designation !== undefined && company.designation !== null ? company.designation : "/"}</td>
          <td>${company.phoneNumber}</td>
          <td>${company.countStage}</td>
          <td>${blackList === 'Non'
          ? `<button type="button" class="btn btn-primary" id="blacklister" data-company-id="${company.companyId}" data-company-version="${company.version}">blacklister</button>`
          : blackList}</td>
  
        </tr>`;
      board.innerHTML += infoCompany;
    });
  }
  
  handleOneCompany();
};
const populateYearSelect = (years) => {
  const selectYear = document.createElement('select');
  selectYear.add(new Option('aucune sélection', 'aucune sélection'));

  years.forEach(year => {
    const option = document.createElement('option');
    option.value = year;
    option.textContent = year;
    selectYear.appendChild(option);
  });

  const selectYearContainer = document.querySelector('#selectYearContainer');
  selectYearContainer.innerHTML = '';
  selectYearContainer.appendChild(selectYear);
};

let yearStage = 'aucune sélection';
let yearBlacklist = 'aucune sélection';
const handleYearChange = (Allcompanys, companyByYear) => {
  const selectedYear = document.querySelector('#selectYearContainer select');
  selectedYear.addEventListener('change', () => {
    const year = selectedYear.value;
    yearStage = year;
    yearBlacklist = year;
    renderCompany(Allcompanys, companyByYear, year);

  });
};

function attachBlacklistEventListeners() {
  const table = document.getElementById('board');
  table.addEventListener('click', async (event) => {
    if (!event.target.matches('#blacklister')) {
      return;
    }
    const user = await getAuthenticatedUser();
    const {companyId} = event.target.dataset;
    const {companyVersion} = event.target.dataset;
    let motivation;

    // modal pour demander la raison
    const modalHtml = `
              <div class="modal" id="declineModal">
                  <div class="modal-dialog">
                      <div class="modal-content">
                          <div class="modal-header">
                              <h5 class="modal-title">Motivation de votre décision</h5>
                              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                          </div>
                          <div class="modal-body">
                              <textarea rows="5" id="declineReason" placeholder="Entrer la motivation de votre décision" style="width:100%;"></textarea>
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

    document.querySelector('#submitDecline').addEventListener('click',
        async () => {
          const reason = document.querySelector('#declineReason').value;
          const response = await blacklistedCompanies(companyId,
              user.user.userId, reason, companyVersion);
          if (response) {
            declineModal.hide();

            // eslint-disable-next-line no-use-before-define
            await ListAllCompany();
            window.location.reload();
          } else {
            declineModal.hide();
            Navigate('/403');
          }
        });
  });
}

let compteurClickName = 0;

const handleOrderByName = (Allcompanys, companyByYear) => {
  const AllorderByName = [...Allcompanys];
  AllorderByName.sort((a, b) => b.tradeNameDesignation.toLowerCase().localeCompare(a.tradeNameDesignation.toLowerCase()));

  const companyByYearOrderByName = [...companyByYear];
  companyByYearOrderByName.sort((a, b) => b.tradeNameDesignation.toLowerCase().localeCompare(a.tradeNameDesignation.toLowerCase()));

  const iconName = document.getElementById('orderByName');
  iconName.addEventListener('click', () => {
    if (compteurClickName % 2 === 0) {
      renderCompany(AllorderByName, companyByYearOrderByName, yearStage);
    } else {
      renderCompany(Allcompanys, companyByYear, yearStage);
    }
    compteurClickName += 1;
  });
};

let compteurClickDesignation = 0;

const handleOrderByDesignation = (Allcompanys, companyByYear) => {
  const AllOrderByDesignationAscending = [...Allcompanys];

  AllOrderByDesignationAscending.sort((a, b) => {
    if(a.designation === null){
      return 1;
    }

    if (b.designation === null) {
      return -1;
    }

    if (a.designation !== null && b.designation !== null) {
      return b.designation.toLowerCase().localeCompare(a.designation.toLowerCase());
    }

    return 1;
  });

  const AllOrderByDesignationDescending = [...Allcompanys];
  AllOrderByDesignationDescending.sort((a, b) => {
    if(a.designation === null){
      return 1;
    }
    if (b.designation === null) {
      return -1;
    }

    if (a.designation !== null && b.designation !== null) {
      return a.designation.toLowerCase().localeCompare(b.designation.toLowerCase());
    }

    return 1;
  });

  const companyByYearOrderBydesignationAscending = [...companyByYear];
  companyByYearOrderBydesignationAscending.sort((a, b) => {
    if(a.designation === null){
      return 1;
    }

    if (b.designation === null) {
      return -1;
    }

    if (a.designation !== null && b.designation !== null) {
      return b.designation.toLowerCase().localeCompare(a.designation.toLowerCase());
    }

    return 1;
  });

  const companyByYearOrderBydesignationDescending = [...companyByYear];
  companyByYearOrderBydesignationDescending.sort((a, b) => {
    if(a.designation === null){
      return 1;
    }
    if (b.designation === null) {
      return -1;
    }

    if (a.designation !== null && b.designation !== null) {
      return a.designation.toLowerCase().localeCompare(b.designation.toLowerCase());
    }

    return 1;
  });

  const iconName = document.getElementById('orderByDesignation');
  iconName.addEventListener('click', () => {
    if (compteurClickDesignation % 2 === 0) {
      renderCompany(AllOrderByDesignationAscending, companyByYearOrderBydesignationAscending, yearStage);
    } else {
      renderCompany(AllOrderByDesignationDescending, companyByYearOrderBydesignationDescending, yearStage);
    }
    compteurClickDesignation += 1;
  });
};

let compteurClickStage = 0;

const handleOrderByStage = (Allcompanys, companyByYear) => {
  const AllorderByStageAscending = [...Allcompanys];
  AllorderByStageAscending.sort((a, b) => {
    if (a.countStage === b.countStage) {
      return 0;
    }
    if (a.countStage < b.countStage) {
      return -1;
    }
    return 1;
  });

  const AllorderByStageDescending = [...Allcompanys];
  AllorderByStageDescending.sort((a, b) => {
    if (a.countStage === b.countStage) {
      return 0;
    }
    if (a.countStage > b.countStage) {
      return -1;
    }
    return 1;
  });
  
  const companyByYearOrderByStageAscending = [...companyByYear];
  companyByYearOrderByStageAscending.sort((a, b) => {
    if (a.countStage === b.countStage) {
      return 0;
    }
    if (a.countStage < b.countStage) {
      return -1;
    }
    return 1;
  });

  const companyByYearOrderByStageDescending = [...companyByYear];
  companyByYearOrderByStageDescending.sort((a, b) => {
    if (a.countStage === b.countStage) {
      return 0;
    }
    if (a.countStage > b.countStage) {
      return -1;
    }
    return 1;
  });
  const iconStage = document.getElementById('orderByStage');
  iconStage.addEventListener('click', () => {
    if (compteurClickStage % 2 === 0) {
      renderCompany(AllorderByStageAscending, companyByYearOrderByStageAscending, yearStage)
    } else {
      renderCompany(AllorderByStageDescending, companyByYearOrderByStageDescending, yearStage)
    }
    compteurClickStage += 1;
  });
}

let compteurClickBlaclist = 0;

const handleOrderByBlacklist = (Allcompanys, companyByYear) => {
  const AllorderByBlacklistAscending = [...Allcompanys];
  AllorderByBlacklistAscending.sort((a, b) => {
    if (a.blacklist === b.blacklist) {
      return 0;
      }
    if (a.blacklist) {
      return -1;
    }
    return 1;
  });

  const AllorderByBlacklistDescending = [...Allcompanys];
  AllorderByBlacklistDescending.sort((a, b) => {
    if (a.blacklist === b.blacklist) {
      return 0;
      }
    if (b.blacklist) {
      return -1;
    }
    return 1;
  });

  const companyByYearOrderByBlacklistAscending = [...companyByYear];
  companyByYearOrderByBlacklistAscending.sort((a, b) => {
    if (a.blacklist === b.blacklist) {
      return 0;
      }
    if (a.blacklist) {
      return -1;
    }
    return 1;
  });

  const companyByYearOrderByBlacklistDescending = [...companyByYear];
  companyByYearOrderByBlacklistDescending.sort((a, b) => {
    if (a.blacklist === b.blacklist) {
      return 0;
      }
    if (b.blacklist) {
      return -1;
    }
    return 1;
  });
  const iconBlacklist = document.getElementById('orderByBlacklist');
  iconBlacklist.addEventListener('click', () => {
    if (compteurClickBlaclist % 2 === 0) {
      renderCompany(AllorderByBlacklistAscending, companyByYearOrderByBlacklistAscending, yearBlacklist)
    } else {
      renderCompany(AllorderByBlacklistDescending, companyByYearOrderByBlacklistDescending, yearBlacklist)
    }
    compteurClickBlaclist += 1;
  });

}

const ListAllCompany = async () => {
  const table = document.getElementById('table');
  if (!table) {
    return; // Exit if table element is not found
  }

  table.innerHTML = `
    <h3 class="ml12 mt-5 pt-5 text-center">Liste de toutes les entreprises</h3>
      <section class="w-75 p-3 mx-auto" id="leaderboard-sec">
        <form id="form">
          <div id="selectYearContainer"></div>
        </form>
                
        <table id="leaderboard" class="table table-striped table-hover">
          <thead>
            <tr class="border border-dark">
              <th id="companyName">Nom de l'entreprise <img id="orderByName" src=${orderBy}/> </th>
              <th id="companyDesignation">Appellation  <img id="orderByDesignation" src=${orderBy}/> </th>
              <th id="phoneNumber">Numéro de téléphone</th>
                              
            <th id="studentInStageNumber">Nombre d'étudiants en stage  <img id="orderByStage" src=${orderBy}/></th>
            <th id="Blacklist">Blacklist <img id="orderByBlacklist"  src=${orderBy}/></th>
          </tr>
          </thead>
          <tbody id="board"></tbody>
        </table>
    </section>
  `;

  const token = localStorage.getItem('token') || sessionStorage.getItem('token');
  const all = await fetch('/api/company/all', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': token
    }
  });

  const {Allcompanys} = await all.json();
  const {companyByYear} = await getListCompanies();
  Allcompanys.sort((a, b) => {
    if (a.tradeName.toLowerCase() < b.tradeName.toLowerCase()) {
      return -1;
    }
    if (a.tradeName.toLowerCase() > b.tradeName.toLowerCase()) {
      return 1;
    }
    if (a.designation && b.designation) {
      const appellationA = a.designation.toLowerCase();
      const appellationB = b.designation.toLowerCase();

      if (appellationA < appellationB) {
        return -1;
      }
      if (appellationA > appellationB) {
        return 1;
      }
    } else if (!a.appellation) {
      return -1;
    } else {
      return 1;
    }

    return 0;
  })

  companyByYear.sort((a, b) => {
    if (a.tradeName.toLowerCase() < b.tradeName.toLowerCase()) {
      return -1;
    }
    if (a.tradeName.toLowerCase() > b.tradeName.toLowerCase()) {
      return 1;
    }
    if (a.designation && b.designation) {

      const appellationA = a.designation.toLowerCase();
      const appellationB = b.designation.toLowerCase();

      if (appellationA < appellationB) {
        return -1;
      }
      if (appellationA > appellationB) {
        return 1;
      }
    } else if (!a.appellation) {
      return -1;
    } else {
      return 1;
    }

    return 0;
  })

  const yearsSet = new Set();
  companyByYear.forEach(company1 => {
    if (company1.academicYear !== undefined) {
      yearsSet.add(company1.academicYear);
    }
  });

  const years = Array.from(yearsSet);
  years.sort().reverse();


  populateYearSelect(years);
  handleYearChange(Allcompanys, companyByYear);
  handleOrderByName(Allcompanys, companyByYear);
  handleOrderByDesignation(Allcompanys, companyByYear);
  handleOrderByStage(Allcompanys, companyByYear);
  handleOrderByBlacklist(Allcompanys, companyByYear);
  renderCompany(Allcompanys, companyByYear, 'aucune sélection');

  attachBlacklistEventListeners();

};

export default HomePageTeacher;
