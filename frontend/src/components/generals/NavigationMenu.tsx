import React, { FC } from 'react';
import { Image, Menu } from 'antd';
import { UserAddOutlined, UserOutlined } from '@ant-design/icons';
import { Link } from 'react-router-dom';
import logo from "../../img/vogu_logo.png";

const NavigationMenu: FC = () => {
    return (
        <div style={{ width: 256, height: "100vh" }}>
            <Menu
                defaultSelectedKeys={['0']}
                mode="inline"
                theme="light"
                style={{ height: "100%" }}
            >
                <Menu.Item disabled={false} style={{ height: " 60px", backgroundColor: "rgba(66, 156, 255, 0.7)" }}>
                    <div style={{ display: 'flex', alignItems: 'center' }}>
                        <Image src={logo} style={{ width: '44px' }} />
                        <p style={{ fontSize: "36px", marginLeft: 10, color: "white" }}>ВоГУ</p>
                    </div>
                </Menu.Item>
                <Menu.Item key="1" icon={<UserOutlined />}>
                    <Link to="/info">Профиль</Link>
                </Menu.Item>
                <Menu.Item key="2" icon={<UserAddOutlined />}>
                    <Link to="/schedule">Расписание</Link>
                </Menu.Item>
                <Menu.Item key="3" icon={<UserAddOutlined />}>
                    <Link to="/lectures">Лекции</Link>
                </Menu.Item>
                <Menu.Item key="4" icon={<UserAddOutlined />}>
                    <Link to="/achievements">Успеваемость</Link>
                </Menu.Item>
            </Menu>
        </div>
    );
};

export default NavigationMenu;