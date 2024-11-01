import React, {FC, useState} from "react";
import {Avatar, Dropdown, Menu} from "antd";
import {UserOutlined} from "@ant-design/icons";
import {Link, useNavigate} from "react-router-dom";
import {LoginCard} from "../LoginCard";
import {Header} from "antd/es/layout/layout";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "../../store";
import authService from "../../services/authService";


export const HeaderMenu: FC = () => {
    const navigate = useNavigate();
    const isAuth: boolean = useSelector((store: RootState) => store.auth.isLoggedIn);
    const  dispatch = useDispatch();
    const [menuVisible, setMenuVisible] = useState(false);
    const handleMenuClick = (e: any) => {
        if (e.key === 'logout') {
            authService.logoutUser(dispatch);
            setMenuVisible(!menuVisible);
            console.log('Выход');
        } else if(e.key === 'profile') {
            navigate("/info");
            setMenuVisible(!menuVisible);
        }
    };

    const menu = (
        <Menu onClick={handleMenuClick}>
            <Menu.Item key="profile">Профиль</Menu.Item>
            <Menu.Item key="logout">Выйти</Menu.Item>
        </Menu>
    );
    return <Header
        style={{
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'space-between',
            background: '#f0f0f0',
            color: '#000',
            paddingLeft: 31,
            paddingRight: 31,
            position: 'fixed',
            top: 0,
            width: isAuth?'85%':"100%",
            left: "15%",
            zIndex: 1,
        }}
    >
        <div></div>
        <div>
            {isAuth ? (
                <Dropdown
                    visible={menuVisible}
                    onVisibleChange={setMenuVisible}
                    overlay={menu}
                    trigger={['click']}
                >
                    <Avatar
                        style={{
                            backgroundColor: 'rgba(66, 156, 255, 0.7)',
                            width: 50,
                            height: 50,
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                            cursor: 'pointer',
                        }}
                        icon={<UserOutlined style={{ fontSize: 36 }} />}
                    />
                </Dropdown>
            ) : (
                <Link to="/auth">
                    <LoginCard />
                </Link>

            )}
        </div>
    </Header>
}