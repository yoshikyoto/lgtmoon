import { FC, useState } from "react";
import styled from "styled-components";
import { Image } from "../types";

const Component = styled.div`
  column-count: 2;
  column-gap: 2px;
`

const ImageComponent = styled.div`
  width: 100%;
  cursor: pointer;
`

const ImageDom = styled.img`
  width: 100%;
`

const OverlayComponent = styled.div`
  position: fixed;
  display: flex;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;

  // モーダルを中央に配置したい
  // space-around で水平方向で中央に配置
  justify-content: space-around;
  // center で垂直方向で中央にくるように
  align-items: center;
  // see: https://www.webcreatorbox.com/tech/css-flexbox-cheat-sheet
`

const Overlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: #000;
  opacity: .5;
  z-index: 100;
`

const Modal = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #fff;
  opacity: 1;
  z-index: 200;
  padding: 20px 10px 40px 10px;
  width: 480px;
`

const ModalImage = styled.img`
`

const ModalHeading = styled.h3`
  margin-top: 20px;
  margin-bottom: 5px;
`

const ModalTextBox = styled.input`
  width: 90%;
`


export const ImagesComponent: FC = () => {
  const [selectedImage, setSelectedImage] = useState<Image | null>(null)
  const images: Array<Image> = [
    {
      id: 3,
      url: "https://image.lgtmoon.dev/180762",
    },
    {
      id: 2,
      url: "https://image.lgtmoon.dev/180762",
    },
    {
      id: 1,
      url: "https://image.lgtmoon.dev/180762",
    }
  ]
  return (
    <Component>
      {selectedImage !== null &&

        <OverlayComponent>
          <Overlay onClick={e => setSelectedImage(null)}></Overlay>
          <Modal>
            <ModalImage src="https://image.lgtmoon.dev/180762" />
            <ModalHeading>Image URL</ModalHeading>
            <ModalTextBox type="text" value="aaa"></ModalTextBox>
            <ModalHeading>GitHub Markdown</ModalHeading>
            <ModalTextBox type="text" value="aaaa"></ModalTextBox>
          </Modal>
        </OverlayComponent>
      }

      {images.map(image => (
        <ImageComponent onClick={e => setSelectedImage(image)} key={image.id}>
          <ImageDom src={image.url} />
        </ImageComponent>
      ))}
    </Component>
  )
}
