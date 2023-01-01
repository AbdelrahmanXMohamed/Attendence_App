import { MemberComponent } from './member/member.component';
import { ReportByRangeComponent } from './report-by-range/report-by-range.component';
import { ReportOfCurrentWeekComponent } from './report-of-current-week/report-of-current-week.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';


const routes: Routes = [
  {
    path: "", component: MemberComponent,
    children:
      [
        { path: "", component: ReportOfCurrentWeekComponent },
        { path: "report-by-range", component: ReportByRangeComponent },
      ]
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MemberRoutingModule { }
