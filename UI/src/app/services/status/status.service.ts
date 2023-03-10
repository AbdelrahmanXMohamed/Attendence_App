import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StatusRequestModel } from 'src/app/models/StatusRequestModel';
import { StatusBetweenTwoDateRequestModel } from 'src/app/models/StatusBetweenTwoDateRequestModel';
import { StatusModel } from 'src/app/models/StatusModel';
import { Status } from 'src/app/models/Status';
import { DatePipe } from '@angular/common';



@Injectable({
  providedIn: 'root'
})
export class StatusService {
  private baseUrl = 'http://localhost:8080'; 

  constructor(private http: HttpClient,private datePipe: DatePipe) { }

  getStatus(): Observable<Status> {
    return this.http.get<Status>(`${this.baseUrl}/status`);
  }


  setStatus(statusRequest: StatusRequestModel): Observable<Status> {
    return this.http.post<any>(`${this.baseUrl}/status`, statusRequest);
  }

  getReportForUser(id: number, dates: StatusBetweenTwoDateRequestModel): Observable<StatusModel[]> {
    const params = new HttpParams()
      .set('startDate', this.datePipe.transform(dates.startDate,'yyyy-MM-dd'))
      .set('endDate', this.datePipe.transform(dates.endDate,'yyyy-MM-dd'));
    return this.http.get<StatusModel[]>(`${this.baseUrl}/status/users-report/${id}`, { params });
  }

  getReportPerDayForUser(userId: number, day: Date): Observable<StatusModel> {
    const params = new HttpParams().set('day', this.datePipe.transform(day,'yyyy-MM-dd'));
    return this.http.get<StatusModel>(`${this.baseUrl}/status/report-per-day-for-users/${userId}`, { params });
  }
  
  getReportPerDayForTeam(teamId: number, day: Date): Observable<StatusModel[]> {
  
    const params = new HttpParams().set('day', this.datePipe.transform(day,'yyyy-MM-dd'));
    return this.http.get<StatusModel[]>(`${this.baseUrl}/status/report-per-day-for-teams/${teamId}`, { params });
  }

  getReportForCurrentWeekForUser(userId: number): Observable<StatusModel[]> {
    return this.http.get<StatusModel[]>(`${this.baseUrl}/status/report-for-current-week-for-users/${userId}`);
  }

  getReportForCurrentWeekForTeam(teamId: number): Observable<StatusModel[]> {
    return this.http.get<StatusModel[]>(`${this.baseUrl}/status/report-for-current-week-for-teams/${teamId}`);
  }
}
