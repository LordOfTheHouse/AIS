import React from "react";
import { Card, Image, Form, Input, Button } from "antd";
import headerUser from "../img/headerUser.png";
import { useSelector } from "react-redux";
import { RootState } from "../store";

export const UserPage: React.FC = () => {
    const user = useSelector((state: RootState) => state.auth.user);

    const onFinish = (values: any) => {
        console.log("Received values of form: ", values);
    };

    const formItemLayout = {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 6 },
            style: { textAlign: 'left' as const }  // Указываем 'left' как значение типа 'const'
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 18 },
        },
    };

    return (
        <div style={{ paddingLeft: "15%", display: "flex", justifyContent: "center", paddingTop: "60px" }}>
            {user &&
                <div style={{ width: "100%", position: "relative" }}>
                    <Image src={headerUser} style={{ width: "100%", minHeight: "130px" }} />
                    <div style={{
                        display: "flex",
                        justifyContent: "center",
                        position: "absolute",
                        top: "40%",
                        left: "15%"
                    }}>
                        <Card
                            style={{ width: 300, margin: 20, textAlign: "center" }}
                            cover={<Image src={headerUser} style={{ height: "256px" }} />}
                        >
                            <div style={{ paddingTop: "5%", fontSize: "20px", fontWeight: "bold" }}>
                                <div>
                                    {user.lastName}
                                </div>
                                <div>
                                    {user.firstName + " " + user.middleName}
                                </div>
                            </div>
                        </Card>
                        <Card title="Настройки" style={{ width: 600, margin: 20 }}>
                            <Form {...formItemLayout} onFinish={onFinish} initialValues={user}>
                                <Form.Item name="lastName" label="Фамилия">
                                    <Input disabled style={{ width: '100%' }} />
                                </Form.Item>
                                <Form.Item name="middleName" label="Отчество">
                                    <Input disabled style={{ width: '100%' }} />
                                </Form.Item>
                                <Form.Item name="firstName" label="Имя">
                                    <Input disabled style={{ width: '100%' }} />
                                </Form.Item>
                                <Form.Item name="groupName" label="Группа">
                                    <Input disabled style={{ width: '100%' }} />
                                </Form.Item>
                                <Form.Item name="email" label="Email">
                                    <Input disabled style={{ width: '100%' }} />
                                </Form.Item>
                                <Form.Item wrapperCol={{ span: 24 }}>
                                    <Button type="primary" htmlType="submit">
                                        Изменить
                                    </Button>
                                </Form.Item>
                            </Form>
                        </Card>
                    </div>
                </div>
            }
        </div>
    );
};

export default UserPage;
