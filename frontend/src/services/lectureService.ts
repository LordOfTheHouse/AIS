import {Dispatch} from "redux";
import authHeader from "./authHeader";
import axios from "axios";
import {setLesson, setStudents} from "../slices/lecture/lectureSlice";
import {IGetStudentsRq, ILesson, IStartLectureRq, IStudent} from "../slices/lecture/types";

const API_URL = "/lecture";



const getLecture = async (dispatch: Dispatch) => {

    const headers = authHeader();

    let detailsResponse = await axios
        .get<ILesson[]>(API_URL + `/groups/today`, {headers});
    console.log(detailsResponse);
    dispatch(setLesson(detailsResponse.data));
    return detailsResponse.data;
};

const getStudents = async (getStudents: IGetStudentsRq, dispatch: Dispatch) => {
    if( getStudents.groupName === "" || getStudents.idLecture === "") return [];
    const headers = authHeader();

    let detailsResponse = await axios
        .post<IStudent[]>(API_URL + `/getStudentMark`, getStudents, {headers});
    console.log(detailsResponse);
    dispatch(setStudents(detailsResponse.data));
    return detailsResponse.data;
};

const startLecture = async (startLecture: IStartLectureRq) => {
    console.log(startLecture);
    const headers = authHeader();

    let detailsResponse = await axios
        .post<string>(API_URL + "/start", startLecture, {headers});
    console.log(detailsResponse.status);
    return detailsResponse.status;
};

const updatePresent = async (idLecture:string, idStudent:string) => {

    const headers = authHeader();
    console.log(headers);
    let detailsResponse = await axios
        .put<string>(API_URL + `/${idLecture}/groups/${idStudent}/present`, {},{headers});
    console.log(detailsResponse);
    return detailsResponse.data;
};

const updateMark = async (idLecture:string, idStudent:string, mark:string) => {

    const headers = authHeader();
    let detailsResponse = await axios
        .put<string>(API_URL + `/${idLecture}/groups/${idStudent}?mark=${mark}`, {},{headers});
    console.log(detailsResponse);
    return detailsResponse.data;
};

const lectureService = {
    getLecture,
    startLecture,
    getStudents,
    updateMark,
    updatePresent
};

export default lectureService;