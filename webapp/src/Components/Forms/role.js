/**
 * role menu to use in ConnexionPage if user has an email like @vinci.be
 * @type {string} role
 */

import teacherIcon from "../../img/teacher.png";  
import adminIcon from "../../img/administrator.png";

const role = `
        <h1 id="role" class="text-center">Quelle est votre rôle ? </h1>
        <div id="but" style="width: 500px; height:250px ;  display: flex; justify-content: space-between; margin-top: 20px">
                <button class="neumorphic" id="admin">
                    <img src="${adminIcon}">
                    <span>Administratif</span>
                </button>

                <button class="neumorphic" id="teacher">
                    <img src="${teacherIcon}">
                    <span>Professeur</span>
                </button>

        </div>
        <div style="display: flex; justify-content: center; align-items: center;">
            <p id="resultatSuccessLogin" class="text-success"></p>
            <p id="resultatDangerLogin" class="text-danger"></p>
        </div>
`;

export default role;
