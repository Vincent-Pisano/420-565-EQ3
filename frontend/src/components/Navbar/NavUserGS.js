import { Link } from "react-router-dom";
import NavDropdown from "react-bootstrap/NavDropdown";

const NavUserGs = () => {
  const signButton = () => {
    window.sessionStorage.setItem("signature", true);
  }
  const assignButton = () => {
    window.sessionStorage.setItem("signature", false);
  }
  return (
    <NavDropdown
      className="nav-drop-cust"
      title={<span className="nav-links-header">Options GS</span>}
      menuVariant="dark"
    >
      <NavDropdown.Item
        as={Link}
        className="nav-item-cust"
        to="/formInternshipOffer"
      >
        Dépôt Offre
      </NavDropdown.Item>
      <NavDropdown.Item
        as={Link}
        className="nav-item-cust"
        to="/listInternshipOffer"
      >
        Liste Offres
      </NavDropdown.Item>
      <NavDropdown.Item
        as={Link}
        className="nav-item-cust"
        onClick={assignButton}
        to="/listInternshipApplication"
      >
        Liste Applications
      </NavDropdown.Item>
      <NavDropdown.Item as={Link} className="nav-item-cust" to="/listStudents">
        Liste CV
      </NavDropdown.Item>
      <NavDropdown.Item
        as={Link}
        className="nav-item-cust"
        to="/listSupervisors"
      >
        Assignation
      </NavDropdown.Item>
      <NavDropdown.Item
        as={Link}
        className="nav-item-cust"
        onClick={signButton}
        to="/listInternshipApplication"
      >
        Signature d'applications
      </NavDropdown.Item>
    </NavDropdown>
  );
};
export default NavUserGs;
