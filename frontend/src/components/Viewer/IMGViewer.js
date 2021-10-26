import React, { useState, useEffect } from "react";
import Viewer from "react-viewer";
import { Container, Button } from "react-bootstrap";
import axios from "axios";

const ImgViewer = ({ username }) => {
  const [visible, setVisible] = useState(false);
  const [url, setUrl] = useState("");

  useEffect(() => {
    axios
      .get(`http://localhost:9090/get/signature/${username}`)
      .then((response) => {
        let blob = new Blob([response.data], { type: "image/png" });
        let uri = URL.createObjectURL(blob);
        setUrl(response.data);
      })
      .catch((error) => {});
  }, [username]);

  return (
    <div>
      <Container className="cont_btn_file">
        <Button
          className="btn_submit"
          onClick={() => {
            setVisible(true);
          }}
        >
          Visualiser la signature
        </Button>
        <img src={url} alt="" width="600px" height="600px" />
      </Container>{" "}
      <Viewer
        visible={visible}
        onClose={() => {
          setVisible(false);
        }}
        images={[{ src: url, alt: "" }]}
      />
    </div>
  );
};

export default ImgViewer;
