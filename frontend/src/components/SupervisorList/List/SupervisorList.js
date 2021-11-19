import { useEffect, useState } from "react";
import axios from "axios";
import { session } from "../../../Utils/Store";
import { useHistory } from "react-router";
import "../../../styles/List.css";
import { URL_STUDENT_LIST_ASSIGN_SUPERVISOR } from "../../../Utils/URL";
import { GET_ALL_SUPERVISORS } from "../../../Utils/API";
import { TITLE_SUPERVISOR_LIST_OF_SESSION } from "../../../Utils/TITLE";
import { ERROR_NO_SUPERVISOR_SUBSCRIBED_TO_THIS_SESSION } from "../../../Utils/Errors_Utils";
import SupervisorListTemplate from "../SupervisorListTemplate";

function SupervisorList() {
  let history = useHistory();

  const [supervisors, setSupervisors] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");

  let title = TITLE_SUPERVISOR_LIST_OF_SESSION + session;

  useEffect(() => {
    axios
      .get(GET_ALL_SUPERVISORS + session)
      .then((response) => {
        setSupervisors(response.data);
      })
      .catch((err) => {
        setErrorMessage(ERROR_NO_SUPERVISOR_SUBSCRIBED_TO_THIS_SESSION);
      });
  }, [supervisors.length]);

  function showAssignableStudents(supervisor) {
    let state = {
      supervisor: supervisor,
    };
    history.push({
      pathname: URL_STUDENT_LIST_ASSIGN_SUPERVISOR,
      state: state,
    });
  }

  return (
    <SupervisorListTemplate
      title={title}
      supervisors={supervisors}
      errorMessage={errorMessage}
      onClick={showAssignableStudents}
    />
  );
}

export default SupervisorList;
