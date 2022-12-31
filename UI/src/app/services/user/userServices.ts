import { Injectable } from "@angular/core";
import { UserRequest } from "../../models/userRequestModel";
import { HttpClient} from "@angular/common/http";

@Injectable({
    providedIn:"root"
})

export class UserServices{

    constructor(private http:HttpClient){}

    createUser(userRequest :UserRequest){
        this.http.post<UserRequest>('http://localhost:8080/users',userRequest)

    }

}