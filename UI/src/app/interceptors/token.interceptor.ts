import { LocalService } from './../services/local/local.service';
import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
    constructor(private router: Router, private localService: LocalService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let authReq = req;
        if (this.localService.getData('token')) {
            authReq = req.clone({
                setHeaders: {
                    Authorization: `Bearer ${this.localService.getData('token')}`
                }
            })
        }
        console.log(this.localService.getData('token'))
        return next.handle(authReq).pipe(
            catchError((error: HttpErrorResponse) => {
                if (error.status === 401) {
                    this.router.navigate(['login']);
                }
                return throwError(error);
            })
        );
    }
}