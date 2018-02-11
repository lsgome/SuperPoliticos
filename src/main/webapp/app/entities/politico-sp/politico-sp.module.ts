import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SuperPoliticosSharedModule } from '../../shared';
import {
    PoliticoSpService,
    PoliticoSpPopupService,
    PoliticoSpComponent,
    PoliticoSpDetailComponent,
    PoliticoSpDialogComponent,
    PoliticoSpPopupComponent,
    PoliticoSpDeletePopupComponent,
    PoliticoSpDeleteDialogComponent,
    politicoRoute,
    politicoPopupRoute,
    PoliticoSpResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...politicoRoute,
    ...politicoPopupRoute,
];

@NgModule({
    imports: [
        SuperPoliticosSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PoliticoSpComponent,
        PoliticoSpDetailComponent,
        PoliticoSpDialogComponent,
        PoliticoSpDeleteDialogComponent,
        PoliticoSpPopupComponent,
        PoliticoSpDeletePopupComponent,
    ],
    entryComponents: [
        PoliticoSpComponent,
        PoliticoSpDialogComponent,
        PoliticoSpPopupComponent,
        PoliticoSpDeleteDialogComponent,
        PoliticoSpDeletePopupComponent,
    ],
    providers: [
        PoliticoSpService,
        PoliticoSpPopupService,
        PoliticoSpResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SuperPoliticosPoliticoSpModule {}
