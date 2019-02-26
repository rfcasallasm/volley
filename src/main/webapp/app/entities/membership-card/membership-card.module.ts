import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VolleySharedModule } from 'app/shared';
import {
    MembershipCardComponent,
    MembershipCardDetailComponent,
    MembershipCardUpdateComponent,
    MembershipCardDeletePopupComponent,
    MembershipCardDeleteDialogComponent,
    membershipCardRoute,
    membershipCardPopupRoute
} from './';

const ENTITY_STATES = [...membershipCardRoute, ...membershipCardPopupRoute];

@NgModule({
    imports: [VolleySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MembershipCardComponent,
        MembershipCardDetailComponent,
        MembershipCardUpdateComponent,
        MembershipCardDeleteDialogComponent,
        MembershipCardDeletePopupComponent
    ],
    entryComponents: [
        MembershipCardComponent,
        MembershipCardUpdateComponent,
        MembershipCardDeleteDialogComponent,
        MembershipCardDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VolleyMembershipCardModule {}
