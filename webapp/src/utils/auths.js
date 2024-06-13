const STORE_NAME_TOKEN = 'token';
let currentUser;
let currentToken;

const getAuthenticatedUser = async () => {
  if (currentUser !== undefined) return currentUser;

  if (currentToken === undefined) {
    currentToken = localStorage.getItem(STORE_NAME_TOKEN) || sessionStorage.getItem(STORE_NAME_TOKEN);
  }

  if (currentToken === null) return null;

  const request = await fetch('/api/auths/me', {
    method: 'GET',
    headers: {
      'Authorization': currentToken,
    },
  });

  if (request.ok) {
    currentUser = await request.json();
    return currentUser;
  }

  return null;
};


const isAuthenticated = () => {
  if(localStorage.getItem(STORE_NAME_TOKEN) ) {
  currentToken = localStorage.getItem(STORE_NAME_TOKEN);
  return true;
  }
  if(sessionStorage.getItem(STORE_NAME_TOKEN)) {
    currentToken = sessionStorage.getItem(STORE_NAME_TOKEN);
    return true;
  }
    return false;
  }

const setLocalToken = (token) => {
  localStorage.setItem(STORE_NAME_TOKEN, token);
  currentToken = token;
}

const setSessionToken = (token) => {
  sessionStorage.setItem(STORE_NAME_TOKEN, token);
  currentToken = token;
}

const clearToken = () => {
  localStorage.removeItem(STORE_NAME_TOKEN);
  sessionStorage.removeItem(STORE_NAME_TOKEN);
  currentToken = undefined;
}



export {
  getAuthenticatedUser,
  isAuthenticated,
  setLocalToken,
  setSessionToken,
  clearToken,
};