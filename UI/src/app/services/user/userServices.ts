import { Injectable } from "@angular/core";
import { UserModel } from "../../models/UserModel";
import { HttpClient} from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
    providedIn:"root"
})

export class UserServices{

    constructor(private http:HttpClient){}

    createUser(userRequest :UserModel){
        this.http.post<UserModel>('http://localhost:8080/users',userRequest)

    }

    getUserById(userId:number):Observable<UserModel>{
        return this.http.get<UserModel>(`http://localhost:8080/users/${userId}`);
    }

    getUserByUserName(username:string):Observable<UserModel>{
        return this.http.get<UserModel>(`http://localhost:8080/users/name/${username}`);
    }

}
