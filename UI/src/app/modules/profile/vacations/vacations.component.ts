import { VacationRequestServices } from './../../../services/vacationRequest/vacationRequestServices';
import { Component, TemplateRef, OnInit } from '@angular/core';
import { NgbModal, ModalDismissReasons, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { vacationRequestResponse } from 'src/app/models/VacationRequestResponseModel';
import { VacationRequest } from 'src/app/models/VacationRequestModel';
import { VacationType } from 'src/app/utils/vacationType';


@Component({
  selector: 'app-vacations',
  templateUrl: './vacations.component.html',
  styleUrls: ['./vacations.component.css']
})
export class VacationsComponent implements OnInit {
  title = 'Vacations';
  modalRef!: NgbModalRef;
  closeResult: string = '';
   startDate=new Date()
   endDate=new Date()
   comment :string ='' ;
   vacationrequest={} as VacationRequest;
  requestRespoonse: vacationRequestResponse[]=[]

  constructor(private modalService: NgbModal, private vacationRequestServices: VacationRequestServices) {}

  ngOnInit(): void {
    this.vacationRequestServices.getAllVacationRequestperUser(1).subscribe(data=>{
      this.requestRespoonse=data
    })
  }

  open(content: any) {
    this.modalRef = this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title', backdrop: "static" })
  }

  submit() {
    this.vacationrequest.comment=this.comment
    this.vacationrequest.endDate=this.endDate
    this.vacationrequest.startDate=this.startDate
    let causal = document.getElementById('CASUAL') as HTMLInputElement
    let planned =document.getElementById("PLANNED") as HTMLInputElement
    if(causal.checked ){
      this.vacationrequest.type=VacationType.CASUAL
    }else if(planned.checked){
      this.vacationrequest.type=VacationType.PLANNED
    }
    console.log(this.startDate)
    this.vacationRequestServices.createVacationRequest(1,this.vacationrequest)

    this.modalRef.close()
  }

}
