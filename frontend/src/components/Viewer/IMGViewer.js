import React from "react";
import Viewer from "react-viewer";
import { Container, Button } from "react-bootstrap";

const ImgViewer = (image) => {
  let blob = new Blob([image.image.data], { type: 'image/png' });

  console.log(blob)

  const objectURL = URL.createObjectURL(blob)
  console.log(objectURL)
  const [visible, setVisible] = React.useState(false);

  return (
    <div>
      <Container className="cont_btn_file">
        <Button
          className="btn_submit"
          onClick={() => {
            setVisible(true);
          }}
        >
          Visualiser le document
        </Button>
      </Container>{" "}
      <Viewer
        visible={visible}
        onClose={() => {
          setVisible(false);
        }}
        images={[{ src: objectURL, alt: "" }]}
      />
    </div>
  );
};

export default ImgViewer;
