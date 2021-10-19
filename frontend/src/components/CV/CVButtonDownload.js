const CVButtonDownload = ({ user, cv }) => {
  return (
    <a
      className="btn btn-success btn-sm"
      href={`http://localhost:9090/get/CV/document/${user.id}/${cv.id}`}
      target="_blank"
      rel="noreferrer"
    >
      Télécharger
    </a>
  );
};
export default CVButtonDownload;
