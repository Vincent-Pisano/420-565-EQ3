import React from 'react'
import auth from "../../services/Auth"
import "../../styles/List.css"
import { Container } from 'react-bootstrap';
import CVButton from "./CVButton"
import CVTable from "./CVTable"


const CVList = () => {

    let user = auth.user ;

    function checkIfEmpty() {
        if (auth.user === undefined || user.cvlist.length === 0) {
            return <p>Pas de CV couillon</p>
        }
        else {
            return <CVTable cvlist={user.cvlist}/>
        }
    }

    return (
        <Container className="cont_list_cv">
            <Container className="cont_list_centrar">
                <h2 className="cont_title_form">
                    Liste de vos CVs
                </h2>
                <CVButton/>
                <Container className="cont_list">
                    {checkIfEmpty()}
                </Container>
            </Container>
        </Container>
    )
}

export default CVList;