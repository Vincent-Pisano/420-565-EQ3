import { Link } from "react-router-dom";
import NavDropdown from "react-bootstrap/NavDropdown";
import { 
  URL_STUDENT_LIST_CV_TO_VALIDATE, URL_INTERNSHIP_APPLICATION_LIST_ACCEPTED, URL_INTERNSHIP_APPLICATION_LIST_SIGNATURE } from "../../Utils/URL"

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
        to={URL_INTERNSHIP_APPLICATION_LIST_ACCEPTED}
      >
        Liste Applications
      </NavDropdown.Item>
      <NavDropdown.Item as={Link} className="nav-item-cust" to={URL_STUDENT_LIST_CV_TO_VALIDATE}>
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
        to={URL_INTERNSHIP_APPLICATION_LIST_SIGNATURE}
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
