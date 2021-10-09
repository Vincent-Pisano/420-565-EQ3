import { Link } from "react-router-dom";
import NavDropdown from "react-bootstrap/NavDropdown";

const NavUserGs = () => {
  return (
    <NavDropdown
      className="nav-drop-cust"
      title={<span className="nav-links-header">Options GS</span>}
      menuVariant="dark"
      noCaret
    >
      <NavDropdown.Item as={Link} to="/formInternshipOffer">
        Dépôt Offre
      </NavDropdown.Item>
      <NavDropdown.Item as={Link} to="/listInternshipOffer">
        Liste Offres
      </NavDropdown.Item>
      <NavDropdown.Item as={Link} to="/listStudents">
        Liste CV
      </NavDropdown.Item>
      <NavDropdown.Item as={Link} to="/listSupervisors">
        Assignation
      </NavDropdown.Item>
    </NavDropdown>
  );
};
export default NavUserGs;
