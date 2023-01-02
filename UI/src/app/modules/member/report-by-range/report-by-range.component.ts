import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { StatusModel } from 'src/app/models/StatusModel';
import { StatusService } from 'src/app/services/status/status.service';

@Component({
  selector: 'app-report-by-range',
  templateUrl: './report-by-range.component.html',
  styleUrls: ['./report-by-range.component.css']
})
export class ReportByRangeComponent implements OnInit {


  userId:number;
  report:StatusModel[] = [];

  constructor(private statusService:StatusService,private route: ActivatedRoute){

  }
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.userId = <number>(<unknown> window.location.href.charAt(29)); 
    });
  }


  getReport(event:any){

    this.statusService.getReportForUser(this.userId,{startDate:event.target.form.startdate.value,endDate:event.target.form.enddate.value}).subscribe((result:StatusModel[]) => {
      this.report = result;
      console.log(result);
      
    })
  }


}
