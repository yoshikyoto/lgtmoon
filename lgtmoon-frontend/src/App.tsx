import React, { useState } from 'react';
import logo from './logo.svg';
import styled, { createGlobalStyle } from 'styled-components';
import './App.css';
import { RecentImages } from './components/RecentImages';
import { RandomImages } from './components/RandomImages';
import { StaredImages } from './components/StaredImages';
import { Help } from './components/Help';

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

type TabId = "recent"|"random"|"star"|"help"
type TabEntity = {
  id: TabId
  name: string
}

function App() {
  const [tabId, setTabId] = useState<TabId>("recent")
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
            {tabId === "recent" ? <SelectedTab>最近の画像</SelectedTab> : <Tab onClick={e => setTabId("recent")}>最近の画像</Tab>}
            {tabId === "random" ? <SelectedTab>ランダム</SelectedTab> : <Tab onClick={e => setTabId("random")}>ランダム</Tab>}
            {tabId === "star" ? <SelectedTab>お気に入り</SelectedTab> : <Tab onClick={e => setTabId("star")}>お気に入り</Tab>}
            {tabId === "help" ? <SelectedTab>使い方</SelectedTab> : <Tab onClick={e => setTabId("help")}>使い方</Tab>}
          </TabGroup>
        </Header>
        {tabId === "recent" && <RecentImages/>}
        {tabId === "random" && <RandomImages/>}
        {tabId === "star" && <StaredImages/>}
        {tabId === "help" && <Help/>}
      </AppContent>
    </AppPage>
  );
}

export default App;
