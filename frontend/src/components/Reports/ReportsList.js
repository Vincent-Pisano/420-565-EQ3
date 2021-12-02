import { Container } from "react-bootstrap";
import Report from "./Report";
import { REPORTS } from "../../Utils/Reports";
import { TITLE_REPORT } from "../../Utils/TITLE";

const ReportsList = () => {
  return (
    <Container className="cont_principal ">
      <Container className="cont_list_centrar my-5">
        <h2 className="cont_title_form">{TITLE_REPORT}</h2>
        <Container className="cont_list">
          <ul>
            {REPORTS.map((report) => (
              <Report key={report.id} report={report} />
            ))}
          </ul>
        </Container>
      </Container>
    </Container>
  );
};
export default ReportsList;
