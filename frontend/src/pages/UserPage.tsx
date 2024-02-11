import React from "react";
import { Card, Image, Upload, Form, Input, Button } from "antd";
import headerUser from "../img/headerUser.png";
import {useSelector} from "react-redux";
import {RootState} from "../store";


export const UserPage: React.FC= () => {
    const user = useSelector((state: RootState) => state.auth.user);
    const onFinish = (values: any) => {
        console.log("Received values of form: ", values);
    };

    return (
        <div style={{ display: "flex", justifyContent: "center" }}>
            {user &&
            <Card
                title="Профиль"
                style={{ width: 300, margin: 20 }}
            >
                <Image src={headerUser} style={{ width: "100%", minHeight: "130px" }} />
                <Form onFinish={onFinish} initialValues={user}>
                    <Form.Item name="lastName" label="Фамилия">
                        <Input />
                    </Form.Item>
                    <Form.Item name="firstName" label="Имя">
                        <Input />
                    </Form.Item>
                    <Form.Item name="groupName" label="Роль">
                        <Input />
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" htmlType="submit">
                            Сохранить
                        </Button>
                    </Form.Item>
                </Form>
            </Card>
            }
            {user &&
            <Card title="Настройки" style={{ width: 300, margin: 20 }}>
                <Form onFinish={onFinish} initialValues={user}>
                    <Form.Item name="lastName" label="Фамилия">
                        <Input />
                    </Form.Item>
                    <Form.Item name="firstName" label="Имя">
                        <Input />
                    </Form.Item>
                    <Form.Item name="groupName" label="Роль">
                        <Input />
                    </Form.Item>
                    <Form.Item name="email" label="Email">
                        <Input />
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" htmlType="submit">
                            Сохранить
                        </Button>
                    </Form.Item>
                </Form>
            </Card>
            }

        </div>
    );
};

export default UserPage;