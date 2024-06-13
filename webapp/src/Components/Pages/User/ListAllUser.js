import {clearPage} from "../../../utils/render";
import {getAuthenticatedUser, isAuthenticated} from '../../../utils/auths';
import Navigate from "../../Router/Navigate";
import searchButton from "../../../img/searchButton.png";
import HomePageListAllInformationForOneStudent from "../PageInfoStudent/PageInfoStudent"

const TEACHER = 'professeur';
const ADMIN = 'administratif';

const studentList = new Set();
const allStudents = new Set();

const handleOneStudent = () => {
  const studentElements = document.querySelectorAll('.studentNameOnly');
  studentElements.forEach(student => {
    const userType = student.querySelector('td:last-child').textContent;
    if (userType === 'étudiant') {
      student.addEventListener('click', async () => {
        await HomePageListAllInformationForOneStudent(
          student.getAttribute('idValue')
        );
      });
    }
  });
};

const renderUserTable = (users, selectedYear, onlyStudents) => {
  const board = document.querySelector('#board');
  board.innerHTML = '';

  users.forEach(user => {
    if ((onlyStudents && user.utilisateurType !== 'étudiant') || (selectedYear
        !== 'aucune sélection' && user.academicYear !== selectedYear)) {
      return;
    }

    let stageInfo = '';
    if (user.utilisateurType === 'étudiant') {
      stageInfo = user.stageId !== 0 ? '<td>stage trouvé</td>'
          : '<td>pas encore de stage</td>';
      studentList.add(`${user.firstName} ${user.lastName}`);
      allStudents.add(user.userId);
    } else {
      stageInfo = '<td></td>';
    }

    const yearInfo = user.utilisateurType === 'étudiant' ? `<td>${user.academicYear}</td>`
        : '<td></td>';
    const infoUser = `
    
      <tr class="studentNameOnly" idValue="${user.userId}">
        <td>${user.lastName}</td>
        <td>${user.firstName}</td>
        <td>${user.email}</td>
        ${yearInfo}
        ${stageInfo}
        <td>${user.utilisateurType}</td>
      </tr>`;
    board.innerHTML += infoUser;
  });
  handleOneStudent();
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

const handleYearChange = (infoBoards, onlyStudents) => {
  const selectedYear = document.querySelector('#selectYearContainer select');
  selectedYear.addEventListener('change', () => {
    const year = selectedYear.value;
    renderUserTable(infoBoards, year, onlyStudents);
  });
};

const handleCheckboxChange = (infoBoards) => {
  const checkbox = document.getElementById('onlyStudents');
  checkbox.addEventListener('change', () => {
    const onlyStudents = checkbox.checked;
    renderUserTable(infoBoards, 'aucune sélection', onlyStudents);
  });
};

const handleFormChange = (infoBoards) => {
  const form = document.getElementById('form');
  form.addEventListener('change', () => {
    const selectedYear = document.querySelector(
        '#selectYearContainer select').value;
    const onlyStudents = document.getElementById('onlyStudents').checked;
    renderUserTable(infoBoards, selectedYear, onlyStudents);
  });
};

const searchStudents = () => {
  const input = document.getElementById('inputText').value.toLowerCase();
  const suggestions = document.getElementById('suggestions');

  suggestions.innerHTML = '';

  const filteredStudentsArray = Array.from(studentList).filter(student => {
    const commonChars = Array.from(student.toLowerCase()).filter(char =>
        input.includes(char)
    ).length;
    return commonChars >= 3;
  });
  const filteredStudents = new Set(filteredStudentsArray);
  filteredStudents.forEach(student => {
    const option = document.createElement('option');
    option.value = student;
    suggestions.appendChild(option);
  });
};


const initializeSearch = (users) => {
  const inputText = document.getElementById('inputText');
  const button = document.getElementById('searchButton');
  inputText.addEventListener('input', searchStudents);
  let enterPressed = false;
  inputText.addEventListener('keydown', (event) => {
    if (event.key === 'Enter') {
      event.preventDefault();
      enterPressed = true;
      const currentInput = inputText.value.toLowerCase();
      const filteredUserIds = Array.from(allStudents).filter(userId => {
        const user = users.find(u => u.userId === userId)
        if (!user) return false;
        const fullName = `${user.firstName} ${user.lastName}`.toLowerCase();
        return fullName.includes(currentInput);
      });
      const filteredStudents = new Set(filteredUserIds.map(userId => users.find(u => u.userId === userId)));
      renderUserTable(filteredStudents, 'aucune sélection', false);

    }
  });
  inputText.addEventListener('change', () => {
    if (enterPressed) {
      enterPressed = false; // Reset the flag
    } else {
      const selectedStudent = inputText.value;
      const studentToFind = Array.from(allStudents).filter(userId => {
        const user = users.find(u => u.userId === userId)
        if (!user) return false;
        const fullName = `${user.firstName} ${user.lastName}`;
        return fullName === selectedStudent;
      });
      const filteredStudents = new Set(studentToFind.map(userId => users.find(u => u.userId === userId)));
      if (!filteredStudents) {
        // eslint-disable-next-line no-use-before-define
        ListAllUser();
      } else {
        renderUserTable(filteredStudents, 'aucune sélection', false);
      }
    }
  });

  button.addEventListener('click', () => {
    // eslint-disable-next-line no-use-before-define
    ListAllUser();
  });
};

const ListAllUser = async () => {
  clearPage();
  const main = document.querySelector('main');

  if (!isAuthenticated()) {
    Navigate('/403');
    return;
  }

  const user = await getAuthenticatedUser();
  const role = user.user.utilisateurType;
  if (role !== TEACHER && role !== ADMIN) {
    Navigate('/403');
    return;
  }

  main.innerHTML = `<h3 class="ml12 mt-5 pt-5 text-center">Liste de tous les utilisateurs</h3>
                    <section class="w-75 p-3 mx-auto" id="leaderboard-sec">
                      <form id="form">
                        <div id="selectYearContainer"></div>
                        <div class="form-check">
                          <input type="checkbox" id="onlyStudents" />
                          <label for="onlyStudents">Uniquement les étudiants</label>
                        </div>
                      </form>
                       <div>
                          <input type="text" id="inputText" list="suggestions">
                          <datalist id="suggestions"></datalist>
                        
                          <img id="searchButton" src=${searchButton}/>
                         
                       </div>
                       
                          
                         
                      <table id="leaderboard" class="table table-striped table-hover">
                        <thead>
                          <tr class="border border-dark">
                            <th id="lastName">Nom</th>
                            <th id="firstName">Prénom</th>
                            <th id="email">Émail</th>
                            <th id="academicYear">Année académique</th>
                            <th id="id_student_stage">Stage</th>
                            <th id="type">Type</th>
                          </tr>
                        </thead>
                        <tbody id="board"></tbody>
                      </table>
                    </section>`;

  try {
    const token = localStorage.getItem('token') || sessionStorage.getItem(
        'token');
    const response = await fetch('/api/auths/ListAllUser', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': token
      }
    });
    if (!response.ok) {
      throw new Error('Failed to fetch data');
    }

    const {users} = await response.json();
    const yearsSet = new Set(users.map(user1 => user1.academicYear));
    const years = Array.from(yearsSet);
    years.sort().reverse();

    populateYearSelect(years);
    handleYearChange(users, false);
    handleCheckboxChange(users);
    handleFormChange(users);
    renderUserTable(users, 'aucune sélection', false);
    initializeSearch(users);
  } catch (error) {
    Navigate('/500');
  }
};

export default ListAllUser;
