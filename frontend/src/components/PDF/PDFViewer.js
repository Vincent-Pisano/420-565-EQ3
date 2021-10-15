import React from "react";

const PDFViewer = ({ url, documentName }) => {
  return (
    <iframe
      style={{ width: "100%", height: "100%" }}
      src={url + "#toolbar=1"}
      title={documentName}
    ></iframe>
  );
};

export default PDFViewer;
