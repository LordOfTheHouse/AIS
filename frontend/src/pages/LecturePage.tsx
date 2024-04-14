import React, {useEffect, useState} from 'react';
import {Table, Input, Button, Select, Switch, InputNumber} from 'antd';
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "../store";
import lectureService from "../services/lectureService";
import {ILesson, IStudent} from "../slices/lecture/types";
import {CheckOutlined, CloseOutlined} from "@ant-design/icons";

const {Option} = Select;

interface Subject {
    idLesson: string;
    subject: string;
}

type AlignType = "left" | "center" | "right";
const LecturePage = () => {

    const dispatch = useDispatch();
    const [subjects, setSubjects] = useState<Subject[]>([
        {
            "idLesson": "",
            "subject": ""
        }]);
    const [topicValue, setTopicValue] = useState("");
    const [topicVisible, setTopicVisible] = useState(true);
    const [selectedSubject, setSelectedSubject] = useState<Subject>({
        "idLesson": "",
        "subject": ""
    });
    const [students, setStudents] = useState<IStudent[]>([]);
    let lesson = useSelector((state: RootState) => state.lesson.lesson);
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
                        style={{
                            width: "75px",
                            flex: "content",
                            alignItems: "center"
                        }}
                        onChange={(value) => handleGradeChange(value, record.studentId)}
                    />
                    <Button type="primary" style={{marginLeft: 10}}
                            onClick={() => handleSetMark(record)}>Оценить</Button>
                </>
            )
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
            )
        }
    ];

    useEffect(() => {
        lectureService.getLecture(dispatch).then(les => {
            if (les && les.length > 0) {
                const subjectsData = les.map(less => ({
                    idLesson: less.idLesson,
                    subject: `${less.nameSubject} ${less.groupName} ${less.start}`
                }));
                setSubjects(subjectsData);

                const firstLesson = les[0];
                console.log(JSON.stringify(firstLesson));
                setSelectedSubject({
                    idLesson: firstLesson.idLesson,
                    subject: `${firstLesson.nameSubject} ${firstLesson.groupName} ${firstLesson.start}`
                });

                if (firstLesson.idLesson !== "-1") {
                    setTopicVisible(false);
                }
            }
        });


    }, []);

    const handleSetMark = (student: IStudent) => {
        let mark: number = student.mark ? student.mark : 1
        lectureService.updateMark(selectedSubject.idLesson, student.studentId, mark.toString());

    };
    const handleAttendanceChange = (checked: boolean, studentId: string) => {
        console.log(studentId);
        lectureService.updatePresent(selectedSubject.idLesson, studentId)
        if (checked !== students.find(student => student.studentId === studentId)?.present) {
            const updatedStudents = students.map(student =>
                student.studentId === studentId ? {...student, present: checked} : student
            );
            setStudents(updatedStudents);
        }
    };

    const handleGradeChange = (value: number | null, studentId: string) => {
        const newValue = value || 1;

        // Обновляем оценку только если значение изменилось
        if (newValue !== students.find(student => student.studentId === studentId)?.mark) {
            const updatedStudents = students.map(student =>
                student.studentId === studentId ? {...student, mark: newValue} : student
            );
            setStudents(updatedStudents);
        }
    };

    const handleStartLecture = () => {
        let startVal = selectedSubject.subject.split(" ");
        console.log(startVal);
        if (startVal.length !== 3) return;
        lectureService.startLecture({
            "groupName": startVal[1],
            "startLecture": startVal[2],
            "topic": topicValue
        })
            .then(status => {
                console.log(status);
                setTopicVisible(false);
                lectureService.getLecture(dispatch);
            }, () => alert("Ошибка"));

    };

    const handleTopicChange = (e: any) => {
        setTopicValue(e.target.value);
    };

    const handleSelectChange = (value: string) => {

        console.log(value);
        let less = lesson.find(subject =>
                    `${subject.nameSubject} ${subject.groupName} ${subject.start}` === value);
        if (less === undefined) return;
        setSelectedSubject({idLesson: less.idLesson,
                                    subject: `${less.nameSubject} ${less.groupName} ${less.start}`});
        console.log(JSON.stringify(less));
        if (less.idLesson !== "-1") {
            lectureService.getStudents({
                idLecture: less.idLesson,
                groupName: less.groupName
            }, dispatch).then(studentsLec => {
                setStudents(studentsLec);
                setTopicVisible(false);
            }, result => {
                setTopicVisible(true);
                setStudents([])
            });
            return;
        }

    };

    return (
        <div style={{
            paddingLeft: '20%',
            paddingRight: "5%",
            paddingTop: '100px',
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            height: '100vh',
        }}>
            <Select placeholder="Выберите предмет" style={{width: 400, marginBottom: 10}}
                    value={selectedSubject.subject} onChange={handleSelectChange}>
                {subjects.map(subject => <Select.Option key={subject.idLesson}
                                                        value={subject.subject}>{subject.subject}</Select.Option>)}
            </Select>
            {topicVisible &&
                <Input.TextArea placeholder="Введите тему занятия" rows={4} style={{marginBottom: 10, width: 400}}
                                value={topicValue} onChange={handleTopicChange}/>}
            {topicVisible &&
                <Button type="primary" onClick={handleStartLecture} style={{marginBottom: 10}}>Начать занятие</Button>}

            <div style={{flex: 1, overflowY: 'auto', width: '100%'}}>
                <Table columns={columns} dataSource={students}></Table>
            </div>
        </div>
    );
};

export default LecturePage;