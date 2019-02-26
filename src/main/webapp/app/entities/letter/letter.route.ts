import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Letter } from 'app/shared/model/letter.model';
import { LetterService } from './letter.service';
import { LetterComponent } from './letter.component';
import { LetterDetailComponent } from './letter-detail.component';
import { LetterUpdateComponent } from './letter-update.component';
import { LetterDeletePopupComponent } from './letter-delete-dialog.component';
import { ILetter } from 'app/shared/model/letter.model';

@Injectable({ providedIn: 'root' })
export class LetterResolve implements Resolve<ILetter> {
    constructor(private service: LetterService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILetter> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Letter>) => response.ok),
                map((letter: HttpResponse<Letter>) => letter.body)
            );
        }
        return of(new Letter());
    }
}

export const letterRoute: Routes = [
    {
        path: '',
        component: LetterComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Letters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LetterDetailComponent,
        resolve: {
            letter: LetterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Letters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LetterUpdateComponent,
        resolve: {
            letter: LetterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Letters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LetterUpdateComponent,
        resolve: {
            letter: LetterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Letters'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const letterPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: LetterDeletePopupComponent,
        resolve: {
            letter: LetterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Letters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
