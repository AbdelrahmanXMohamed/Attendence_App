import { StatusService } from './../../../services/status/status.service';
import { StatusRequestModel } from 'src/app/models/StatusRequestModel';
import { UserModel } from 'src/app/models/UserModel';
import { UserServices } from './../../../services/user/userServices';
import { Status } from './../../../models/Status';
import { Component, OnInit } from '@angular/core';
import { ModalDismissReasons, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DailyStatus } from 'src/app/models/DailyStatus';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  title = 'Profile';

  modalRef!: NgbModalRef;
  statusRequest ={} as StatusRequestModel

  userModel:UserModel={
    id: 0,
    name: '',
    username: '',
    password: '',
    email: '',
    phone: ''
  }

  loseResult: string = '';
  status: string = "ABSENCE"

  constructor(private modalService: NgbModal, private userService : UserServices, private statusServices: StatusService) { }
  ngOnInit(): void {
   this.userService.getUserById(1).subscribe(data=>{
      this.userModel=data
    })
  }

  open(content: any) {
    this.modalRef = this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title', backdrop: "static" })
  }
 
  submit() {
    let onsite =document.getElementById('onSite') as HTMLInputElement
    let remote =document.getElementById('remote') as HTMLInputElement
    let day =new Date()
    this.statusRequest.day=day
    this.statusRequest.userId =1
    if(onsite.checked){
      this.statusRequest.status=DailyStatus.ONSITE
    }else if (remote.checked){
      this.statusRequest.status=DailyStatus.REMOTE
    }
    console.log(this.statusRequest)
    this.statusServices.setStatus(this.statusRequest).subscribe(data=>{
      console.log(data)
    })
    

    console.log("submit")
    
     this.modalRef.close()
  }
}
