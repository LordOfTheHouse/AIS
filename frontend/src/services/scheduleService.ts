import {Dispatch} from "redux";
import axios from "axios";
import {setSchedule} from "../slices/scheduleSlice";
import authHeader from "./authHeader";
import {ISchedule} from "../types/types"



const API_URL = "/schedule";

const getSchedule = async (isEvenWeek: boolean, dispatch: Dispatch) => {


    const headers = authHeader();
    console.log(headers);
    let detailsResponse = await axios
        .get<ISchedule>(API_URL + `?isEvenWeek=${isEvenWeek}`, {headers});
    console.log(detailsResponse);
    dispatch(setSchedule(detailsResponse.data));
    return detailsResponse.data;
};

const scheduleService = {
    getSchedule
};

export default scheduleService;