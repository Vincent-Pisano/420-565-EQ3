import { Link } from "react-router-dom";
import NavDropdown from "react-bootstrap/NavDropdown";

const NavUserMonitor = () => {
  return (
    <NavDropdown
      className="nav-drop-cust"
      title={<span className="nav-links-header">Options Moniteur</span>}
      menuVariant="dark"
      noCaret
    >
      <NavDropdown.Item as={Link} to="/formInternshipOffer">
        Dépôt d'offres de stage
      </NavDropdown.Item>

      <NavDropdown.Item
        download
        href={`http://localhost:9090/get/studentEvaluation/document`}
      >
        Document d'évalution d'étudiant
      </NavDropdown.Item>
    </NavDropdown>
  );
};
export default NavUserMonitor;
