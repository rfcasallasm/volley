import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VolleySharedModule } from 'app/shared';
import {
    PostalAddressComponent,
    PostalAddressDetailComponent,
    PostalAddressUpdateComponent,
    PostalAddressDeletePopupComponent,
    PostalAddressDeleteDialogComponent,
    postalAddressRoute,
    postalAddressPopupRoute
} from './';

const ENTITY_STATES = [...postalAddressRoute, ...postalAddressPopupRoute];

@NgModule({
    imports: [VolleySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PostalAddressComponent,
        PostalAddressDetailComponent,
        PostalAddressUpdateComponent,
        PostalAddressDeleteDialogComponent,
        PostalAddressDeletePopupComponent
    ],
    entryComponents: [
        PostalAddressComponent,
        PostalAddressUpdateComponent,
        PostalAddressDeleteDialogComponent,
        PostalAddressDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VolleyPostalAddressModule {}
