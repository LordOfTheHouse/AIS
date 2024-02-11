export interface IUserResponse {
    refresh_token: string;
    accessToken: string;
    id: string;
    username: string;
    group: string;
    firstName:string;
    middleName: string|null;
    lastName:string;
    email: string;
}

export interface ILogin {
    username: string;
    password: string;
}

export interface IUser {
    id: string;
    username: string;
    groupName: string;
    firstName:string;
    middleName: string|null;
    lastName:string;
    email: string;
}
