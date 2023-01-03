import { Injectable } from "@angular/core";
import { UserModel } from "../../models/UserModel";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { userRequestModel } from "src/app/models/UserRequestModel";
import { LoginModel } from "src/app/models/LoginModel";
import { LoginResponseModel } from "src/app/models/LoginResponseModel";

@Injectable({
    providedIn: "root"
})

export class UserServices {

    constructor(private http: HttpClient) { }

    createUser(userRequest: userRequestModel): Observable<string> {
        return this.http.post<string>('http://localhost:8080/registration', userRequest);

    }

    getUserById(userId: number): Observable<UserModel> {
        return this.http.get<UserModel>(`http://localhost:8080/users/${userId}`);
    }

    getUser(): Observable<UserModel> {
        return this.http.get<UserModel>(`http://localhost:8080/users`);
    }
    getUserByUserName(username: string): Observable<UserModel> {
        return this.http.get<UserModel>(`http://localhost:8080/users/name/${username}`);
    }

    login(user: LoginModel): Observable<LoginResponseModel> {
        console.log(user)
        return this.http.post<LoginResponseModel>('http://localhost:8080/registration/sign-in', user);
    }

}
