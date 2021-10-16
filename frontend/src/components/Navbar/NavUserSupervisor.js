import { Link } from "react-router-dom";
import NavDropdown from "react-bootstrap/NavDropdown";

const NavUserSupervisor = () => {
  return (
    <NavDropdown
      className="nav-drop-cust"
      title={<span className="nav-links-header">Options Superviseur</span>}
      menuVariant="dark"
    >
      <NavDropdown.Item as={Link} to="/listStudents">
        Liste des étudiants
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
