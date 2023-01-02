import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileComponent } from './profile/profile.component';
import { ProfileRoutingModule } from './profile-routing.module';
import { VacationsComponent } from './vacations/vacations.component';
import { TeamsComponent } from './teams/teams.component';
import { StatusComponent } from './status/status.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';



@NgModule({
  declarations: [
    ProfileComponent,
    VacationsComponent,
    TeamsComponent,
    StatusComponent
  ],
  imports: [
    CommonModule,
    ProfileRoutingModule,
    NgbModule

  ]
})
export class ProfileModule { }
