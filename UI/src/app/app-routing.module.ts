import { LoginGuard } from './guards/login.guard';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { RouterModule, Routes, CanActivate } from '@angular/router';
import { NgModule } from '@angular/core';
import { AuthGuard } from './guards/auth.guard';

const routes: Routes = [
  { path: "", canActivate: [LoginGuard], loadChildren: () => import('./modules/welcome/welcome.module').then(m => m.WelcomeModule) },
  { path: "login", canActivate: [LoginGuard], loadChildren: () => import('./modules/login/login.module').then(m => m.LoginModule) },
  { path: "signup", canActivate: [LoginGuard], loadChildren: () => import('./modules/signup/signup.module').then(m => m.SignupModule) },
  { path: "profile", canActivate: [AuthGuard], loadChildren: () => import('./modules/profile/profile.module').then(m => m.ProfileModule) },
  { path: "teams/:id", canActivate: [AuthGuard], loadChildren: () => import('./modules/team/team.module').then(m => m.TeamModule) },
  { path: "member/:id", canActivate: [AuthGuard], loadChildren: () => import('./modules/member/member.module').then(m => m.MemberModule) },
  { path: '**', pathMatch: 'full', component: NotFoundComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes),
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
