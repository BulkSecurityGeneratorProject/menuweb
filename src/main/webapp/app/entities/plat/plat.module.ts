import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MenuwebSharedModule } from '../../shared';
import { MenuwebAdminModule } from '../../admin/admin.module';
import {
    PlatService,
    PlatPopupService,
    PlatComponent,
    PlatDetailComponent,
    PlatDialogComponent,
    PlatPopupComponent,
    PlatDeletePopupComponent,
    PlatDeleteDialogComponent,
    platRoute,
    platPopupRoute,
} from './';

const ENTITY_STATES = [
    ...platRoute,
    ...platPopupRoute,
];

@NgModule({
    imports: [
        MenuwebSharedModule,
        MenuwebAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PlatComponent,
        PlatDetailComponent,
        PlatDialogComponent,
        PlatDeleteDialogComponent,
        PlatPopupComponent,
        PlatDeletePopupComponent,
    ],
    entryComponents: [
        PlatComponent,
        PlatDialogComponent,
        PlatPopupComponent,
        PlatDeleteDialogComponent,
        PlatDeletePopupComponent,
    ],
    providers: [
        PlatService,
        PlatPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MenuwebPlatModule {}
