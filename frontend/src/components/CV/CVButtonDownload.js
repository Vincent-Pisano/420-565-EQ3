const CVButtonDownload = ({document}) => {
  return (
    <a
        className="btn btn-success btn-sm"              
        download={document.name}
        href={"data:application/pdf;base64," + document.content.data}
    >Télécharger</a>
  );
}
export default CVButtonDownload;