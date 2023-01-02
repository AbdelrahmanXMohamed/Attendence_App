import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { StatusModel } from 'src/app/models/StatusModel';
import { StatusService } from 'src/app/services/status/status.service';

@Component({
  selector: 'app-report-of-current-week',
  templateUrl: './report-of-current-week.component.html',
  styleUrls: ['./report-of-current-week.component.css']
})
export class ReportOfCurrentWeekComponent implements OnInit {

  userId:number;
  report:StatusModel[] = [];

  constructor(private statusService:StatusService,private route: ActivatedRoute){

  }



  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.userId = <number>(<unknown> window.location.href.charAt(29));     
    });
    this.getUserReport();

  }

  getUserReport(){
    this.statusService.getReportForCurrentWeekForUser(this.userId).subscribe((result:StatusModel[]) =>{
       this.report = result;      
    })
  }

}
