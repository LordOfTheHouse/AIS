import {createSlice, PayloadAction} from '@reduxjs/toolkit'
import {IUserResponse} from "../types/types";


interface ISchedule {
    nameSubject:string;
    start:string;
    classroom:string;
    groupName:string;
    teacherName:string;
    typeSubject:string;
};

interface ScheduleState {
    schedule: ISchedule[][] | null;
}

const initialState: ScheduleState = {
    schedule: null,
};

export const scheduleSlice = createSlice({
    name: 'schedule',
    initialState: initialState,
    reducers: {

        setSchedule: (state, action: PayloadAction<any>) => {
            state.schedule = [...action.payload, []];
            console.log("schedule: "+ action.payload)
        },
        deleteSchedule(state) {
            state.schedule = null;
        }

    },
})

export const {setSchedule, deleteSchedule} = scheduleSlice.actions

export default scheduleSlice.reducer