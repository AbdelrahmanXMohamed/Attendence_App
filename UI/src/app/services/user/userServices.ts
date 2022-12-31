import { Injectable } from "@angular/core";
import { UserModel } from "../../models/UserModel";
import { HttpClient} from "@angular/common/http";

@Injectable({
    providedIn:"root"
})

export class UserServices{

    constructor(private http:HttpClient){}

    createUser(userRequest :UserModel){
        this.http.post<UserModel>('http://localhost:8080/users',userRequest)

    }

}
