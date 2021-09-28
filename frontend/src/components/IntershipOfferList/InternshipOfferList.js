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
                <h2 className="cont_title_form">
                    Liste des offres de stages de votre département
                </h2>
                <Container className="cont_list">
                    <p>{errorMessage}</p>
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
            </div>
        </div>
    )

    /*
            <div className="cont_principal">
            <div className="cont_list_centrar">
                <h2 className="cont_title_form">
                    Étudiants de votre département
                </h2>
                <Container className="cont_list">
                    <p>{errorMessage}</p>
                    <ul>
                        {
                            students.map(student => (
                                <Student key={student.idUser} student={student} />
                            ))
                        }
                    </ul>
                </Container>
            </div>
        </div>
    */
}

export default InternshipOfferList;