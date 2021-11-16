import StudentList from "../StudentListTemplate";
import { session } from "../../../Utils/Store";
import AssignSupervisorModal from "../Modal/AssignSupervisorModal";

function StudentListAssignSupervisor() {
  let supervisor =
    isStudentListAssigned && sessionStorage.getItem("supervisor") !== null
      ? JSON.parse(sessionStorage.getItem("supervisor"))
      : undefined;

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [students, setStudents] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");

  const [currentStudent, setCurrentStudent] = useState(undefined);

  const title = "Étudiants de ce département à assigner";

  useEffect(() => {
    axios
      .get(
        `http://localhost:9090/getAll/students/noSupervisor/${supervisor.department}/${session}`
      )
      .then((response) => {
        setStudents(response.data);
        setErrorMessage("");
      })
      .catch((err) => {
        setErrorMessage("Erreur! Aucun étudiant à assigner actuellement");
        setStudents([]);
      });
  }, []);

  function showModal(student) {
    setCurrentStudent(student);
    handleShow();
  }

  return (
    <>
      <StudentList
        title={title}
        students={students}
        errorMessage={errorMessage}
        onClick={showModal}
      />
      <AssignSupervisorModal
        show={show}
        handleClose={handleClose}
        students={students}
        setStudents={setStudents}
        setErrorMessage={setErrorMessage}
        currentStudent={currentStudent}
        supervisor={supervisor}
      />
    </>
  );
}

export default StudentListAssignSupervisor;
