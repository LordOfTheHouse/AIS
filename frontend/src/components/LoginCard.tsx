import React, {FC} from "react";
import {UserOutlined} from "@ant-design/icons";
import {Card} from "antd";


export const LoginCard: FC = () => {
    return (<Card
        style={{
            width: 130,
            height: 50,
            display: 'flex',
            alignItems: 'center',
            color: "#f0f0f0",
            justifyContent: 'center',
            borderRadius: 50,
            border: '2px solid #429CFF',
            transition: 'background 0.3s',
        }}
        hoverable
    >
        <UserOutlined style={{
            fontSize: 18,
            marginRight: 5,
            border: '2px solid #429CFF',
            borderRadius: 40,
            color: "#429CFF"
        }}/>
        <span style={{
            marginTop: -4,
            fontSize: 18,
            fontFamily: 'Franklin Gothic Medium',
            color: '#429CFF'
        }}>Войти</span>
    </Card>);
}
