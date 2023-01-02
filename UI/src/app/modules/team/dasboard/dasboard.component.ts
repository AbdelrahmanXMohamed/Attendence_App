import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { StatusModel } from 'src/app/models/StatusModel';
import { TeamResponseModel } from 'src/app/models/TeamResponseModel';
import { UserModel } from 'src/app/models/UserModel';
import { StatusService } from 'src/app/services/status/status.service';
import { UserServices } from 'src/app/services/user/userServices';
import { TeamService } from '../../../services/team/team.service'

@Component({
  selector: 'app-dasboard',
  templateUrl: './dasboard.component.html',
  styleUrls: ['./dasboard.component.css']
})
export class DasboardComponent implements OnInit {

  teamId:number;
  userId:number = 1;
  team:TeamResponseModel;
  user:UserModel;

  usersStatus:StatusModel[];

  constructor(private route: ActivatedRoute,private teamService: TeamService,private userService:UserServices,private statusService:StatusService){
    
  }
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.teamId = params['id'];
    });
    this.getTeam(this.teamId);
    this.getUser(this.userId);
    this.getTeamReportForToday();
    
  }
  state: string = "Today"
  getTeamStatusBy(state: string) {
    this.state = state    
    if(this.state === "Today"){
      this.viewDayStatus(); 
    }
    else{
      this.viewWeekStatus();
    }
  }

  viewDayStatus(){
    this.getTeamReportForToday();
  }
  viewWeekStatus(){
    this.getTeamReportForWeek();
  }
  
  getTeam(teamId:number){
    this.teamService.getTeamById(teamId).subscribe((result:TeamResponseModel) => {
      this.team = result;
    })
  }
  getUser(userId:number){
    this.userService.getUserById(userId).subscribe((result:UserModel) =>{
      this.user = result;
    })
  }





  getTeamReportForToday(){
    this.statusService.getReportPerDayForTeam(this.teamId,new Date()).subscribe((result:StatusModel[]) =>{  
      this.usersStatus = result;
    })
  }
  getTeamReportForWeek(){
    this.statusService.getReportForCurrentWeekForTeam(this.teamId).subscribe((result:StatusModel[]) =>{  
      this.usersStatus = result;
    })
  }

  
}
