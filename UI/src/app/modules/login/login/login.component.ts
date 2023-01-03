import { LocalService } from './../../../services/local/local.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginModel } from 'src/app/models/LoginModel';
import { LoginResponseModel } from 'src/app/models/LoginResponseModel';
import { UserServices } from 'src/app/services/user/userServices';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  user: LoginModel = { email: "", password: "" };

  constructor(private userService: UserServices, private router: Router, private localService: LocalService) {

  }

  login(event: any) {
    event.preventDefault();
    this.userService.login(this.user).subscribe((result: LoginResponseModel) => {
      this.localService.saveData('token', result.token)
      this.router.navigate(['profile'])
    }, err => {
      console.log(err)
    })
  }
}
