import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModalRef, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { StatusModel } from 'src/app/models/StatusModel';
import { UserModel } from 'src/app/models/UserModel';
import { StatusService } from 'src/app/services/status/status.service';
import { UserServices } from 'src/app/services/user/userServices';

@Component({
  selector: 'app-member',
  templateUrl: './member.component.html',
  styleUrls: ['./member.component.css']
})
export class MemberComponent implements OnInit {
  title = 'Profile';

  user:UserModel;

  modalRef!: NgbModalRef;

  userId:number;
  closeResult: string = '';
  status: string = "ABSENCE"
  constructor(private modalService: NgbModal,private route: ActivatedRoute,private userService:UserServices,private statusService:StatusService) { }
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.userId = params['id'];
    });
    this.getUser();
    this.getUserStatus();
  }


  open(content: any) {
    this.modalRef = this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title', backdrop: "static" })
    // .result.then((result) => {
    //   this.closeResult = `Closed with: ${result}`;
    //   console.log("opens")
    // }, (reason) => {
    //   this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    //   console.log("close")
    // });
  }


  // private getDismissReason(reason: any): string {
  //   if (reason === ModalDismissReasons.ESC) {
  //     return 'by pressing ESC';
  //   } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
  //     return 'by clicking on a backdrop';
  //   } else {
  //     return `with: ${reason}`;
  //   }
  // }
  submit() {
    console.log("submit")
    // this.modalRef.close()
  }
  getUser(){
    this.userService.getUserById(this.userId).subscribe((result:UserModel) =>{
      this.user = result;
      
    })
  }
  getUserStatus(){
    this.statusService.getReportPerDayForUser(this.userId,new Date()).subscribe((result:StatusModel) =>{  
      this.status = result.status;
    })
  }
}
