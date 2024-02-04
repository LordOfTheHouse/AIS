import React, {ChangeEvent, FC, useState} from 'react';
import {Row, Col, Layout, Card} from 'antd';
import {MenuOutlined, UserOutlined} from '@ant-design/icons';
import './styles/Header.css';
import logo from '../../img/vogu_logo.png'
import {Link} from "react-router-dom";
import {Content, Header} from "antd/es/layout/layout";
import Meta from "antd/es/card/Meta";

interface Props {
    setCollapsed: () => void;
}

/**
 * Компонент-header
 * @constructor
 */
const HeaderVogu: FC<Props> = ({setCollapsed}) => {

    // return (<>
    //     <div className="header">
    //         <Row gutter={16} align="middle">
    //             <Col flex="auto">
    //                 <p className="header__menuOpen" onClick={setCollapsed}>
    //                     <MenuOutlined/>
    //                 </p>
    //             </Col>
    //             <Col>
    //                 <p className="header__logo">
    //                     <img src={logo} alt={"ВоГУ.png"} height={"25px"}/>
    //                 </p>
    //             </Col>
    //             <Col push={15}>
    //                 <p className="header__title">
    //                     ВоГУ
    //                 </p>
    //             </Col>
    //         </Row>
    //         <Link to="/info">
    //             <div
    //                 style={{
    //
    //                     width: '40px',
    //                     height: '40px',
    //                     float: 'right',
    //                     backgroundColor: '#EFEFEF',
    //                     display: 'flex',
    //                     justifyContent: 'center',
    //                     borderRadius: '50%',
    //                     marginTop: '10px',
    //                     marginRight: '-30px'
    //                 }}
    //             >
    //                 <UserOutlined style={{float: 'right', fontSize: '32px'}}/>
    //             </div>
    //         </Link>
    //     </div>
    //
    // </>);

    return (
        <Layout className="layout" style={{padding: '0 0'}}>
            <Header style={{
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'space-between',
                background: '#f0f0f0',
                color: '#000',
            }}>
                <p onClick={setCollapsed} style={{right: 20, color: '#000'}}>
                    <MenuOutlined/>
                </p>
                <Link to="/info">
                    <Card
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
                        }} />
                        <span style={{
                            marginTop: -4,
                            fontSize: 18,
                            fontFamily: 'Franklin Gothic Medium',
                            color: '#429CFF'
                        }}>Войти</span>
                    </Card>
                </Link>

            </Header>
        </Layout>);
};

export default HeaderVogu;