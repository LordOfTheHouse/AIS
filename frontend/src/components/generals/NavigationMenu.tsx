import React, {useState, FC} from 'react';
import type {MenuProps} from 'antd';
import {Button, Menu} from 'antd';
import {
    ContainerOutlined,
    DesktopOutlined, MenuFoldOutlined, MenuUnfoldOutlined,
    PieChartOutlined
} from '@ant-design/icons';
import './styles/NavMenu.css';
import {Link, Route, Routes} from 'react-router-dom';

type MenuItem = Required<MenuProps>['items'][number];

function getItem(
    label: React.ReactNode,
    key: React.Key,
    icon?: React.ReactNode,
    children?: MenuItem[],
    type?: 'group',
): MenuItem {
    return {
        key,
        icon,
        children,
        label,
        type,
    } as MenuItem;
}

const items: MenuItem[] = [
    getItem('Расписание', '1', <PieChartOutlined/>),
    getItem('Занятие', '2', <DesktopOutlined/>),
    getItem('Успеваемость', '3', <ContainerOutlined/>)
]

const NavigationMenu: FC = () => {
    const [collapsed, setCollapsed] = useState(false);

    const toggleCollapsed = () => {
        setCollapsed(!collapsed);
    };

    return (<>
            <div style={{width: 256}} className="navigationMenu">
                <Button type="primary" onClick={toggleCollapsed} style={{marginBottom: 16}}>
                    {collapsed ? <MenuUnfoldOutlined/> : <MenuFoldOutlined/>}
                </Button>
                <Menu
                    defaultSelectedKeys={['1']}
                    mode="inline"
                    theme="dark"
                    inlineCollapsed={collapsed}
                    items={items}
                />
            </div>
            <Routes>
                <Route path="/" element={<></>}/>
            </Routes>
        </>

    );
};

export default NavigationMenu;