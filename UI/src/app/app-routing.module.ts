import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {path:"",loadChildren:() => import('./modules/welcome/welcome.module').then(m => m.WelcomeModule)},
  {path:"login",loadChildren:() => import('./modules/login/login.module').then(m => m.LoginModule)},
  {path:"signup",loadChildren:() => import('./modules/signup/signup.module').then(m => m.SignupModule)}

];

@NgModule({
  imports: [RouterModule.forRoot(routes),
    ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
