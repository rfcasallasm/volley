import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MembershipCard } from 'app/shared/model/membership-card.model';
import { MembershipCardService } from './membership-card.service';
import { MembershipCardComponent } from './membership-card.component';
import { MembershipCardDetailComponent } from './membership-card-detail.component';
import { MembershipCardUpdateComponent } from './membership-card-update.component';
import { MembershipCardDeletePopupComponent } from './membership-card-delete-dialog.component';
import { IMembershipCard } from 'app/shared/model/membership-card.model';

@Injectable({ providedIn: 'root' })
export class MembershipCardResolve implements Resolve<IMembershipCard> {
    constructor(private service: MembershipCardService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMembershipCard> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MembershipCard>) => response.ok),
                map((membershipCard: HttpResponse<MembershipCard>) => membershipCard.body)
            );
        }
        return of(new MembershipCard());
    }
}

export const membershipCardRoute: Routes = [
    {
        path: '',
        component: MembershipCardComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MembershipCards'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MembershipCardDetailComponent,
        resolve: {
            membershipCard: MembershipCardResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MembershipCards'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MembershipCardUpdateComponent,
        resolve: {
            membershipCard: MembershipCardResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MembershipCards'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MembershipCardUpdateComponent,
        resolve: {
            membershipCard: MembershipCardResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MembershipCards'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const membershipCardPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MembershipCardDeletePopupComponent,
        resolve: {
            membershipCard: MembershipCardResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MembershipCards'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
