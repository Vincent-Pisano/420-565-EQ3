import { Link } from "react-router-dom";
import NavDropdown from "react-bootstrap/NavDropdown";
import { URL_INTERNSHIP_OFFER_LIST_OF_MONITOR, URL_INTERNSHIP_OFFER_FORM } from "../../Utils/URL"

const NavUserMonitor = () => {
  return (
    <NavDropdown
      className="nav-drop-cust"
      title={<span className="nav-links-header">Options Moniteur</span>}
      menuVariant="dark"
    >
      <NavDropdown.Item as={Link} to={URL_INTERNSHIP_OFFER_FORM}>
        Dépôt d'offres de stage
      </NavDropdown.Item>
      <NavDropdown.Item as={Link} to={URL_INTERNSHIP_OFFER_LIST_OF_MONITOR}>
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
