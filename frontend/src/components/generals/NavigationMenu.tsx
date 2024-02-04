import React, {FC} from 'react';
import {Menu} from 'antd';
import {
     UserAddOutlined, UserOutlined
} from '@ant-design/icons';
import './styles/NavMenu.css';
import {Route, Routes} from 'react-router-dom';

interface Props {
    collapsed: boolean;
}

const NavigationMenu: FC<Props> = ({ collapsed }) => {

    return (
            <div style={{width: 256, height:"100vh"}} className="navigationMenu">
                <Menu
                    defaultSelectedKeys={['1']}
                    mode="inline"
                    theme="light"
                    inlineCollapsed={collapsed}
                    style={{height:"100%"}}
                >

                    <Menu.Item key="1" icon={<UserOutlined/>}>Вход</Menu.Item>
                    <Menu.Item key="2" icon={<UserAddOutlined/>}>Регистрация</Menu.Item>

                </Menu>
                <Routes>
                    <Route path="/" element={<></>}/>
                </Routes>

            </div>
    );

};

export default NavigationMenu;