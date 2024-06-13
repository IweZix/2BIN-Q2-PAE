DROP SCHEMA IF EXISTS pae CASCADE ;
CREATE SCHEMA pae;

CREATE TABLE pae.utilisateurs
(
    id_utilisateur      SERIAL PRIMARY KEY,
    email               varchar(200),
    mot_de_passe        varchar(100),
    nom                 varchar(100) ,
    prenom              varchar(100),
    num_tel             varchar(20),
    date_inscription    DATE,
    annee_academique    varchar(20),
    type                varchar(200),
    version INTEGER
);

CREATE TABLE pae.entreprises
(
    id_entreprise   SERIAL PRIMARY KEY ,
    nom             varchar(100),
    appellation     varchar(100),
    nom_appellation varchar(200),
    blacklist       boolean,
    adresse         varchar(200),
    num_tel         varchar(20),
    email           varchar (200),
    version INTEGER
);

CREATE TABLE pae.entreprises_problematiques
(
    entreprise      INTEGER REFERENCES pae.entreprises(id_entreprise),
    PRIMARY KEY (entreprise),
    utilisateur     INTEGER REFERENCES pae.utilisateurs(id_utilisateur),
    date            DATE,
    motivation      varchar(300)
);

CREATE TABLE pae.contacts
(
    etudiant            INTEGER REFERENCES pae.utilisateurs(id_utilisateur),
    entreprise          INTEGER REFERENCES pae.entreprises(id_entreprise),
    PRIMARY KEY (etudiant, entreprise),
    etat_contact        varchar(100),
    type_de_rencontre   varchar(100),
    raison_refus        varchar(300),
    annee_academique        VARCHAR(20),
    version INTEGER
);

CREATE TABLE pae.responsables_de_stage
(
    id_responsable              SERIAL PRIMARY KEY,
    nom                         varchar(100),
    prenom                      varchar(100),
    num_tel                     varchar(20),
    email                       varchar(200),
    entreprise                  INTEGER REFERENCES pae.entreprises(id_entreprise)
);

CREATE TABLE pae.stages
(
    id_stage                SERIAL PRIMARY KEY,
    date_de_signature       DATE,
    sujet_de_stage          VARCHAR(300),
    annee_academique        VARCHAR(20),
    etudiant                INTEGER,
    entreprise              INTEGER,
    responsable_du_stage    INTEGER REFERENCES pae.responsables_de_stage(id_responsable),
    FOREIGN KEY (etudiant, entreprise) REFERENCES pae.contacts(etudiant, entreprise),
    version INTEGER
);

-- Insert for livrable 6
-- companies
INSERT INTO pae.entreprises(nom, nom_appellation, num_tel, adresse, version, blacklist) VALUES ('Assyst Europe', 'Assyst Europe', '02.609.25.00', 'Avenue du Japon, 1/B9, 1420 Braine-l''Alleud', 1, false);
INSERT INTO pae.entreprises(nom, nom_appellation, num_tel, adresse, version, blacklist) VALUES ('AXIS SRL', 'AXIS SRL', '02 752 17 60', 'Avenue de l''Hélianthe, 63, 1180 Uccle', 1, false);
INSERT INTO pae.entreprises(nom, nom_appellation, num_tel, adresse, version, blacklist) VALUES ('Infrabel', 'Infrabel', '02 525 22 11', 'Rue Bara, 135, 1070 Bruxelles', 1, false);
INSERT INTO pae.entreprises(nom, nom_appellation, num_tel, adresse, version, blacklist) VALUES ('La route du papier', 'La route du papier', '02 586 16 65', 'Avenue des Mimosas, 83, 1150 Woluwe-Saint-Pierre', 1, false);
INSERT INTO pae.entreprises(nom, nom_appellation, num_tel, adresse, version, blacklist) VALUES ('LetsBuild', 'LetsBuild', '014 54 67 54', 'Chaussée de Bruxelles, 135A, 1310 La Hulpe', 1, false);
INSERT INTO pae.entreprises(nom, nom_appellation, num_tel, adresse, version, blacklist) VALUES ('Niboo', 'Niboo', '0487 02 79 13', 'Boulevard du Souverain, 24, 1170 Watermael-Boisfort', 1, false);
INSERT INTO pae.entreprises(nom, nom_appellation, num_tel, adresse, version, blacklist) VALUES ('Sopra Steria', 'Sopra Steria', '02 566 66 66', 'Avenue Arnaud Fraiteur, 15/23, 1050 Bruxelles', 1, false);
INSERT INTO pae.entreprises(nom, nom_appellation, num_tel, adresse, version, blacklist) VALUES ('The Bayard Partnership', 'The Bayard Partnership', '02 309 52 45', 'Grauwmeer, 1/57 bte 55, 3001 Leuven', 1, false);
-- responsable de stage
INSERT INTO pae.responsables_de_stage(nom, prenom, num_tel, email, entreprise) VALUES ('Dossche', 'Stéphanie', '014.54.67.54', 'stephanie.dossche@letsbuild.com', (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'LetsBuild'));
INSERT INTO pae.responsables_de_stage(nom, prenom, num_tel, entreprise) VALUES ('Alvarez Corchete', 'Roberto', '02.566.60.14', (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Sopra Steria'));
INSERT INTO pae.responsables_de_stage(nom, prenom, num_tel, email, entreprise) VALUES ('Assal', 'Farid', '0474 39 69 09', 'f.assal@assyst-europe.com', (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Assyst Europe'));
INSERT INTO pae.responsables_de_stage(nom, prenom, num_tel, entreprise) VALUES ('Ile', 'Emile', '0489 32 16 54', (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'La route du papier'));
INSERT INTO pae.responsables_de_stage(nom, prenom, num_tel, entreprise) VALUES ('Hibo', 'Owln', '0456 678 567', (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Infrabel'));
INSERT INTO pae.responsables_de_stage(nom, prenom, num_tel, entreprise) VALUES ('Barn', 'Henri', '02 752 17 60', (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'AXIS SRL'));
-- users
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Baroni', 'Raphaël', '0481 01 01 01', 'raphael.baroni@vinci.be', '$2a$10$vC7DtqAeTIgGTeikkyTXuOdi/mKjrFw5wbZn6gnLPI4qZickON/j.', 'professeur', '2020-09-21', '2020-2021', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Lehmann', 'Brigitte', '0482 02 02 02', 'brigitte.lehmann@vinci.be', '$2a$10$vC7DtqAeTIgGTeikkyTXuOdi/mKjrFw5wbZn6gnLPI4qZickON/j.', 'professeur', '2020-09-21', '2020-2021', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Leleux', 'Laurent', '0483 03 03 03', 'laurent.leleux@vinci.be', '$2a$10$vC7DtqAeTIgGTeikkyTXuOdi/mKjrFw5wbZn6gnLPI4qZickON/j.', 'professeur', '2020-09-21', '2020-2021', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Lancaster', 'Annouck', '0484 04 04 04', 'annouck.lancaster@vinci.be', '$2a$10$WCANH0DGKtqV42gfSiUg.ewTHur.5IqmLlqcrT3A/E3FZG0sruyj6', 'administratif', '2020-09-21', '2020-2021', 1);

INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Skile', 'Elle', '0491 00 00 01', 'elle.skile@student.vinci.be', '$2a$10$TEgQ8K7IPv4AT.ev48M.8.yNNPTrOHctEe.poJ4fhHtKZ1VC0u7Ky', 'étudiant', '2021-09-21', '2021-2022', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Ilotie', 'Basile', '0491 00 00 11', 'basile.ilotie@student.vinci.be', '$2a$10$TEgQ8K7IPv4AT.ev48M.8.yNNPTrOHctEe.poJ4fhHtKZ1VC0u7Ky', 'étudiant', '2021-09-21', '2021-2022', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Frilot', 'Basile', '0491 00 00 21', 'basile.frilot@student.vinci.be', '$2a$10$TEgQ8K7IPv4AT.ev48M.8.yNNPTrOHctEe.poJ4fhHtKZ1VC0u7Ky', 'étudiant', '2021-09-21', '2021-2022', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Ilot', 'Basile', '0492 00 00 01', 'basile.ilot@student.vinci.be', '$2a$10$TEgQ8K7IPv4AT.ev48M.8.yNNPTrOHctEe.poJ4fhHtKZ1VC0u7Ky', 'étudiant', '2021-09-21', '2021-2022', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Dito', 'Arnaud', '0493 00 00 01', 'arnaud.dito@student.vinci.be', '$2a$10$TEgQ8K7IPv4AT.ev48M.8.yNNPTrOHctEe.poJ4fhHtKZ1VC0u7Ky', 'étudiant', '2021-09-21', '2021-2022', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Dilo', 'Arnaud', '0494 00 00 01', 'arnaud.dilo@student.vinci.be', '$2a$10$TEgQ8K7IPv4AT.ev48M.8.yNNPTrOHctEe.poJ4fhHtKZ1VC0u7Ky', 'étudiant', '2021-09-21', '2021-2022', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Dilot', 'Cedric', '0495 00 00 01', 'cedric.dilot@student.vinci.be', '$2a$10$TEgQ8K7IPv4AT.ev48M.8.yNNPTrOHctEe.poJ4fhHtKZ1VC0u7Ky', 'étudiant', '2021-09-21', '2021-2022', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Linot', 'Auristelle', '0496 00 00 01', 'auristelle.linot@student.vinci.be', '$2a$10$TEgQ8K7IPv4AT.ev48M.8.yNNPTrOHctEe.poJ4fhHtKZ1VC0u7Ky', 'étudiant', '2021-09-21', '2021-2022', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Demoulin', 'Basile', '0496 00 00 02', 'basile.demoulin@student.vinci.be', '$2a$10$TEgQ8K7IPv4AT.ev48M.8.yNNPTrOHctEe.poJ4fhHtKZ1VC0u7Ky', 'étudiant', '2022-09-23', '2022-2023', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Moulin', 'Arthur', '0497 00 00 02', 'arthur.moulin@student.vinci.be', '$2a$10$TEgQ8K7IPv4AT.ev48M.8.yNNPTrOHctEe.poJ4fhHtKZ1VC0u7Ky', 'étudiant', '2022-09-23', '2022-2023', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Moulin', 'Hugo', '0497 00 00 03', 'hugo.moulin@student.vinci.be', '$2a$10$TEgQ8K7IPv4AT.ev48M.8.yNNPTrOHctEe.poJ4fhHtKZ1VC0u7Ky', 'étudiant', '2022-09-23', '2022-2023', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Demoulin', 'Jeremy', '0497 00 00 20', 'jeremy.demoulin@student.vinci.be', '$2a$10$TEgQ8K7IPv4AT.ev48M.8.yNNPTrOHctEe.poJ4fhHtKZ1VC0u7Ky', 'étudiant', '2022-09-23', '2022-2023', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Mile', 'Aurèle', '0497 00 00 21', 'aurele.mile@student.vinci.be', '$2a$10$TEgQ8K7IPv4AT.ev48M.8.yNNPTrOHctEe.poJ4fhHtKZ1VC0u7Ky', 'étudiant', '2022-09-23', '2022-2023', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Mile', 'Frank', '0497 00 00 75', 'frank.mile@student.vinci.be', '$2a$10$TEgQ8K7IPv4AT.ev48M.8.yNNPTrOHctEe.poJ4fhHtKZ1VC0u7Ky', 'étudiant', '2022-09-27', '2022-2023', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Dumoulin', 'Basile', '0497 00 00 58', 'basile.dumoulin@student.vinci.be', '$2a$10$TEgQ8K7IPv4AT.ev48M.8.yNNPTrOHctEe.poJ4fhHtKZ1VC0u7Ky', 'étudiant', '2022-09-27', '2022-2023', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Dumoulin', 'Axel', '0497 00 00 97', 'axel.dumoulin@student.vinci.be', '$2a$10$TEgQ8K7IPv4AT.ev48M.8.yNNPTrOHctEe.poJ4fhHtKZ1VC0u7Ky', 'étudiant', '2022-09-27', '2022-2023', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Line', 'Caroline', '0486 00 00 01', 'caroline.line@student.vinci.be', '$2a$10$TEgQ8K7IPv4AT.ev48M.8.yNNPTrOHctEe.poJ4fhHtKZ1VC0u7Ky', 'étudiant', '2023-09-18', '2023-2024', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Ile', 'Achille', '0487 00 00 01', 'ach.ile@student.vinci.be', '$2a$10$TEgQ8K7IPv4AT.ev48M.8.yNNPTrOHctEe.poJ4fhHtKZ1VC0u7Ky', 'étudiant', '2023-09-18', '2023-2024', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Ile', 'Basile', '0488 00 00 01', 'basile.ile@student.vinci.be', '$2a$10$TEgQ8K7IPv4AT.ev48M.8.yNNPTrOHctEe.poJ4fhHtKZ1VC0u7Ky', 'étudiant', '2023-09-18', '2023-2024', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('skile', 'Achille', '0490 00 00 01', 'achille.skile@student.vinci.be', '$2a$10$TEgQ8K7IPv4AT.ev48M.8.yNNPTrOHctEe.poJ4fhHtKZ1VC0u7Ky', 'étudiant', '2023-09-18', '2023-2024', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('skile', 'Carole', '0489 00 00 01', 'carole.skile@student.vinci.be', '$2a$10$TEgQ8K7IPv4AT.ev48M.8.yNNPTrOHctEe.poJ4fhHtKZ1VC0u7Ky', 'étudiant', '2023-09-18', '2023-2024', 1);
INSERT INTO pae.utilisateurs(nom, prenom, num_tel, email, mot_de_passe, type, date_inscription, annee_academique, version) VALUES ('Ile', 'Théophile', '0488 35 33 89', 'theophile.ile@student.vinci.be', '$2a$10$TEgQ8K7IPv4AT.ev48M.8.yNNPTrOHctEe.poJ4fhHtKZ1VC0u7Ky', 'étudiant', '2023-03-01', '2023-2024', 1);
-- Contacts
-- 2023-2024
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'carole.skile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'LetsBuild'), 'accepte', 'distance', '2023-2024', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'ach.ile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Sopra Steria'), 'accepte', 'en entreprise', '2023-2024', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, raison_refus, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'ach.ile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Niboo'), 'refuse', 'N''ont pas accepte d''avoir un entretien', 'distance', '2023-2024', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'basile.ile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Assyst Europe'), 'accepte', 'en entreprise', '2023-2024', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'basile.ile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'LetsBuild'), 'suspendu', 'distance', '2023-2024', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'basile.ile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Sopra Steria'), 'suspendu', '2023-2024', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, raison_refus, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'basile.ile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Niboo'), 'refuse', 'ne prennent qu''un seul etudiant', 'en entreprise', '2023-2024', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, raison_refus, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'caroline.line@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Niboo'), 'refuse', 'Pas d''affinité avec le l’ERP Odoo', 'distance', '2023-2024', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'caroline.line@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Sopra Steria'), 'plus suivi', '2023-2024', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'caroline.line@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'LetsBuild'), 'pris', 'distance', '2023-2024', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'theophile.ile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Sopra Steria'), 'initie', '2023-2024', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'theophile.ile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Niboo'), 'initie', '2023-2024', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'theophile.ile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'LetsBuild'), 'initie', '2023-2024', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'achille.skile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Sopra Steria'), 'initie', '2023-2024', 1);
-- Stages
INSERT INTO pae.stages(annee_academique, etudiant, entreprise, sujet_de_stage, date_de_signature, responsable_du_stage, version) VALUES ('2023-2024', (SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'carole.skile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'LetsBuild'), 'Un ERP : Odoo', '10/10/2023', (SELECT id_responsable FROM pae.responsables_de_stage WHERE nom = 'Dossche'), 1);
INSERT INTO pae.stages(annee_academique, etudiant, entreprise, sujet_de_stage, date_de_signature, responsable_du_stage, version) VALUES ('2023-2024', (SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'ach.ile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Sopra Steria'), 'sBMS project - a complex environment', '23/11/2023', (SELECT id_responsable FROM pae.responsables_de_stage WHERE nom = 'Alvarez Corchete'), 1);
INSERT INTO pae.stages(annee_academique, etudiant, entreprise, sujet_de_stage, date_de_signature, responsable_du_stage, version) VALUES ('2023-2024', (SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'basile.ile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Assyst Europe'), 'CRM : Microsoft Dynamics 365 For Sales', '12/10/2023', (SELECT id_responsable FROM pae.responsables_de_stage WHERE nom = 'Assal'), 1);
-- Contacts
-- 2021-2022
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'elle.skile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'La route du papier'), 'accepte', 'distance', '2021-2022', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'basile.ilot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Sopra Steria'), 'plus suivi', '2021-2022', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, raison_refus, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'basile.frilot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'The Bayard Partnership'), 'refuse', 'ne prennent pas de stage', 'distance', '2021-2022', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'arnaud.dito@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Sopra Steria'), 'accepte', 'en entreprise', '2021-2022', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'arnaud.dilo@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Sopra Steria'), 'accepte', 'en entreprise', '2021-2022', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'cedric.dilot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Assyst Europe'), 'accepte', 'en entreprise', '2021-2022', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, raison_refus, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'cedric.dilot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Sopra Steria'), 'refuse', 'Choix autre etudiant', 'en entreprise', '2021-2022', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'auristelle.linot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Infrabel'), 'accepte', 'distance', '2021-2022', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'auristelle.linot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Sopra Steria'), 'suspendu', '2021-2022', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, raison_refus, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'auristelle.linot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Niboo'), 'refuse', 'Choix autre etudiant', 'distance', '2021-2022', 1);
-- Stages
INSERT INTO pae.stages(annee_academique, etudiant, entreprise, sujet_de_stage, date_de_signature, responsable_du_stage, version) VALUES ('2021-2022', (SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'elle.skile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'La route du papier'), 'Conservation et restauration d’œuvres d''art', '25/11/2021', (SELECT id_responsable FROM pae.responsables_de_stage WHERE nom = 'Ile'), 1);
INSERT INTO pae.stages(annee_academique, etudiant, entreprise, sujet_de_stage, date_de_signature, responsable_du_stage, version) VALUES ('2021-2022', (SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'arnaud.dito@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Sopra Steria'), 'L''analyste au centre du développement', '17/11/2021', (SELECT id_responsable FROM pae.responsables_de_stage WHERE nom = 'Alvarez Corchete'), 1);
INSERT INTO pae.stages(annee_academique, etudiant, entreprise, sujet_de_stage, date_de_signature, responsable_du_stage, version) VALUES ('2021-2022', (SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'arnaud.dilo@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Sopra Steria'), 'L''analyste au centre du développement', '17/11/2021', (SELECT id_responsable FROM pae.responsables_de_stage WHERE nom = 'Alvarez Corchete'), 1);
INSERT INTO pae.stages(annee_academique, etudiant, entreprise, sujet_de_stage, date_de_signature, responsable_du_stage, version) VALUES ('2021-2022', (SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'cedric.dilot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Assyst Europe'), 'ERP : Microsoft Dynamics 366', '23/11/2021', (SELECT id_responsable FROM pae.responsables_de_stage WHERE nom = 'Assal'), 1);
INSERT INTO pae.stages(annee_academique, etudiant, entreprise, sujet_de_stage, date_de_signature, responsable_du_stage, version) VALUES ('2021-2022', (SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'auristelle.linot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Infrabel'), 'Entretien des rails', '22/11/2021', (SELECT id_responsable FROM pae.responsables_de_stage WHERE nom = 'Hibo'), 1);
-- Contacts
-- 2022-2023
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'jeremy.demoulin@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Assyst Europe'), 'accepte', 'distance', '2022-2023', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'arthur.moulin@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'AXIS SRL'), 'accepte', 'en entreprise', '2022-2023', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'hugo.moulin@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'AXIS SRL'), 'accepte', 'en entreprise', '2022-2023', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'aurele.mile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'AXIS SRL'), 'accepte', 'distance', '2022-2023', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'frank.mile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'AXIS SRL'), 'accepte', 'distance', '2022-2023', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, raison_refus, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'basile.dumoulin@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'AXIS SRL'), 'refuse', 'Entretien n''a pas eu lieu', 'en entreprise', '2022-2023', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, raison_refus, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'basile.dumoulin@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Niboo'), 'refuse', 'Entretien n''a pas eu lieu', 'en entreprise', '2022-2023', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, raison_refus, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'basile.dumoulin@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Sopra Steria'), 'refuse', 'Entretien n''a pas eu lieu', 'distance', '2022-2023', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'axel.dumoulin@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Sopra Steria'), 'accepte', 'distance', '2022-2023', 1);
INSERT INTO pae.contacts(etudiant, entreprise, etat_contact, raison_refus, type_de_rencontre, annee_academique, version) VALUES ((SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'basile.frilot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Sopra Steria'), 'refuse', 'Choix autre etudiant', 'distance', '2022-2023', 1);
-- Stages
INSERT INTO pae.stages(annee_academique, etudiant, entreprise, sujet_de_stage, date_de_signature, responsable_du_stage, version) VALUES ('2022-2023', (SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'jeremy.demoulin@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Assyst Europe'), 'CRM : Microsoft Dynamics 365 For Sales', '23/11/2022', (SELECT id_responsable FROM pae.responsables_de_stage WHERE nom = 'Assal'), 1);
INSERT INTO pae.stages(annee_academique, etudiant, entreprise, sujet_de_stage, date_de_signature, responsable_du_stage, version) VALUES ('2022-2023', (SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'arthur.moulin@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'AXIS SRL'), 'Un metier : chef de projet', '19/10/2022', (SELECT id_responsable FROM pae.responsables_de_stage WHERE nom = 'Barn'), 1);
INSERT INTO pae.stages(annee_academique, etudiant, entreprise, sujet_de_stage, date_de_signature, responsable_du_stage, version) VALUES ('2022-2023', (SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'hugo.moulin@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'AXIS SRL'), 'Un metier : chef de projet', '19/10/2022', (SELECT id_responsable FROM pae.responsables_de_stage WHERE nom = 'Barn'), 1);
INSERT INTO pae.stages(annee_academique, etudiant, entreprise, sujet_de_stage, date_de_signature, responsable_du_stage, version) VALUES ('2022-2023', (SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'aurele.mile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'AXIS SRL'), 'Un metier : chef de projet', '19/10/2022', (SELECT id_responsable FROM pae.responsables_de_stage WHERE nom = 'Barn'), 1);
INSERT INTO pae.stages(annee_academique, etudiant, entreprise, sujet_de_stage, date_de_signature, responsable_du_stage, version) VALUES ('2022-2023', (SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'frank.mile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'AXIS SRL'), 'Un metier : chef de projet', '19/10/2022', (SELECT id_responsable FROM pae.responsables_de_stage WHERE nom = 'Barn'), 1);
INSERT INTO pae.stages(annee_academique, etudiant, entreprise, sujet_de_stage, date_de_signature, responsable_du_stage, version) VALUES ('2022-2023', (SELECT id_utilisateur FROM pae.utilisateurs WHERE email = 'axel.dumoulin@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE nom = 'Sopra Steria'), 'sBMS project - Java Development', '17/10/2022', (SELECT id_responsable FROM pae.responsables_de_stage WHERE nom = 'Alvarez Corchete'), 1);


/*-- Requetes du client
-- Comptage du nombre d’utilisateurs.
SELECT COUNT(*) AS nombre_utilisateurs FROM pae.utilisateurs;
-- Comptage du nombre d’entreprises.
SELECT COUNT(*) AS nombre_entreprises FROM pae.entreprises;
-- Comptage du nombre de stages par année académique.
SELECT annee_academique, COUNT(*) AS nombre_stages
FROM pae.stages
GROUP BY annee_academique;
-- Comptage du nombre de contacts par année académique.
SELECT annee_academique, COUNT(*) AS nombre_contacts
FROM pae.utilisateurs
WHERE type = 'étudiant'
GROUP BY annee_academique;
-- Etats (en format lisible par le client) et comptage du nombre de contacts dans chacun des états.
SELECT etat_contact, COUNT(*) AS nombre_etat_contact
FROM pae.contacts
GROUP BY etat_contact;*/

-- New requests for last demo
-- Comptage du nombre d’utilisateurs, par rôle et par année académique
SELECT annee_academique, type, COUNT(*) AS nombre_utilisateurs
FROM pae.utilisateurs
GROUP BY annee_academique, type;
-- Année académique et comptage du nombre de stages par année académique
SELECT annee_academique, COUNT(*) AS nombre_stages
FROM pae.stages
GROUP BY annee_academique;
-- Entreprise, année académique, et comptage du nombre de stages par entreprise et année académique
SELECT e.nom AS entreprise, s.annee_academique, COUNT(*) AS nombre_stages
FROM pae.stages s
         JOIN pae.entreprises e ON s.entreprise = e.id_entreprise
GROUP BY e.nom, s.annee_academique;
-- Année académique et comptage du nombre de contacts par année académique
SELECT annee_academique, COUNT(*) AS nombre_contacts
FROM pae.contacts
GROUP BY annee_academique;
-- États et comptage du nombre de contacts dans chacun des états :
SELECT
    CASE
        WHEN etat_contact = 'accepte' THEN 'Accepté'
        WHEN etat_contact = 'refuse' THEN 'Refusé'
        WHEN etat_contact = 'suspendu' THEN 'Suspendu'
        WHEN etat_contact = 'initie' THEN 'Initié'
        WHEN etat_contact = 'pris' THEN 'Pris'
        WHEN etat_contact = 'plus suivi' THEN 'Non Suivi'
        ELSE etat_contact
        END AS etat_lisible,
    COUNT(*) AS nombre_contacts
FROM pae.contacts
GROUP BY etat_contact;
-- Année académique, états et comptage du nombre de contacts dans chacun des états par année académique
SELECT annee_academique,
       CASE
           WHEN etat_contact = 'accepte' THEN 'Accepté'
           WHEN etat_contact = 'refuse' THEN 'Refusé'
           WHEN etat_contact = 'suspendu' THEN 'Suspendu'
           WHEN etat_contact = 'initie' THEN 'Initié'
           WHEN etat_contact = 'pris' THEN 'Pris'
           WHEN etat_contact = 'plus suivi' THEN 'Non Suivi'
           ELSE etat_contact
           END AS etat_lisible,
       COUNT(*) AS nombre_contacts
FROM pae.contacts
GROUP BY annee_academique, etat_contact;
-- Entreprise, états et comptage du nombre de contacts dans chacun des états par entreprise
SELECT
    e.nom AS entreprise,
    CASE
        WHEN c.etat_contact = 'accepte' THEN 'Accepté'
        WHEN c.etat_contact = 'refuse' THEN 'Refusé'
        WHEN c.etat_contact = 'suspendu' THEN 'Suspendu'
        WHEN c.etat_contact = 'initie' THEN 'Initié'
        WHEN c.etat_contact = 'pris' THEN 'Pris'
        WHEN c.etat_contact = 'plus suivi' THEN 'Non Suivi'
        ELSE c.etat_contact
        END AS etat_lisible,
    COUNT(*) AS nombre_contacts
FROM pae.contacts c
         JOIN pae.entreprises e ON c.entreprise = e.id_entreprise
GROUP BY e.nom, c.etat_contact;
