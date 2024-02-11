import React from 'react';
import { Route, Routes } from 'react-router-dom';
import AuthPage from '../../pages/AuthPage';
import { UserPage } from '../../pages/UserPage';

export const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/auth" element={<AuthPage />} />
            <Route path="/info" element={<UserPage />} />
        </Routes>
    );
};