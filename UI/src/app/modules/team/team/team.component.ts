import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TeamResponseModel } from 'src/app/models/TeamResponseModel';
import { TeamService } from 'src/app/services/team/team.service';

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.css']
})
export class TeamComponent implements OnInit {
  title = 'Team Page';
  teamId!: number;
  modalRef!: NgbModalRef;
  team:TeamResponseModel;

  closeResult: string = '';

  constructor(private modalService: NgbModal, private route: ActivatedRoute,private teamService:TeamService) { }

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.teamId = params['id'];
    });
    this.getTeam(this.teamId);
  }
  getTeam(teamId:number){
    this.teamService.getTeamById(teamId).subscribe((result:TeamResponseModel) => {
      this.team = result;
    })
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
}
