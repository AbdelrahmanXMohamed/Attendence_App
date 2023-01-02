import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TeamResponseModel } from 'src/app/models/TeamResponseModel';
import { UserModel } from 'src/app/models/UserModel';
import { TeamService } from 'src/app/services/team/team.service';
import { UserServices } from 'src/app/services/user/userServices';

@Component({
  selector: 'app-members',
  templateUrl: './members.component.html',
  styleUrls: ['./members.component.css']
})
export class MembersComponent implements OnInit {
  title = 'Members';

  modalRef!: NgbModalRef;

  closeResult: string = '';

  users:UserModel[];
  teamId:number;
  constructor(private modalService: NgbModal,private teamService:TeamService,private userService:UserServices,private router: Router) { }
  ngOnInit(): void {
    this.teamId = <number>(<unknown> window.location.href.charAt(28));
    this.getTeamUsers(this.teamId);

  }

  getTeamUsers(teamId:number){
    this.teamService.getAllTeamUsers(teamId).subscribe((result:UserModel[]) => {
      this.users = result;
      console.log(this.users);
      
    });
    
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
  submit(event:any) {
    event.preventDefault();
    this.addUserToTeam(event.target.form.Username.value);
    console.log("submit")
    this.modalRef.close()
  }
  addUserToTeam(username:string){
    this.userService.getUserByUserName(username).subscribe((result => {
      let user:UserModel = result;
      console.log(result);
      
      this.teamService.addUserToTeam(user.id,this.teamId).subscribe((result:TeamResponseModel) =>{
        console.log(result);
        this.getTeamUsers(this.teamId);
        
      })
    }),err => {
      alert("user doesn't exist")
    });
  }
}
