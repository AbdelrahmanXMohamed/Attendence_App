import { StatusComponent } from './status/status.component';
import { TeamsComponent } from './teams/teams.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProfileComponent } from './profile/profile.component';
import { VacationsComponent } from './vacations/vacations.component';

const routes: Routes = [
  {
    path: "", component: ProfileComponent, children:
      [
        { path: "", component: StatusComponent },
        { path: "vacations", component: VacationsComponent },
        { path: "teams", component: TeamsComponent }
      ]
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule { }
