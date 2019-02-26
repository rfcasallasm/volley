import { NgModule } from '@angular/core';

import { VolleySharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [VolleySharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [VolleySharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class VolleySharedCommonModule {}
