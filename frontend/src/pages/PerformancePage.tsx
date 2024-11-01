import React, { FC, useState } from 'react';
import { Card, Select, Row, Col } from 'antd';
import { PieChart, Pie, Cell, Tooltip } from 'recharts';

const { Option } = Select;

const subjects = [
    {
        name: 'Математика РПС-41',
        students: [
            { name: 'Иванов Таня Александрович', grade: 90, attendance: 95 },
            { name: 'Петров', grade: 80, attendance: 85 },
            { name: 'Сидоров', grade: 85, attendance: 90 }
        ]
    },
    {
        name: 'Физика РПС-41',
        students: [
            { name: 'Иванов', grade: 75, attendance: 80 },
            { name: 'Петров', grade: 85, attendance: 90 },
            { name: 'Сидоров', grade: 70, attendance: 75 }
        ]
    },
    // Другие предметы...
];

const PerformancePage: FC = () => {
    const [selectedSubject, setSelectedSubject] = useState<string | null>(null);
    const [selectedStudent, setSelectedStudent] = useState<string | null>(null);

    const handleSubjectChange = (value: string) => {
        setSelectedSubject(value);
        setSelectedStudent(null); // Сбрасываем выбранного студента при изменении предмета
    };

    const handleStudentChange = (value: string) => {
        setSelectedStudent(value);
    };

    const selectedSubjectData = subjects.find(subject => subject.name === selectedSubject);
    const selectedStudentData = selectedSubjectData?.students.find(student => student.name === selectedStudent);

    const gradePercentage = selectedStudentData ? selectedStudentData.grade : 0;
    const attendancePercentage = selectedStudentData ? selectedStudentData.attendance : 0;

    const gradeData = [
        { name: 'Средний балл', value: gradePercentage },
        { name: 'Остальное', value: 100 - gradePercentage },
    ];

    const attendanceData = [
        { name: 'Средняя посещаемость', value: attendancePercentage },
        { name: 'Отсутствие', value: 100 - attendancePercentage },
    ];

    const COLORS_GRADE = ['#1890ff', '#f0f0f0'];
    const COLORS_ATTENDANCE = ['#52c41a', '#ff4d4f'];

    return (
        <div style={{ paddingLeft: '20%', paddingRight: '5%', paddingBottom: '10%', paddingTop: '5%', height: '100vh', display: 'flex', justifyContent: 'center' }}>
            <Card title="Выберите предмет и студента" style={{ width: '80%' }}>
                <Select
                    style={{ width: '100%' }}
                    placeholder="Выберите предмет"
                    onChange={handleSubjectChange}
                >
                    {subjects.map(subject => (
                        <Option key={subject.name} value={subject.name}>
                            {subject.name}
                        </Option>
                    ))}
                </Select>
                {selectedSubject && (
                    <>
                        <Select
                            style={{ width: '100%', marginTop: '20px' }}
                            placeholder="Выберите студента"
                            onChange={handleStudentChange}
                        >
                            {selectedSubjectData?.students.map(student => (
                                <Option key={student.name} value={student.name}>
                                    {student.name}
                                </Option>
                            ))}
                        </Select>
                        {selectedStudent && (
                            <Row justify="center" gutter={16} style={{ marginTop: '20px' }}>
                                <Col span={12}>
                                    <Card title="Средний балл">
                                        <div style={{ display: 'flex', justifyContent: 'center' }}>
                                            <PieChart width={250} height={250}>
                                                <Pie
                                                    data={gradeData}
                                                    dataKey="value"
                                                    nameKey="name"
                                                    cx="50%"
                                                    cy="50%"
                                                    outerRadius={90}
                                                    fill="#8884d8"
                                                    label
                                                >
                                                    {gradeData.map((entry, index) => (
                                                        <Cell key={`cell-${index}`} fill={COLORS_GRADE[index % COLORS_GRADE.length]} />
                                                    ))}
                                                </Pie>
                                                <Tooltip formatter={(value) => `${value}%`} />
                                            </PieChart>
                                        </div>
                                    </Card>
                                </Col>
                                <Col span={12}>
                                    <Card title="Средняя посещаемость">
                                        <div style={{ display: 'flex', justifyContent: 'center' }}>
                                            <PieChart width={250} height={250}>
                                                <Pie
                                                    data={attendanceData}
                                                    dataKey="value"
                                                    nameKey="name"
                                                    cx="50%"
                                                    cy="50%"
                                                    outerRadius={90}
                                                    fill="#8884d8"
                                                    label
                                                >
                                                    {attendanceData.map((entry, index) => (
                                                        <Cell key={`cell-${index}`} fill={COLORS_ATTENDANCE[index % COLORS_ATTENDANCE.length]} />
                                                    ))}
                                                </Pie>
                                                <Tooltip formatter={(value) => `${value}%`} />
                                            </PieChart>
                                        </div>
                                    </Card>
                                </Col>
                            </Row>
                        )}
                    </>
                )}
            </Card>
        </div>
    );
};

export default PerformancePage;
