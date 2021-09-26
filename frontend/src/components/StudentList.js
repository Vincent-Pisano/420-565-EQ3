import React, { useEffect, useState } from 'react'
import axios from 'axios'
import Student from './Student'
import auth from "../services/Auth"


function ListStudents() {
    const [students, setStudents] = useState([])
    const [errorMessage, setErrorMessage] = useState('')

    useEffect(() => {
        axios.get(`http://localhost:9090/getAll/students/${auth.user.department}`)
            .then(response => {
                setStudents(response.data)
            }).catch(err => {
                setErrorMessage("Aucun étudiant ne s'est inscrit pour le moment");
                console.log(err)
            })
    }, [students.length])

    return (
        <div className="cont_principal">
            <div className="cont_list_centrar">
                <h2>Liste des étudiant dans votre département</h2>
                <div className="cont_list">
                    <p>{errorMessage}</p>
                    <ul>
                    {
                        students.map(student => (
                            <Student key={student.idUser} student={student}/>
                        ))
                    }
                    </ul>
                </div>
            </div>
        </div>
    )
}

export default ListStudents;