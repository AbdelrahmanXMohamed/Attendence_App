import { vacationRequestResponse } from '../../models/vacationRequestResponseModel';
import { approveVacationRequest } from '../../models/approveRequestVacationModel';
import { VacationRequest } from '../../models/vacationRequestModel';
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({
    providedIn:"root"
})

export class VacationRequestServices{

    requests: VacationRequest[] = []

    requestRespoonse: vacationRequestResponse[]=[]

    constructor(private http:HttpClient){}

    createVacationRequest(id :number, vacationRequest : VacationRequest){
        this.http.post<VacationRequest>("http://localhost:8080/vacations/"+id,vacationRequest)
    }

    getAllVacationRequestperTeam(userId:number, teamId:number):VacationRequest[]{
        let res= this.http.get<any>("http://localhost:8080/vacations/"+userId+"/"+teamId).subscribe(data=>{
            this.requests=data
        })
        return this.requests
    }

    approveRequest(userId:number , vacationId:number, approveRequest:approveVacationRequest){
        this.http.post<any>("http://localhost:8080/vacations/"+userId+"/"+vacationId,approveRequest)
    }

    getAllVacationRequestperUser(userId: number):vacationRequestResponse[]{
        let res =this.http.get<any>("http://localhost:8080/vacations/"+userId).subscribe(data=>{
            this.requestRespoonse=data
        })
        return this.requestRespoonse
    }

}