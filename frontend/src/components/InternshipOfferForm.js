import axios from "axios";
import React from "react";
import { useState } from "react";
import { useFormFields } from "../lib/hooksLib";
import { useHistory } from "react-router";
import auth from "../services/Auth"
import "../App.css";


const InternshipOfferForm = () => {

    let user = auth.user;
    let history = useHistory();
    let internshipOffer = history.location.state;

    formatDates()

    const [errorMessage, setErrorMessage] = useState('')

    const [fields, handleFieldChange] = useFormFields(
        internshipOffer !== undefined ? internshipOffer :
            {
                jobName: "",
                description: "",
                startDate: "",
                endDate: "",
                weeklyWorkTime: "",
                hourlySalary: "",
                workDays: [],
                address: "",
                city: "",
                postalCode: "",
                workShift: "DAY",
                workField: "COMPUTER_SCIENCE",
                monitor: {}
            }
    );

    const [monitor, setMonitor] = useFormFields({
        name: ""
    });

    function onCreatePost(e) {
        e.preventDefault();

        if (user.username.startsWith('G')) {
            axios
                .get(`http://localhost:9090/get/monitor/${monitor.name}/`)
                .then((response) => {
                    fields.monitor = response.data
                    saveInternshipOffer()
                }
                ).catch((error) => {
                    console.log(error)
                    setErrorMessage("Erreur! Le moniteur est inexistant")
                });
        } else {
            fields.monitor = user
            saveInternshipOffer()
        }
    }

    function saveInternshipOffer() {
        axios
            .post("http://localhost:9090/save/internshipOffer", fields)
            .then((response) => {
                setErrorMessage("L'offre de stage a été sauvegardé")
                console.log(response.data);
            }
            ).catch((error) => {
                console.log(error)
                setErrorMessage("Erreur! L'offre de stage est invalide")
            });
    }

    function validateInternshipOffer() {
        axios
            .post(`http://localhost:9090/save/internshipOffer/validate/${user.username}`, fields)
            .then((response) => {
                setErrorMessage("L'offre de stage a été validé")
                console.log(response.data);
            }
            ).catch((error) => {
                console.log(error)
                setErrorMessage("Erreur lors de la validation")
            });
    }

    function checkIfGS() {
        if (user.username.startsWith('G') && internshipOffer === undefined) {
            return (
                <input
                    id="name"
                    type="text"
                    value={monitor.name}
                    onChange={setMonitor}
                    placeholder="Entrer le nom d'utilisateur du moniteur"
                    className="input_form_sign d_block active_inp_form"
                    required />
            )
        }
    }

    function checkIfValidated() {
        if (user.username.startsWith('G') && internshipOffer !== undefined) {
            return (
                <button className="btn_sign" onClick={() => validateInternshipOffer()}>Valider</button>
            )
        }
    }

    function setpageTitle() {
        if (internshipOffer === undefined) {
            return (
                <h2 className="pt-3">Ajout d'offre de stages</h2>
            )
        }
        else {
            return (
                <h2 className="pt-3">Information sur l'offre de stage</h2>
            )
        }
    }

    function checkIfChecked(checkboxName) {
        return fields.workDays.some(workday => checkboxName === workday)
    }

    function formatDates() {
        if (internshipOffer) {
            internshipOffer.startDate = formatDate(internshipOffer.startDate)
            internshipOffer.endDate = formatDate(internshipOffer.endDate)
        }
    }

    function formatDate(dateString) {
        let date = new Date(dateString)
        let dateFormatted = date.toISOString().split('T')[0]
        return dateFormatted;
    }

    return (
        <div className="cont_principal">
            <div className="cont_centrar cont_form">
                <fieldset disabled={internshipOffer ? "disabled" : ""}>
                    <form onSubmit={onCreatePost}>
                        <div className="cont_tabs_login">
                            {setpageTitle()}
                        </div>
                        <div className="cont_text_inputs">
                            {checkIfGS()}
                            <input
                                id="jobName"
                                type="text"
                                value={fields.jobName}
                                onChange={handleFieldChange}
                                placeholder="Entrer le nom du poste de travail"
                                className="input_form_sign d_block active_inp_form"
                                required />
                            <input
                                id="description"
                                type="text"
                                value={fields.description}
                                onChange={handleFieldChange}
                                placeholder="Entrer une description du poste"
                                className="input_form_sign d_block active_inp_form"
                                required />
                            <div>
                                <p className="labelFields">La date de début du stage</p>
                            </div>
                            <input
                                id="startDate"
                                type="date"
                                value={fields.startDate}
                                onChange={handleFieldChange}
                                placeholder="Entrer la date de début du stage"
                                className="input_form_sign d_block active_inp_form"
                                required />
                            <div>
                                <p className="labelFields">La date de fin du stage</p>
                            </div>
                            <input
                                id="endDate"
                                type="date"
                                value={fields.endDate}
                                onChange={handleFieldChange}
                                placeholder="Entrer la date de fin du stage"
                                className="input_form_sign d_block active_inp_form"
                                required />
                            <input
                                id="weeklyWorkTime"
                                type="number"
                                step=".01"
                                value={fields.weeklyWorkTime}
                                onChange={handleFieldChange}
                                min="0"
                                placeholder="Entrer la quantité d'heures par semaine"
                                className="input_form_sign d_block active_inp_form"
                                required />
                            <input
                                id="hourlySalary"
                                type="number"
                                step=".01"
                                value={fields.hourlySalary}
                                onChange={handleFieldChange}
                                min="0"
                                placeholder="Entrer le salaire par heure"
                                className="input_form_sign d_block active_inp_form"
                                required />
                            <div>
                                <p className="labelFields_checkbox">Les jours de travail</p>
                            </div>

                            <div className="workDays checkboxes">
                                <label htmlFor="Monday">
                                    <input type="checkbox" id="Monday"
                                        name="day1" onChange={handleFieldChange}
                                        defaultChecked={checkIfChecked("Monday")} />
                                    <span>Lundi</span></label>
                                <br />
                                <label htmlFor="Tuesday">
                                    <input type="checkbox" id="Tuesday"
                                        name="day2" onChange={handleFieldChange}
                                        defaultChecked={checkIfChecked("Tuesday")} />
                                    <span>Mardi</span></label>
                                <br />
                                <label htmlFor="Wednesday">
                                    <input type="checkbox" id="Wednesday"
                                        name="day3" onChange={handleFieldChange}
                                        defaultChecked={checkIfChecked("Wednesday")} />
                                    <span>Mercredi</span></label>
                                <br />
                                <label htmlFor="Thursday">
                                    <input type="checkbox" id="Thursday"
                                        name="day4" onChange={handleFieldChange}
                                        defaultChecked={checkIfChecked("Thursday")} />
                                    <span>Jeudi</span></label>
                                <br />
                                <label htmlFor="Friday">
                                    <input type="checkbox" id="Friday"
                                        name="day5" onChange={handleFieldChange}
                                        defaultChecked={checkIfChecked("Friday")} />
                                    <span>Vendredi</span></label>
                                <br />
                                <label htmlFor="Sunday">
                                    <input type="checkbox" id="Sunday"
                                        name="day6" onChange={handleFieldChange}
                                        defaultChecked={checkIfChecked("Sunday")} />
                                    <span>Samedi</span></label>
                                <br />
                                <label htmlFor="Saturday">
                                    <input type="checkbox" id="Saturday"
                                        name="day7" onChange={handleFieldChange}
                                        defaultChecked={checkIfChecked("Saturday")} />
                                    <span>Dimanche</span></label>
                                <br />
                            </div>
                            <input
                                id="address"
                                type="text"
                                value={fields.address}
                                onChange={handleFieldChange}
                                placeholder="Entrer l'adresse de l'entreprise"
                                className="input_form_sign d_block active_inp_form"
                                required />
                            <input
                                id="city"
                                type="text"
                                value={fields.city}
                                onChange={handleFieldChange}
                                placeholder="Entrer la ville de l'entreprise"
                                className="input_form_sign d_block active_inp_form"
                                required />
                            <input
                                id="postalCode"
                                type="text"
                                value={fields.postalCode}
                                onChange={handleFieldChange}
                                placeholder="Entrer le code postal de l'entreprise"
                                className="input_form_sign d_block active_inp_form"
                                minLength="6"
                                maxLength="6"
                                required />
                            <div>
                                <p className="labelFields">Le type d'horaire</p>
                            </div>
                            <select id="workShift"
                                defaultValue={fields.workShift}
                                onChange={handleFieldChange}
                                className="select_form_sign d_block active_select "
                                required>
                                <option value="DAY">Jour</option>
                                <option value="NIGHT">Nuit</option>
                                <option value="FLEXIBLE">Flexible</option>
                            </select>
                        </div>
                        <div>
                            <p className="labelFields">Le type de l'offre de stage</p>
                        </div>
                        <select id="workField"
                            defaultValue={fields.workField}
                            onChange={handleFieldChange}
                            className="select_form_sign d_block active_select "
                            required>
                            <option value="ARCHITECTURE">Architecture</option>
                            <option value="COMPUTER_SCIENCE">Informatique</option>
                            <option value="NURSING">Infirmier</option>
                        </select>
                        <div className="cont_btn" style={{ display: internshipOffer ? 'none' : 'inline-block' }}>
                            <p>{errorMessage}</p>
                            <button className="btn_sign">Confirmer</button>
                        </div>
                    </form>
                </fieldset>
                <div className="cont_btn" >
                    <p>{errorMessage}</p>
                    {checkIfValidated()}
                </div>
            </div>
        </div>
    )
}
export default InternshipOfferForm;
