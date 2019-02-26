import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VolleySharedModule } from 'app/shared';
import {
    LetterComponent,
    LetterDetailComponent,
    LetterUpdateComponent,
    LetterDeletePopupComponent,
    LetterDeleteDialogComponent,
    letterRoute,
    letterPopupRoute
} from './';

const ENTITY_STATES = [...letterRoute, ...letterPopupRoute];

@NgModule({
    imports: [VolleySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [LetterComponent, LetterDetailComponent, LetterUpdateComponent, LetterDeleteDialogComponent, LetterDeletePopupComponent],
    entryComponents: [LetterComponent, LetterUpdateComponent, LetterDeleteDialogComponent, LetterDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VolleyLetterModule {}
