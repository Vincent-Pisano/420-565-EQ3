import React, { useEffect, useState } from 'react'
import { useHistory } from "react-router";
import axios from 'axios'
import InternshipOffer from './InternshipOffer'
import auth from "../services/Auth"
import "../App.css"


function InternshipOfferList() {

    let history = useHistory();

    const [internshipOffers, setInternshipOffers] = useState([])
    const [errorMessage, setErrorMessage] = useState('')

    useEffect(() => {
        axios.get(`http://localhost:9090/getAll/internshipOffer/${auth.user.department}`)
            .then(response => {
                console.log(response.data)
                setInternshipOffers(response.data)
            }).catch(err => {
                setErrorMessage("Aucune Offre de stage n'a été validé pour le moment");
                console.log(err)
            })
    }, [])

    function showInternshipOffer(internshipOffer) {
        console.log(internshipOffer);
        history.push({
            pathname: "/formInternshipOffer",
            state: internshipOffer
        });
    }

    return (
        <div className="cont_principal">
            <div className="cont_list_centrar">
                <h2>Liste des offres de stages de votre département</h2>
                <div className="cont_list">
                    <p>{errorMessage}</p>
                    <ul>
                    {
                        internshipOffers.map(internshipOffer => (
                            <InternshipOffer 
                            key={internshipOffer.idOffer} 
                            internshipOffer={internshipOffer}
                            onToggle={showInternshipOffer}/>
                        ))
                    }
                    </ul>
                </div>
            </div>
        </div>
    )
}

export default InternshipOfferList;