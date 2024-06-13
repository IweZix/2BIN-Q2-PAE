/**
 * Add a new company
 * @param tradeName the company's tradeName
 * @param designation the company's designation
 * @param address the company's address
 * @param phoneNumber the company's phoneNumber
 * @param email the company's email
 */

async function createNewCompany(tradeName, designation, address, phoneNumber, email) {
      const token = localStorage.getItem('token') || sessionStorage.getItem('token');
      const request = await fetch(`/api/company/create`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        },
        body: JSON.stringify({
            tradeName,
            designation,
            address,
            phoneNumber,
            email,
        }),
      });

      if (request.ok) {
        const newCompany = await request.json(); 
        return newCompany; 
    } 

    throw new Error('Erreur lors de la création de la nouvelle entreprise'); 
}    

/**
 * Decline a contact
 * @param companyId contact id
 * @param declineReason the reason for declining the contact
 * @param version the version of the contact
 * @returns true if the contact was declined, false otherwise
 */
async function declineContact(companyId, declineReason, version) {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token');
    const request = await fetch(`/api/contact/decline`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        },
        body: JSON.stringify({
            companyId,
            declineReason,
            version
        })
    });

    return request.ok;
}

/**
 * Get all companys from the database
 * @returns {JSON} all companys
 */
async function getAllCompanys() {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token');
    const request = await fetch('/api/company/all', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        },
    });
    if (request.ok) {
        const response = await request.json();
        return response.Allcompanys;
    }
    return null;
}

/**
 * Get all companys from the database
 * @returns {JSON} all companys
 */
async function getAllNotBlacklistedCompanys() {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token');
    const request = await fetch('/api/company/allNotBlacklisted', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        },
    });

    if (request.ok) {
        const response = await request.json();
        return response.companys;
    }
    return null;
}

/**
 * Get all contacts from the database
 * @returns {JSON} all contacts
 */
async function getAllContacts() {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token');
    const request = await fetch('/api/contact/all', {
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
 * Not follow a contact
 * @param id contact id
 * @param contactVersion the version of the contact
 * @returns true if the contact was not followed, false otherwise
 */
async function notFollowContact(companyId, version) {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token');
    const request = await fetch('/api/contact/stop', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        },
        body: JSON.stringify({
            companyId,
            version
        })
    });

    return request.ok;
}

/**
 * Started a contact
 * @param id contact id
 * @returns true if the contact was started, false otherwise
 */

async function startContact(companyId) {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token');
    const request = await fetch('/api/contact/started', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        },
        body: JSON.stringify({
            companyId
        })
    });

    if (request.ok) {
        const newContact = await request.json();
        return newContact;
    }
    throw new Error('Erreur lors de la création du nouveau contact');
}

/**
 * Take a contact
 * @param id contact id
 * @param meet the type of meeting
 * @param version the version of the contact
 * @returns true if the contact was taken, false otherwise
 */
async function takeContact(companyId, meetingType, version) {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token');
    const request = await fetch('/api/contact/meet', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        },
        body: JSON.stringify({
            companyId,
            meetingType,
            version
        })
    });


    return request.ok;
}

/**
 * Create a stage
 * 
 * @param tradeNameCompany the name of the company
 * @param internshipSupervisorName the name of the internship supervisor
 * @param internshipProject the internship project subject
 * @param dateSignature the date of the convention signature
 */
async function createStage(tradeNameCompany, internshipSupervisorLastName, internshipSupervisorFirstName, internshipProject, dateSignature) {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token');
    const request = await fetch('/api/stage/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        },
        body: JSON.stringify({
            "tradeNameCompany": tradeNameCompany,
            "internshipSupervisorLastName": internshipSupervisorLastName,
            "internshipSupervisorFirstName": internshipSupervisorFirstName,
            "internshipProject": internshipProject,
            "signatureDate": dateSignature
        })
    });

    return request.ok;
}

/**
 * Accept a contact
 * @param companyId contact id
 * @param version the version of the contact
 * @returns true if the contact was accepted, false otherwise
 */
async function accepteContact(companyId, version) {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token');
    const request = await fetch(`/api/contact/accepte`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        },
        body: JSON.stringify({
            companyId,
            version
        })
    });

    return request.ok;
}

/**
 * Get the stage information
 * @returns {JSON} stage information
 */
async function getStageInfo() {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token');
    const request = await fetch('/api/stage/accepte', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        }
    });
    if (request.ok) {
        const stage = await request.json();
        return stage;
    }
    return null;
}
/**
 * Method to change the internship project
 * @param internshipProject internship project
 * @returnstrue if the contact was taken, false otherwise
 */
async function changeInternshipProject(internshipProject, stageId, version){
    const token = localStorage.getItem('token') || sessionStorage.getItem('token');
    const request = await fetch('/api/stage/internshipProject', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        },
        body: JSON.stringify({
            "internshipProject": internshipProject,
            "stageId": stageId,
            "version": version
        })
    });
    return request.ok;
}

/**
 * Get all internship supervisor from a company
 * 
 * @param companyId the company id
 * @returns {JSON} all internship supervisor
 */
async function getAllInternshipSupervisor(companyId) {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token');
    const request = await fetch(`/api/internshipsupervisor/all?companyId=${companyId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        }
    });

    if (request.ok) {
        const response = await request.json();
        return response.AllInternshipSupervisor;
    }
    return null;
}

/**
 * Get the specific student from the database
 * @returns {JSON} one student
 */
async function getUserById(id) {
    const token = localStorage.getItem('token') || sessionStorage.getItem(
        'token');
    const request = await fetch(`/api/auths/getUserById?studentId=${id}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': token
      }
    });

    if (request.ok) {
      const response = await request.json();
        return response.user;
    }
    return null

}

export {createNewCompany, declineContact, getAllCompanys, getAllNotBlacklistedCompanys, getAllContacts,
    notFollowContact, startContact, takeContact, createStage, accepteContact
    , getStageInfo, changeInternshipProject, getAllInternshipSupervisor, getUserById} ;