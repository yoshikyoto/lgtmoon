import { FC } from "react";
import { Image } from "../types";
import { ImagesComponent } from "./ImagesComponent";

export const StaredImages: FC = () => {
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
      <ImagesComponent images={images}></ImagesComponent>
    </>
  )
}
