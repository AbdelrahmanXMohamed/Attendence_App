import { NotFoundComponent } from './components/not-found/not-found.component';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

const routes: Routes = [
  { path: "", loadChildren: () => import('./modules/welcome/welcome.module').then(m => m.WelcomeModule) },
  { path: "login", loadChildren: () => import('./modules/login/login.module').then(m => m.LoginModule) },
  { path: "signup", loadChildren: () => import('./modules/signup/signup.module').then(m => m.SignupModule) },
  { path: "profile", loadChildren: () => import('./modules/profile/profile.module').then(m => m.ProfileModule) },
  { path: "teams/:id", loadChildren: () => import('./modules/team/team.module').then(m => m.TeamModule) },
  { path: "member/:id", loadChildren: () => import('./modules/member/member.module').then(m => m.MemberModule) },
  { path: '**', pathMatch: 'full', component: NotFoundComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes),
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
