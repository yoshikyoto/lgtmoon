import { FC, useState } from "react";
import styled from "styled-components";
import { Image } from "../types";
import starIcon from "../images/star-off.png"
import staredIcon from "../images/star-on.png"
import copyIcon from "../images/copy.png"

const ImagesSection = styled.section`
  column-count: 2;
  column-gap: 2px;
`

const ImageDom = styled.img`
  width: 100%;
`

const StarButton = styled.img`
  display: none;
  position: absolute;
  z-index: 10;
  top: 2px;
  right: 2px;
  width: 30px;
  height: 30px;
`

const CopyButton = styled.img`
  display: none;
  position: absolute;
  z-index: 10;
  top: 2px;
  right: 36px;
  width: 30px;
  height: 30px;
`

const ImageComponent = styled.div`
  width: 100%;
  cursor: pointer;
  position: relative;
  &:hover ${StarButton} {
    display: block;
  }
  &:hover ${CopyButton} {
    display: block;
  }
`

const OverlaySection = styled.section`
  position: fixed;
  display: flex;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 100;

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
  width: 420px;
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
    <>
      {selectedImage !== null &&
        <OverlaySection>
          <Overlay onClick={e => setSelectedImage(null)}></Overlay>
          <Modal>
            <ModalImage src="https://image.lgtmoon.dev/180762" />
            <ModalHeading>Image URL</ModalHeading>
            <ModalTextBox type="text" value="aaa"></ModalTextBox>
            <ModalHeading>GitHub Markdown</ModalHeading>
            <ModalTextBox type="text" value="aaaa"></ModalTextBox>
          </Modal>
        </OverlaySection>
      }
      <ImagesSection>
        {images.map(image => (
          <ImageComponent onClick={e => setSelectedImage(image)} key={image.id}>
            <ImageDom src={image.url}/>
            <StarButton src={starIcon}/>
            <CopyButton src={copyIcon}/>
          </ImageComponent>
        ))}
      </ImagesSection>
    </>
  )
}
