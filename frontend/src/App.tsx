import React, {useState} from 'react';
import './App.css';
import NavigationMenu from "./components/generals/NavigationMenu";
import HeaderVogu from "./components/generals/HeaderVogu";

function App() {
    const [collapsed, setCollapsed] = useState(false);

    const toggleCollapsed = () => {
        setCollapsed(!collapsed);
    };
  return (
    <div>
      <HeaderVogu setCollapsed={toggleCollapsed}/>
      <NavigationMenu collapsed={collapsed}/>
    </div>
  );
}

export default App;
