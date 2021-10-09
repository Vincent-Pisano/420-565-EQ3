import { Link } from "react-router-dom";
import NavDropdown from "react-bootstrap/NavDropdown";

const NavUserStudent = () => {
  return (
    <NavDropdown
      className="nav-drop-cust"
      title={<span className="nav-links-header">Options Étudiant</span>}
      menuVariant="dark"
      noCaret
    >
      <NavDropdown.Item as={Link} to="/listInternshipOffer">
        Liste d'offres de stage
      </NavDropdown.Item>
    </NavDropdown>
  );
};
export default NavUserStudent;
