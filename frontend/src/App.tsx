import React, {useEffect, useState} from 'react';
import './App.css';
import NavigationMenu from "./components/generals/NavigationMenu";
import {Navigate, Route, Routes} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "./store";
import authService from "./services/authService";
import {Layout} from "antd";
import {Content} from "antd/es/layout/layout";
import {AppRoutes} from "./components/generals/AppRoutes";
import {HeaderMenu} from "./components/generals/HeaderMenu";

function App() {
    const user = useSelector((state: RootState) => state.auth.user);
    const dispatch = useDispatch();
    useEffect(() => {
        const refreshInterval = setInterval(() => {
            refreshToken();
        }, 60 * 14 * 1000);

        return () => clearInterval(refreshInterval);
    }, [user]);
    const refreshToken = () => {
        console.log("Check for refresh");
        const userStr = sessionStorage.getItem("user");

        let userS = null;
        if (userStr) {
            userS = JSON.parse(userStr);
        } else if (localStorage.getItem("user")) {
            authService.logoutUser(dispatch);
        }
        console.log(userStr);
        if (userS) {
            const refresh_token = userS.refresh_token;
            authService.refresh(refresh_token, dispatch)
                .then((userData) => {
                    console.log("Refresh successful", userData);
                })
                .catch((error) => {
                    authService.logoutUser(dispatch);
                    console.error("Error during refresh", error);
                });
        }
    };
    return (

        <Layout>
            <div style={{display: 'flex'}}>
                {user ? <NavigationMenu/> : <Navigate to={"/auth"}/>}
                <div style={{width: '100%'}}>
                    <HeaderMenu/>
                    <Content>
                        <AppRoutes/>
                    </Content>
                </div>
            </div>
            <Routes>
                <Route path="/" element={<></>}/>
            </Routes>
        </Layout>
    );
}

export default App;
