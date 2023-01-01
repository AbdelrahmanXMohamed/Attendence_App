import { MemberRoutingModule } from './member-routing.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MemberComponent } from './member/member.component';
import { ReportOfCurrentWeekComponent } from './report-of-current-week/report-of-current-week.component';
import { ReportByRangeComponent } from './report-by-range/report-by-range.component';



@NgModule({
  declarations: [
    MemberComponent,
    ReportOfCurrentWeekComponent,
    ReportByRangeComponent
  ],
  imports: [
    CommonModule,
    MemberRoutingModule
  ]
})
export class MemberModule { }
