import React from 'react';
import logo from './logo.svg';
import styled, { createGlobalStyle } from 'styled-components';
import './App.css';
import { RecentImages } from './components/RecentImages';

const AppPage = styled.div`
`

const AppContent = styled.div`
`

const Header = styled.header`
  display: flex;
  flex-direction: column;
`

const TitleComponent = styled.div`
  display: flex;
  flex-direction: row;
  align-items: left;
`

// ikafont の設定が index.css にある
const Title = styled.h1`
  font-family: ikafont;
  margin: 0;
  font-size: 4em;
  
`

const AppBar = styled.div`
  background-color: #def;
  padding: 5px;
  display: flex;
  flex-direction: row;
  gap: 5px;
`

const TextInput = styled.input`
  box-sizing: border-box;
  height: 28px;
  width: 300px;
  border-width: 0;
`

const ButtonBase = styled.button`
  border: 0;
  background: #009;
  color: #fff;
  font-weight: 700;
  cursor: pointer;
`

const SubmitButton = styled(ButtonBase)`
  height: 28px;
  width: 100px;
`

const UploadButton = styled(ButtonBase)`
  height: 28px;
  width: 200px;
`

const TabGroup = styled.div`
 display: flex;
 flex-direction: row;
 justify-content: space-between;
 width: 100%;
`

const Tab = styled.div`
  cursor: pointer;
  font-weight: 700;
  font-size: 1.5em;
  text-align: center;
  width: 100%;
  padding: 10px 0;

  &:hover {
    background-color: #ccf;
    color: #000;
  }
`

const SelectedTab = styled(Tab)`
  background-color: #009;
  color: #fff;
`

function App() {
  return (
    <AppPage>
      <AppContent>
        <Header>
          <TitleComponent>
            <Title>LGTMoon</Title>
          </TitleComponent>
          <AppBar>
            <TextInput type="text"></TextInput>
            <SubmitButton>検索</SubmitButton>
            <UploadButton>画像アップロード</UploadButton>
          </AppBar>
          <TabGroup>
            <SelectedTab>最近の画像</SelectedTab>
            <Tab>ランダム</Tab>
            <Tab>お気に入り</Tab>
            <Tab>使い方</Tab>
          </TabGroup>
        </Header>
        <RecentImages></RecentImages>
      </AppContent>
    </AppPage>
  );
}

export default App;
