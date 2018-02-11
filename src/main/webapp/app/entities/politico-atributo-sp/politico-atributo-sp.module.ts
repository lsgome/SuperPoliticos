import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SuperPoliticosSharedModule } from '../../shared';
import {
    PoliticoAtributoSpService,
    PoliticoAtributoSpPopupService,
    PoliticoAtributoSpComponent,
    PoliticoAtributoSpDetailComponent,
    PoliticoAtributoSpDialogComponent,
    PoliticoAtributoSpPopupComponent,
    PoliticoAtributoSpDeletePopupComponent,
    PoliticoAtributoSpDeleteDialogComponent,
    politicoAtributoRoute,
    politicoAtributoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...politicoAtributoRoute,
    ...politicoAtributoPopupRoute,
];

@NgModule({
    imports: [
        SuperPoliticosSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PoliticoAtributoSpComponent,
        PoliticoAtributoSpDetailComponent,
        PoliticoAtributoSpDialogComponent,
        PoliticoAtributoSpDeleteDialogComponent,
        PoliticoAtributoSpPopupComponent,
        PoliticoAtributoSpDeletePopupComponent,
    ],
    entryComponents: [
        PoliticoAtributoSpComponent,
        PoliticoAtributoSpDialogComponent,
        PoliticoAtributoSpPopupComponent,
        PoliticoAtributoSpDeleteDialogComponent,
        PoliticoAtributoSpDeletePopupComponent,
    ],
    providers: [
        PoliticoAtributoSpService,
        PoliticoAtributoSpPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SuperPoliticosPoliticoAtributoSpModule {}
