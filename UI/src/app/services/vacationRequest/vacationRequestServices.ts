
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { approveVacationRequest } from "src/app/models/ApproveRequestVacationModel";
import { VacationRequest } from "src/app/models/VacationRequestModel";
import { vacationRequestResponse } from "src/app/models/VacationRequestResponseModel";

@Injectable({
    providedIn:"root"
})

export class VacationRequestServices{

    requests: vacationRequestResponse[] = []



    constructor(private http:HttpClient){}

    createVacationRequest(id :number, vacationRequest : VacationRequest){
        this.http.post<any>("http://localhost:8080/vacations/" +id, vacationRequest).subscribe(data=>{

        })
    }

    getAllVacationRequestperTeam(userId:number, teamId:number):vacationRequestResponse[]{
        let res= this.http.get<any>("http://localhost:8080/vacations/"+userId+"/"+teamId).subscribe(data=>{
            this.requests=data
        })
        return this.requests
    }

    approveRequest(userId:number , vacationId:number, approveRequest:approveVacationRequest){
        this.http.post<any>("http://localhost:8080/vacations/"+userId+"/"+vacationId,approveRequest)
    }

    getAllVacationRequestperUser(userId: number):Observable< vacationRequestResponse[]>{
        return this.http.get<any>("http://localhost:8080/vacations/"+userId)
    }
}
