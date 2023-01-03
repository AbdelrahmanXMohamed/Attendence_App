import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserModel } from 'src/app/models/UserModel';
import { userRequestModel } from 'src/app/models/UserRequestModel';
import { UserServices } from 'src/app/services/user/userServices';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  user:userRequestModel = {name:"",email:"",password:"",phone:"",username:""};

  constructor(private userService:UserServices,private router: Router){

  }


  register(event:any){
    event.preventDefault();
    console.log(this.user);
    
    this.userService.createUser(this.user).subscribe((result:string) => {
      alert("http://localhost:8080/registration/confirm?token=" + result);
    });
    this.router.navigate(['login'])
  }
}
