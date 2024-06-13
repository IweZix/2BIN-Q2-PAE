/**
 * Get the number of students with a stage and without a stage by academic year
 * @returns {Promise<any>} response of request
 */
async function getNbEtudiants() {
  const token = localStorage.getItem('token') || sessionStorage.getItem(
      'token');
  const response = await fetch(`/api/contact/users-stages`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': token
    }
  });
  return response.json();
}

/**
 * Get the list of companies
 * @returns {Promise<any>} response of request
 */
async function getListCompanies() {
  const token = localStorage.getItem('token') || sessionStorage.getItem(
      'token');
  const response = await fetch(`/api/company`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': token
    }
  });
  return response.json();
}

/**
 * Blacklist a company
 * @param companyId the id of the company
 * @param userId the id of the user
 * @param reason the reason of the blacklist
 * @returns {Promise<any>} response of request
 */
async function blacklistedCompanies(companyId, studentId, motivationBlacklist, version) {
  const token = localStorage.getItem('token') || sessionStorage.getItem(
      'token');
  const response = await fetch(`/api/company/blacklist`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': token
    },
    body: JSON.stringify({
      companyId,
      studentId,
      motivationBlacklist,
      version
    })
  });
  return response.json();
}

/**
 * Get the specific company from the database
 * @returns {JSON} one company
 */
async function getCompanyById(id) {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token');
    const request = await fetch(`/api/company/oneCompany?idCompany=${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        },
    });

    if (request.ok) {
        const response = await request.json();
        return response.company;
    } 
    
    return null;

}

/**
 * Get the list of contacts of specific company from the database
 * @returns {JSON} the list of contacts
 */
async function getAllContactForTheCompany(id) {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token');
    const request = await fetch(`/api/contact/getAllContactForTheCompany?idCompany=${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        },
    });

    if (request.ok) {
        const response = await request.json();
        return response.contacts;
    } 
    
    return null;

}

/**
 * Get the list of contacts of specific student from the database
 * @returns {JSON} the list of contacts
 */
async function getAllContactAndInforStudent(id) {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token');
    const request = await fetch(`/api/contact/getAllContactForTheStudent?studentId=${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        },
    });
    if (request.ok) {
        const response = await request.json();
        return response.contacts;
    } 
    
    return null;

}

/**
 * Get the stages informations for the specific student from the database
 * @returns {JSON} the stage
 */
async function getStageForTheStudent(id) {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token');
    const request = await fetch(`/api/stage/getStageForTheStudent?idStudent=${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        },
    });

    if (request.ok) {
        const response = await request.json();
        return response.stage;
    } 
    
    return null;

}


module.exports = {
  getNbEtudiants,
  getListCompanies,
  blacklistedCompanies,
  getCompanyById,
  getAllContactForTheCompany,
  getAllContactAndInforStudent,
  getStageForTheStudent
}

