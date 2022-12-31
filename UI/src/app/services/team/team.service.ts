import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TeamRequestModel } from 'src/app/models/TeamRequestModel';
import { TeamResponseModel } from 'src/app/models/TeamResponseModel';
import { User } from 'src/app/models/User';

@Injectable({
  providedIn: 'root'
})
export class TeamService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  createTeam(requestModel: TeamRequestModel): Observable<TeamResponseModel> {
    return this.http.post<TeamResponseModel>(`${this.baseUrl}/teams`, requestModel);
  }

  addUserToTeam(userId: number, teamId: number): Observable<TeamResponseModel> {
    return this.http.post<TeamResponseModel>(`${this.baseUrl}/teams/${teamId}/users/${userId}`, {});
  }

  getAllTeamUsers(teamId: number): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}/teams/users/${teamId}`);
  }

  removeUserFromTeam(userId: number, teamId: number): Observable<TeamResponseModel> {
    return this.http.delete<TeamResponseModel>(`${this.baseUrl}/teams/${teamId}/users/${userId}`);
  }
}
