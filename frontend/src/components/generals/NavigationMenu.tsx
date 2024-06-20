import React, { FC, useState } from 'react';
import { Image, Menu } from 'antd';
import { UserAddOutlined, UserOutlined } from '@ant-design/icons';
import { Link } from 'react-router-dom';
import logo from "../../img/vogu_logo.png";

const NavigationMenu: FC = () => {
    const [isMenuVisible, setMenuVisible] = useState(true);

    const toggleMenu = () => {
        setMenuVisible(!isMenuVisible);
    };

    return (
        <div style={{ width: "15%", height: "100vh", position: "fixed", top: 0, left: 0, zIndex: 2, minWidth: 200}}>
            {isMenuVisible &&
                <Menu
                    defaultSelectedKeys={['0']}
                    mode="inline"
                    theme="light"
                    style={{ height: "100%" }}
                >
                    <Menu.Item key="0" onClick={toggleMenu} style={{ height: " 60px", backgroundColor: "rgba(66, 156, 255, 0.7)", minWidth:180 }}>
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
                        <Link to="/lesson">Лекции</Link>
                    </Menu.Item>
                    <Menu.Item key="4" icon={<UserAddOutlined />}>
                        <Link to="/performance">Успеваемость</Link>
                    </Menu.Item>
                </Menu>
            }
            {!isMenuVisible &&
                <div onClick={toggleMenu}>
                    <p>Show Menu</p>
                </div>
            }
        </div>
    );
};

export default NavigationMenu;