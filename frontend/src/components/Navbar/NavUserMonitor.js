import { Link } from "react-router-dom";
import NavDropdown from "react-bootstrap/NavDropdown";

const NavUserMonitor = () => {
  return (
    <NavDropdown
      className="nav-drop-cust"
      title={<span className="nav-links-header">Options Moniteur</span>}
      menuVariant="dark"
    >
      <NavDropdown.Item as={Link} to="/formInternshipOffer">
        Dépôt d'offres de stage
      </NavDropdown.Item>
      <NavDropdown.Item as={Link} to="/listInternshipOffer">
        Liste d'offres de stage
      </NavDropdown.Item>

      <NavDropdown.Item
        href={`http://localhost:9090/get/student/evaluation/document`}
        target="_blank"
      >
        Document d'évalution d'étudiant
      </NavDropdown.Item>
    </NavDropdown>
  );
};
export default NavUserMonitor;
