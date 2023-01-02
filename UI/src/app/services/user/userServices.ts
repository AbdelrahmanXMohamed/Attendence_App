import { Observable } from 'rxjs';
import { UserModel } from 'src/app/models/UserModel';
import { Injectable } from "@angular/core";
import { HttpClient} from "@angular/common/http";

@Injectable({
    providedIn:"root"
})

export class UserServices{
    userModel:any
    id :number | undefined

    constructor(private http:HttpClient){}

    createUser(userRequest :UserModel){
        this.http.post<UserModel>('http://localhost:8080/users',userRequest)

    }

    getUserById(id:number):Observable<UserModel>{
       return this.http.get<any>("http://localhost:8080/users/"+id)

    }

}
