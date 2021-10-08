import React from "react";
import { Table } from "react-bootstrap";
import CV from "./CV";
import auth from "../../services/Auth";

const CVTable = ({ cvlist }) => {
  function checkIfEmpty() {
    if (auth.user === undefined || cvlist.length === 0) {
      return (
        <tr>
          <td colSpan="5">Pas de CV</td>
        </tr>
      );
    } else {
      return (
        <>
          {cvlist.map((cv) => (
            <CV key={cvlist.indexOf(cv)} cv={cv} />
          ))}
        </>
      );
    }
  }

  return (
    <Table responsive="md" striped bordered hover variant="dark">
      <thead>
        <tr>
          <th>Nom de fichier</th>
          <th>Téléchargements</th>
          <th>Suppressions</th>
          <th>CV Actif</th>
          <th>CV Valide</th>
        </tr>
      </thead>
      <tbody>{checkIfEmpty()}</tbody>
    </Table>
  );
};

export default CVTable;
