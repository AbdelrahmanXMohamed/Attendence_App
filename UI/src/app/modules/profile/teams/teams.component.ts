import { Component, OnInit,  } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TeamRequestModel } from 'src/app/models/TeamRequestModel';
import { TeamResponseModel } from 'src/app/models/TeamResponseModel';
import { UserModel } from 'src/app/models/UserModel';
import { TeamService } from 'src/app/services/team/team.service';

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent implements OnInit {
  title = 'Teams';

  modalRef!: NgbModalRef;

  closeResult: string = '';

  teams:TeamResponseModel[] = [];
  userId = 1;

  constructor(private modalService: NgbModal,private teamService:TeamService) {
    
  }
  ngOnInit(): void {
    this.getTeams();
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
    const manager : UserModel = {
      id:this.userId,
      name:"",
      username:"",
      password:"",
      email:"",
      phone:""

  };
    const teamRequest:TeamRequestModel = {
      name: event.target.form.name.value,
      description:event.target.form.description.value,
      manager: manager,
    };
    this.createTeam(teamRequest);
    this.modalRef.close()
  }
  getTeams(): void {
    this.teamService.getTeamsOfManager().subscribe((result: TeamResponseModel[]) => {
      this.teams.push(...result);
    });
  }
  createTeam(team:TeamRequestModel){
    this.teamService.createTeam(team).subscribe((result:TeamResponseModel) => {
      this.getTeams();
    });
  }
}
