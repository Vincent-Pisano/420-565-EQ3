import React from "react";
import Viewer from "react-viewer";
import { Container, Button } from "react-bootstrap";

const ImgViewer = (image) => {
  console.log(image);

  //const objectURL = URL.createObjectURL(image)
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
        images={[{ src: "https://www.akibagamers.it/wp-content/uploads/2019/12/bakamitai.jpg", alt: "" }]}
      />
    </div>
  );
};

export default ImgViewer;
