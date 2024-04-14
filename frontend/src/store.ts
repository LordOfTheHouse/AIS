import { configureStore } from "@reduxjs/toolkit";
import authReducer from "./slices/authSlice";
import scheduleReducer from "./slices/scheduleSlice";
import lessonReducer from "./slices/lecture/lectureSlice";

export const store = configureStore({
    reducer: {
        auth: authReducer,
        schedule: scheduleReducer,
        lesson: lessonReducer
    },
});

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch
