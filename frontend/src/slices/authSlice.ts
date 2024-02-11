import {createSlice, PayloadAction} from '@reduxjs/toolkit'
import {IUserResponse} from "../types/types";


const user: IUserResponse | null = JSON.parse(localStorage.getItem('user') || "null");

interface AuthState {
    isLoggedIn: boolean;
    user: IUserResponse | null;
}

const initialState: AuthState = {
    isLoggedIn: Boolean(user),
    user: user || null,
};

export const authSlice = createSlice({
    name: 'auth',
    initialState: initialState,
    reducers: {
        login: (state, action: PayloadAction<any>) => {
            state.isLoggedIn = true;
            console.log(action.payload.access_token)
            if (action.payload.access_token && action.payload.refresh_token) {
                sessionStorage.setItem('user', JSON.stringify(action.payload));
            }
        },
        setUserData: (state, action: PayloadAction<any>) => {
            state.user = action.payload;
            console.log("Login: "+ action.payload)
            if (action.payload.id) {
                localStorage.setItem('user', JSON.stringify(action.payload));
            }
        },
        logout: (state) => {
            state.isLoggedIn = false;
            state.user = null;
            localStorage.removeItem('user');
            sessionStorage.removeItem('user');
            window.location.reload();
        }
    },
})

export const {login, logout, setUserData} = authSlice.actions

export default authSlice.reducer