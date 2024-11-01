import React, { useEffect, useState } from 'react';
import { Table, Input, Button, Select, Switch, InputNumber } from 'antd';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../store';
import lectureService from '../services/lectureService';
import { ILesson, IStudent } from '../slices/lecture/types';
import { CheckOutlined, CloseOutlined } from '@ant-design/icons';

const { Option } = Select;

interface Subject {
    idLesson: string;
    subject: string;
}

type AlignType = 'left' | 'center' | 'right';

const LecturePage = () => {
    const dispatch = useDispatch();
    const [subjects, setSubjects] = useState<Subject[]>([]);
    const [topicValue, setTopicValue] = useState('');
    const [topicVisible, setTopicVisible] = useState(true);
    const [selectedSubject, setSelectedSubject] = useState<Subject>({
        idLesson: '',
        subject: '',
    });
    const [students, setStudents] = useState<IStudent[]>([]);
    const lesson = useSelector((state: RootState) => state.lesson.lesson);
    const user = useSelector((state: RootState) => state.auth.user);
    const columns = [
        {
            title: 'ФИО',
            dataIndex: 'studentName',
            key: 'studentName',
            width: '65%',
            sorter: (a: IStudent, b: IStudent) => a.studentName.localeCompare(b.studentName),
        },
        {
            title: 'Оценка',
            dataIndex: 'mark',
            key: 'mark',
            width: '20%',
            align: 'center' as AlignType,
            sorter: (a: IStudent, b: IStudent) => (a.mark || 0) - (b.mark || 0),
            render: (_: any, record: IStudent) => (
                <>
                    <InputNumber
                        min={1}
                        max={100}
                        value={record.mark}
                        style={{ width: '75px' }}
                        onChange={(value) => handleGradeChange(value, record.studentId)}
                    />
                    {user && user.groupName === "Teacher" &&
                        <Button type="primary" style={{ marginLeft: 10, marginTop:10 }} onClick={() => handleSetMark(record)}>
                        Оценить
                    </Button>}
                </>
            ),
        },
        {
            title: 'Присутствие',
            dataIndex: 'isPresent',
            key: 'isPresent',
            align: 'center' as AlignType,
            sorter: (a: IStudent, b: IStudent) => (a.present ? 1 : 0) - (b.present ? 1 : 0),
            render: (_: any, record: IStudent) => (
                <Switch
                    checked={record.present}
                    onChange={(checked) => handleAttendanceChange(checked, record.studentId)}
                />
            ),
        },
    ];

    useEffect(() => {
        lectureService.getLecture(dispatch).then((les) => {
            if (les && les.length > 0) {
                const subjectsData = les.map((less) => ({
                    idLesson: less.idLesson,
                    subject: `${less.nameSubject} ${less.groupName} ${less.start}`,
                }));
                setSubjects(subjectsData);

                const firstLesson = les[0];
                setSelectedSubject({
                    idLesson: firstLesson.idLesson,
                    subject: `${firstLesson.nameSubject} ${firstLesson.groupName} ${firstLesson.start}`,
                });

                if (firstLesson.idLesson !== '-1' || (user && user.groupName !== "Teacher")) {
                    console.log(user)
                    setTopicVisible(false);
                    lectureService.getStudents({
                        idLecture: firstLesson.idLesson,
                        groupName: firstLesson.groupName
                    }, dispatch).then(studentsLec => {
                        setStudents(studentsLec);
                    });
                    return;
                }
                setTopicVisible(true);
            }
        });
    }, [dispatch]);

    const handleSetMark = (student: IStudent) => {
        if(user && user.groupName !== "Teacher") return;
        const mark = student.mark ? student.mark : 1;
        lectureService.updateMark(selectedSubject.idLesson, student.studentId, mark.toString());
    };

    const handleAttendanceChange = (checked: boolean, studentId: string) => {
        if(user && user.groupName !== "Teacher") return;
        lectureService.updatePresent(selectedSubject.idLesson, studentId);
        if (checked !== students.find((student) => student.studentId === studentId)?.present) {
            const updatedStudents = students.map((student) =>
                student.studentId === studentId ? { ...student, present: checked } : student
            );
            setStudents(updatedStudents);
        }
    };

    const handleGradeChange = (value: number | null, studentId: string) => {
        if(user && user.groupName !== "Teacher") return;
        const newValue = value || 1;

        if (newValue !== students.find((student) => student.studentId === studentId)?.mark) {
            const updatedStudents = students.map((student) =>
                student.studentId === studentId ? { ...student, mark: newValue } : student
            );
            setStudents(updatedStudents);
        }
    };

    const handleStartLecture = () => {
        const startVal = selectedSubject.subject.split(' ');
        if (startVal.length !== 3) return;
        lectureService
            .startLecture({
                groupName: startVal[1],
                startLecture: startVal[2],
                topic: topicValue,
            })
            .then(
                (status) => {
                    console.log(status);
                    setTopicVisible(false);
                    lectureService.getLecture(dispatch);
                },
                () => alert('Ошибка')
            );
    };

    const handleTopicChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        setTopicValue(e.target.value);
    };

    const handleSelectChange = (value: string) => {
        const less = lesson.find(
            (subject) => `${subject.nameSubject} ${subject.groupName} ${subject.start}` === value
        );
        if (less === undefined) return;
        setSelectedSubject({
            idLesson: less.idLesson,
            subject: `${less.nameSubject} ${less.groupName} ${less.start}`,
        });
        if (less.idLesson !== '-1' || (user && user.groupName !== "Teacher")) {
            console.log(less.idLesson)
            lectureService.getStudents(
                {
                    idLecture: less.idLesson,
                    groupName: less.groupName,
                },
                dispatch
            ).then(
                (studentsLec) => {
                    setStudents(studentsLec);
                    setTopicVisible(false);
                },
                () => {
                    setTopicVisible(true);
                }
            );
            return;
        }
    };

    return (
        <div
            style={{
                paddingLeft: '20%',
                paddingRight: '5%',
                paddingTop: '100px',
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                height: '100vh',
            }}
        >
            <Select
                placeholder="Выберите предмет"
                style={{ width: 400, marginBottom: 10 }}
                value={selectedSubject.subject}
                onChange={handleSelectChange}
            >
                {subjects.map((subject) => (
                    <Option key={subject.idLesson} value={subject.subject}>
                        {subject.subject}
                    </Option>
                ))}
            </Select>
            {topicVisible && (
                <Input.TextArea
                    placeholder="Введите тему занятия"
                    rows={4}
                    style={{ marginBottom: 10, width: 400 }}
                    value={topicValue}
                    onChange={handleTopicChange}
                />
            )}
            {topicVisible && (
                <Button type="primary" onClick={handleStartLecture} style={{ marginBottom: 10 }}>
                    Начать занятие
                </Button>
            )}

            <div style={{ flex: 1, overflowY: 'auto', width: '100%' }}>
                <Table columns={columns} dataSource={students} />
            </div>
        </div>
    );
};

export default LecturePage;