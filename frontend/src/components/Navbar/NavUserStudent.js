import { Link } from "react-router-dom";
import NavDropdown from "react-bootstrap/NavDropdown";
import { URL_INTERNSHIP_APPLICATION_LIST_OF_STUDENT, URL_INTERNSHIP_OFFER_LIST_OF_DEPARTMENT } from "../../Utils/URL"

const NavUserStudent = () => {
  return (
    <NavDropdown
      className="nav-drop-cust"
      title={<span className="nav-links-header">Options Étudiant</span>}
      menuVariant="dark"
    >
      <NavDropdown.Item as={Link} to={URL_INTERNSHIP_OFFER_LIST_OF_DEPARTMENT}>
        Liste d'offres de stage
      </NavDropdown.Item>
      <NavDropdown.Item as={Link} to={URL_INTERNSHIP_APPLICATION_LIST_OF_STUDENT}>
        Liste de vos applications de stage
      </NavDropdown.Item>
    </NavDropdown>
  );
};
export default NavUserStudent;
