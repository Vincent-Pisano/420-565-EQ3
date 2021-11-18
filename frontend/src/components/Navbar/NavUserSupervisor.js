import { Link } from "react-router-dom";
import NavDropdown from "react-bootstrap/NavDropdown";
import {
  URL_STUDENT_LIST_OF_DEPARTMENT,
  URL_STUDENT_LIST_ASSIGNED_SUPERVISOR,
} from "../../Utils/URL";

const NavUserSupervisor = () => {
  return (
    <NavDropdown
      className="nav-drop-cust"
      title={<span className="nav-links-header">Options Superviseur</span>}
      menuVariant="dark"
    >
      <NavDropdown.Item as={Link} to={URL_STUDENT_LIST_OF_DEPARTMENT}>
        Liste des étudiants du département
      </NavDropdown.Item>

      <NavDropdown.Item as={Link} to={URL_STUDENT_LIST_ASSIGNED_SUPERVISOR}>
        Liste de vos étudiants
      </NavDropdown.Item>

      <NavDropdown.Item
        href={`http://localhost:9090/get/enterprise/evaluation/document`}
        target="_blank"
      >
        Document d'évalution d'entreprise
      </NavDropdown.Item>
    </NavDropdown>
  );
};
export default NavUserSupervisor;
