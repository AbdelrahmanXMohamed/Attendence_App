import { DasboardComponent } from './dasboard/dasboard.component';
import { VacationsRequestComponent } from './vacations-request/vacations-request.component';
import { MembersComponent } from './members/members.component';
import { TeamComponent } from './team/team.component';

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';


const routes: Routes = [
  {
    path: "", component: TeamComponent,
    children:
      [
        { path: "", component: DasboardComponent },
        { path: "members", component: MembersComponent },
        { path: "vacation-request", component: VacationsRequestComponent }
      ]
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TeamRoutingModule { }
