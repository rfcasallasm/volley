import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VolleySharedModule } from 'app/shared';
import {
    LetterBookComponent,
    LetterBookDetailComponent,
    LetterBookUpdateComponent,
    LetterBookDeletePopupComponent,
    LetterBookDeleteDialogComponent,
    letterBookRoute,
    letterBookPopupRoute
} from './';

const ENTITY_STATES = [...letterBookRoute, ...letterBookPopupRoute];

@NgModule({
    imports: [VolleySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LetterBookComponent,
        LetterBookDetailComponent,
        LetterBookUpdateComponent,
        LetterBookDeleteDialogComponent,
        LetterBookDeletePopupComponent
    ],
    entryComponents: [LetterBookComponent, LetterBookUpdateComponent, LetterBookDeleteDialogComponent, LetterBookDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VolleyLetterBookModule {}
