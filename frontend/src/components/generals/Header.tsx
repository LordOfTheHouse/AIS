import React, {FC} from 'react';
import { Row, Col } from 'antd';
import { ClockCircleOutlined } from '@ant-design/icons';
import './styles/Header.css';

/**
 * Компонент-header
 * @constructor
 */
const Header: FC = () => {;

    return (
        <div className="header">
            <Row gutter={16} align="middle">

                <Col>
                    <p className="header__workTime">
                        <ClockCircleOutlined style={{ fontSize: '16px', paddingRight: '5px' }} />
                        Сегодня с 9:00 до 00:00
                    </p>
                </Col>
            </Row>
        </div>
    );
};

export default Header;