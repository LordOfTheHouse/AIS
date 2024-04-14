import React, { useEffect } from "react";
import scheduleService from "../services/scheduleService";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import { setSchedule } from "../slices/scheduleSlice";
import {ISchedule} from "../types/types";
import {Card, Table} from "antd";

function isEvenWeek(): boolean {
    const currentDate = new Date();
    const weekNumber = getWeekNumber(currentDate);
    return weekNumber % 2 === 0;
}

function getWeekNumber(date: Date): number {
    const dayOfWeek = date.getDay();
    const dayOfYear = getDayOfYear(date);
    const weekNumber = Math.ceil(dayOfYear / 7);
    if (dayOfWeek === 0) {
        return weekNumber - 1;
    }
    return weekNumber;
}

function getDayOfYear(date: Date): number {
    const startOfYear = new Date(date.getFullYear(), 0, 1);
    const diff = date.getTime() - startOfYear.getTime();
    return Math.floor(diff / (24 * 60 * 60 * 1000)) + 1;
}
const getToday = (): string => {
    const weekdays = ['Понедельник', 'Вторник', 'Среда', 'Четверг', 'Пятница', 'Суббота', 'Воскресенье'];
    const today = new Date().getDay();
    return weekdays[today === 0 ? 6 : today - 1];
};
export const SchedulePage: React.FC = () => {
    const columns = [
        {
            title: 'Time',
            dataIndex: 'start',
            key: 'start',
        },
        {
            title: 'Subject',
            dataIndex: 'nameSubject',
            key: 'nameSubject',
            render: (text: string, record: ISchedule) => (
                <div>
                    <p>{record.nameSubject}</p>
                    <p>{record.teacherName}</p>
                </div>
            ),
        },
        {
            title: 'Classroom',
            dataIndex: 'classroom',
            key: 'classroom',
        },
    ];

    const dispatch = useDispatch();
    let schedule = useSelector((state: RootState) => state.schedule.schedule);

    useEffect(() => {
        scheduleService.getSchedule(isEvenWeek(), dispatch);
    }, []);
    const weekdays = ['Понедельник', 'Вторник', 'Среда', 'Четверг', 'Пятница', 'Суббота', 'Воскресенье'];
    const today = getToday();

    return (
        <div style={{
            display: 'flex',
            flexDirection: 'row',
            flexWrap: 'wrap',
            justifyContent: 'center',
            paddingLeft: '15%',
            paddingTop: '100px',
            overflowX: 'auto'
        }}>
            {schedule && schedule.map((daySchedule, index) => (
                <Card key={index} title={weekdays[index]} style={{
                    flex: '0 0 300px',
                    marginRight: '20px',
                    marginBottom: '20px',
                    backgroundColor: weekdays[index] === today ? 'rgba(66, 156, 255, 0.7)' : 'inherit'
                }}>
                    <Table columns={columns} dataSource={daySchedule} pagination={false}/>
                </Card>
            ))}
        </div>
    );
};