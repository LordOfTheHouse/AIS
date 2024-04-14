
export interface ILesson {
    idLesson: string;
    nameSubject:string;
    start:string;
    groupName:string;
    teacherName:string;
    typeSubject:string;
    topic: string;
}

export interface IStudent {
    studentId: string;
    studentName: string;
    mark: number|null;
    present: boolean;
}

export interface IStartLectureRq {
    groupName: string;
    startLecture: string;
    topic: string;
}

export interface IGetStudentsRq {
    idLecture: string;
    groupName: string;
}

export interface LessonState {
    lesson: ILesson[];
    students: IStudent[];
}