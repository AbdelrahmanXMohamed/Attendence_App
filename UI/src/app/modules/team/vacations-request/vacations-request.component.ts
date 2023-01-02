import { Component, OnInit } from '@angular/core';
import { right } from '@popperjs/core';
import { VacationModel } from 'src/app/models/VacationModel';
import { VacationRequest } from 'src/app/models/VacationRequestModel';
import { VacationRequestServices } from 'src/app/services/vacationRequest/vacationRequestServices';

@Component({
  selector: 'app-vacations-request',
  templateUrl: './vacations-request.component.html',
  styleUrls: ['./vacations-request.component.css']
})
export class VacationsRequestComponent implements OnInit {
  
  vacationRequests:VacationModel[] = [];
  teamId:number;
  managerId:number = 1;


  constructor(private vacationService:VacationRequestServices){

  }
  

ngOnInit(): void {
    this.teamId = <number>(<unknown> window.location.href.charAt(28));
    this.getVacationRequests();
    
  }
  getVacationRequests(){
    this.vacationRequests = this.vacationService.getAllVacationRequestperTeam(this.managerId,this.teamId);
  }
  approveRequest(vacationId:number,userId:number){
    console.log("approving");
    
    this.vacationService.approveRequest(this.managerId,vacationId,{approve:1})    
    this.getVacationRequests();
  }
}
