import { Link } from "react-router-dom";
import NavDropdown from "react-bootstrap/NavDropdown";

const NavUserSupervisor = () => {
  return (
    <NavDropdown
      className="nav-drop-cust"
      title={<span className="nav-links-header">Options Superviseur</span>}
      menuVariant="dark"
      noCaret
    >
      <NavDropdown.Item as={Link} to="/listStudents">
        Liste des étudiants
      </NavDropdown.Item>

      <NavDropdown.Item
        download
        href={`http://localhost:9090/get/enterpriseEvaluation/document`}
      >
        Document d'évalution d'entreprise
      </NavDropdown.Item>
    </NavDropdown>
  );
};
export default NavUserSupervisor;