import { Link } from "react-router-dom";
import NavDropdown from "react-bootstrap/NavDropdown";

const NavUserGs = () => {
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
        to="/listInternshipApplication/signature"
      >
        Signature d'applications
      </NavDropdown.Item>
      <NavDropdown.Item as={Link} className="nav-item-cust" to="/reports">
        Voir les Rapports
      </NavDropdown.Item>
    </NavDropdown>
  );
};
export default NavUserGs;
