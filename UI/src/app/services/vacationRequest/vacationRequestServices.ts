import { vacationRequestResponse } from '../../models/VacationRequestResponseModel';
import { approveVacationRequest } from '../../models/ApproveRequestVacationModel';
import { VacationRequest } from '../../models/VacationRequestModel';
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { VacationModel } from 'src/app/models/VacationModel';
import { Observable } from 'rxjs';

@Injectable({
    providedIn:"root"
})

export class VacationRequestServices{

    requests: VacationModel[] = []

    requestRespoonse: vacationRequestResponse[]=[]

    constructor(private http:HttpClient){}

    createVacationRequest(id :number, vacationRequest : VacationRequest){
        this.http.post<VacationRequest>("http://localhost:8080/vacations/"+id,vacationRequest).subscribe((result:any) =>{
            
        })
    }

    getAllVacationRequestperTeam(userId:number, teamId:number):VacationModel[]{
        let res= this.http.get<any>("http://localhost:8080/vacations/"+userId+"/"+teamId).subscribe(data=>{
            this.requests=data
        })
        return this.requests
    }

    approveRequest(userId:number , vacationId:number, approveRequest:approveVacationRequest){
        this.http.post<any>("http://localhost:8080/vacations/"+userId+"/"+vacationId,approveRequest).subscribe((result:void) => {

        })
    }

    getAllVacationRequestperUser(userId: number):Observable<vacationRequestResponse[]>{
        return this.http.get<any>("http://localhost:8080/vacations/"+userId);
    }

}
