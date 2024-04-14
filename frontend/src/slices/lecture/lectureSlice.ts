import {createSlice, PayloadAction} from '@reduxjs/toolkit'
import {LessonState} from "./types";

const initialState: LessonState = {
    lesson: [],
    students: []
};

export const lessonSlice = createSlice({
    name: 'lesson',
    initialState: initialState,
    reducers: {

        setLesson: (state, action: PayloadAction<any>) => {
            state.lesson = action.payload;
            console.log("lesson: "+ action.payload)
        },
        setStudents: (state, action: PayloadAction<any>) => {
            state.students = action.payload;
            console.log("students: "+ action.payload)
        },
        deleteLesson(state) {
            state.lesson = [];
            state.students = [];
        },
    },
})

export const {setLesson, setStudents, deleteLesson} = lessonSlice.actions

export default lessonSlice.reducer