import React, {FC, CSSProperties} from "react";
import {Button, Card, Form, Image, Input, message, Row} from "antd";
import {Link, useNavigate} from "react-router-dom";
import {useDispatch} from "react-redux";
import {LockOutlined, UserOutlined} from "@ant-design/icons";
import './styles/AuthPage.css'
import authService from '../services/authService'
import {ILogin} from "../types/types";

/**
 * Страница аутентификации пользователя
 * @constructor
 */
const AuthPage: FC = () => {
    const [form] = Form.useForm();
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const onFinish = (values: ILogin) => {
        authService.loginUser(values, dispatch).then((user) => {
            console.log(user)
            //dispatch(login(user))
            message.success("Вы успешно вошли в систему! Здравствуйте!")
            navigate("/")
            const reloadTime = 1;
            setTimeout(() => {
                window.location.reload();
            }, reloadTime);

        }, (error) => {
            const _content = (error.response && error.response.data) || error.message || error.toString();
            console.log(_content);
            message.error("Неверно указан логин или пароль!")
        });
    };

    const cardStyle: CSSProperties = {
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        paddingLeft: '10%',
        paddingRight: '10%',
        minWidth: '300px',
        width: '10%',
        height: '10%',
        borderWidth: '3px',
    };

    const h2Style: CSSProperties = {
        fontSize: '36px',
        marginBottom: '20px',
        width:"200px",
        textAlign: 'center',
    };

    const buttonStyle: CSSProperties = {
        backgroundColor: '#0e4acb',
        color: '#FFFFFF',
        width: '100%',
    };

    return (
        <Row className="authPage">
            <Card style={cardStyle}>
                <div style={{display: 'flex'}}>
                    <div>
                        <h2 style={h2Style}>
                            Вход
                        </h2>
                        <Form
                            name="normal_login"
                            form={form}
                            layout="vertical"
                            onFinish={onFinish}
                        >
                            <Form.Item
                                name="username"
                                rules={[{required: true, message: 'Пожалуйста, введите логин!'}]}
                            >
                                <Input style={{borderRadius:50}} prefix={<UserOutlined/>} placeholder="Логин"/>
                            </Form.Item>
                            <Form.Item
                                name="password"
                                rules={[{required: true, message: 'Пожалуйста, введите пароль!'}]}
                            >
                                <Input.Password style={{borderRadius:50}} prefix={<LockOutlined/>} placeholder="Пароль"/>
                            </Form.Item>
                            <Form.Item>
                                <Button type="primary" htmlType={"submit"} shape="round" size="large" style={buttonStyle}>
                                    Войти
                                </Button>
                            </Form.Item>
                        </Form>
                        <p style={{fontSize: '18px', textAlign: 'center'}}>Забыли пароль? <Link
                            to="/forgot">Восстановите</Link></p>
                    </div>
                </div>
            </Card>
        </Row>
    );
};

export default AuthPage;