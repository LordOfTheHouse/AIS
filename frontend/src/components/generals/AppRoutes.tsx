import React from 'react';
import { Route, Routes } from 'react-router-dom';
import AuthPage from '../../pages/AuthPage';
import { UserPage } from '../../pages/UserPage';
import {SchedulePage} from "../../pages/SchedulePage";
import LecturePage from "../../pages/LecturePage";
import PerformancePage from "../../pages/PerformancePage";

export const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/auth" element={<AuthPage />} />
            <Route path="/info" element={<UserPage />} />
            <Route path="/schedule" element={<SchedulePage />} />
            <Route path="/lesson" element={<LecturePage />} />
            <Route path="/performance" element={<PerformancePage />} />
        </Routes>
    );
};