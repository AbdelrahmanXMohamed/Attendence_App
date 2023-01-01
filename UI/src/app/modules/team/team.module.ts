import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TeamComponent } from './team/team.component';
import { TeamRoutingModule } from './team-routing.module';
import { MembersComponent } from './members/members.component';
import { VacationsRequestComponent } from './vacations-request/vacations-request.component';
import { DasboardComponent } from './dasboard/dasboard.component';



@NgModule({
  declarations: [
    TeamComponent,
    MembersComponent,
    VacationsRequestComponent,
    DasboardComponent
  ],
  imports: [
    CommonModule,
    TeamRoutingModule
  ]
})
export class TeamModule { }
