import React, {useState} from "react";
import {Card, Image, Upload, Form, Input, Button, UploadFile, UploadProps, GetProp} from "antd";
import headerUser from "../img/headerUser.png";
import {useSelector} from "react-redux";
import {RootState} from "../store";

export const UserPage: React.FC = () => {
    const user = useSelector((state: RootState) => state.auth.user);

    const onFinish = (values: any) => {
        console.log("Received values of form: ", values);
    };

    return (
        <div style={{paddingLeft: "15%", display: "flex", justifyContent: "center", paddingTop: "60px"}}>
            {user &&
                <div style={{width: "100%", position: "relative"}}>
                    <Image src={headerUser} style={{width: "100%", minHeight: "130px"}}/>
                    <div style={{
                        display: "flex",
                        justifyContent: "center",
                        position: "absolute",
                        top: "40%",
                        left: "15%"
                    }}>
                        <Card
                            style={{width: 300, margin: 20, textAlign: "center"}}
                            cover={<Image src={headerUser} style={{height: "256px"}}/>}
                        >
                            <div style={{paddingTop: "5%", fontSize: "20px", fontWeight: "bold"}}>
                                <div>
                                    {user.lastName}
                                </div>
                                <div>
                                    {user.firstName + " " + user.middleName}
                                </div>
                            </div>
                        </Card>
                        <Card title="Настройки" style={{width: 600, margin: 20}}>
                            <Form onFinish={onFinish} initialValues={user}>
                                <Form.Item name="lastName" label="Фамилия">
                                    <Input/>
                                </Form.Item>
                                <Form.Item name="middleName" label="Отчество">
                                    <Input/>
                                </Form.Item>
                                <Form.Item name="firstName" label="Имя">
                                    <Input/>
                                </Form.Item>
                                <Form.Item name="groupName" label="Группа">
                                    <Input/>
                                </Form.Item>
                                <Form.Item name="email" label="Email">
                                    <Input/>
                                </Form.Item>
                                <Form.Item>
                                    <Button type="primary" htmlType="submit">
                                        Сохранить
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