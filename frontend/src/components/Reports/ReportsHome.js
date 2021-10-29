import { Container } from "react-bootstrap";
import Report from "./Report";
import { ReportsList } from "../../Utils/Reports";

const ReportsHome = () => {
  let reportsList = ReportsList();

  return (
    <Container className="cont_principal">
      <Container className="cont_list_centrar">
        <h2 className="cont_title_form">Liste des rapports disponibles</h2>
        <Container className="cont_list">
          <ul>
            {reportsList.map((report) => (
              <Report key={report.id} report={report} />
            ))}
          </ul>
        </Container>
      </Container>
    </Container>
  );
};
export default ReportsHome;
