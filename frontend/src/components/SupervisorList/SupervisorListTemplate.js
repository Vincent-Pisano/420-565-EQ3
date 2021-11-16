import { Container } from "react-bootstrap";
import Supervisor from "./Supervisor";
import "../../styles/List.css";

function SupervisorListTemplate({title, supervisors, errorMessage, onClick}) {

  return (
    <Container className="cont_principal">
      <Container className="cont_list_centrar">
        <h2 className="cont_title_form">{title}</h2>
        <Container className="cont_list">
          <p>{errorMessage}</p>
          <ul>
            {supervisors.map((supervisor) => (
              <Supervisor
                key={supervisor.id}
                supervisor={supervisor}
                onClick={onClick}
              />
            ))}
          </ul>
        </Container>
      </Container>
    </Container>
  );
}

export default SupervisorListTemplate;
