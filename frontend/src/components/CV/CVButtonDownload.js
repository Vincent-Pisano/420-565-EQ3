const CVButtonDownload = ({ user, cv }) => {
  return (
    <a
      className="btn btn-success btn-sm"
      download
      href={`http://localhost:9090/get/CV/document/${user.idUser}/${cv.id}`}
    >
      Télécharger
    </a>
  );
};
export default CVButtonDownload;
