import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LetterBook } from 'app/shared/model/letter-book.model';
import { LetterBookService } from './letter-book.service';
import { LetterBookComponent } from './letter-book.component';
import { LetterBookDetailComponent } from './letter-book-detail.component';
import { LetterBookUpdateComponent } from './letter-book-update.component';
import { LetterBookDeletePopupComponent } from './letter-book-delete-dialog.component';
import { ILetterBook } from 'app/shared/model/letter-book.model';

@Injectable({ providedIn: 'root' })
export class LetterBookResolve implements Resolve<ILetterBook> {
    constructor(private service: LetterBookService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILetterBook> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<LetterBook>) => response.ok),
                map((letterBook: HttpResponse<LetterBook>) => letterBook.body)
            );
        }
        return of(new LetterBook());
    }
}

export const letterBookRoute: Routes = [
    {
        path: '',
        component: LetterBookComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LetterBooks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LetterBookDetailComponent,
        resolve: {
            letterBook: LetterBookResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LetterBooks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LetterBookUpdateComponent,
        resolve: {
            letterBook: LetterBookResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LetterBooks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LetterBookUpdateComponent,
        resolve: {
            letterBook: LetterBookResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LetterBooks'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const letterBookPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: LetterBookDeletePopupComponent,
        resolve: {
            letterBook: LetterBookResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LetterBooks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
