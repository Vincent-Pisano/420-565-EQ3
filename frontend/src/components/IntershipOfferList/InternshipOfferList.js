import React, { useEffect, useState } from 'react'
import axios from 'axios'
import auth from "../../services/Auth"
import { useHistory } from "react-router";
import InternshipOffer from './InternshipOffer'
import "../../styles/List.css"
import { Container } from 'react-bootstrap';


function InternshipOfferList() {

    let history = useHistory();

    const [internshipOffers, setInternshipOffers] = useState([])
    const [errorMessage, setErrorMessage] = useState('')

    useEffect(() => {
        if (auth.user.username.startsWith('G')) {
            axios.get(`http://localhost:9090/getAll/internshipOffer`)
                .then(response => {
                    console.log(response.data)
                    setInternshipOffers(response.data)
                }).catch(err => {
                    setErrorMessage("Aucune Offre de stage n'a été validé pour le moment");
                    console.log(err)
                })
        }
        else {
            axios.get(`http://localhost:9090/getAll/internshipOffer/${auth.user.department}`)
                .then(response => {
                    console.log(response.data)
                    setInternshipOffers(response.data)
                }).catch(err => {
                    setErrorMessage("Aucune Offre de stage n'a été validé pour le moment");
                    console.log(err)
                })
        }
    }, [])

    function showInternshipOffer(internshipOffer) {
        console.log(internshipOffer);
        history.push({
            pathname: "/formInternshipOffer",
            state: internshipOffer
        });
    }

    function showList() {
        let pageTitle;
        if (auth.user.username.startsWith('G')) {
            pageTitle = "Liste des offres de stages non validées";
        }
        else {
            pageTitle = "Liste des offres de stages de votre département";
        }
        return (
            <Container className="cont_principal">
                <Container className="cont_list_centrar">
                    <h2 className="cont_title_form">
                        {pageTitle}
                    </h2>
                    <Container className="cont_list">
                        <p className="cont_title_form">{errorMessage}</p>
                        <ul>
                            {
                                internshipOffers.map(internshipOffer => (
                                    <InternshipOffer
                                        key={internshipOffer.idOffer}
                                        internshipOffer={internshipOffer}
                                        onToggle={showInternshipOffer} />
                                ))
                            }
                        </ul>
                    </Container>
                </Container>
            </Container>

        );
    }

    return (
        <>
            {showList()}
        </>
    )
}

export default InternshipOfferList;