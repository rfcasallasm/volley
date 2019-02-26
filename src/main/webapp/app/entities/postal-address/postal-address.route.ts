import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PostalAddress } from 'app/shared/model/postal-address.model';
import { PostalAddressService } from './postal-address.service';
import { PostalAddressComponent } from './postal-address.component';
import { PostalAddressDetailComponent } from './postal-address-detail.component';
import { PostalAddressUpdateComponent } from './postal-address-update.component';
import { PostalAddressDeletePopupComponent } from './postal-address-delete-dialog.component';
import { IPostalAddress } from 'app/shared/model/postal-address.model';

@Injectable({ providedIn: 'root' })
export class PostalAddressResolve implements Resolve<IPostalAddress> {
    constructor(private service: PostalAddressService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPostalAddress> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PostalAddress>) => response.ok),
                map((postalAddress: HttpResponse<PostalAddress>) => postalAddress.body)
            );
        }
        return of(new PostalAddress());
    }
}

export const postalAddressRoute: Routes = [
    {
        path: '',
        component: PostalAddressComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PostalAddresses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PostalAddressDetailComponent,
        resolve: {
            postalAddress: PostalAddressResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PostalAddresses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PostalAddressUpdateComponent,
        resolve: {
            postalAddress: PostalAddressResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PostalAddresses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PostalAddressUpdateComponent,
        resolve: {
            postalAddress: PostalAddressResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PostalAddresses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const postalAddressPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PostalAddressDeletePopupComponent,
        resolve: {
            postalAddress: PostalAddressResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PostalAddresses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
