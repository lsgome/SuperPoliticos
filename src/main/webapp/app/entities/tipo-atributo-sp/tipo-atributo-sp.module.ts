import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SuperPoliticosSharedModule } from '../../shared';
import {
    TipoAtributoSpService,
    TipoAtributoSpPopupService,
    TipoAtributoSpComponent,
    TipoAtributoSpDetailComponent,
    TipoAtributoSpDialogComponent,
    TipoAtributoSpPopupComponent,
    TipoAtributoSpDeletePopupComponent,
    TipoAtributoSpDeleteDialogComponent,
    tipoAtributoRoute,
    tipoAtributoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...tipoAtributoRoute,
    ...tipoAtributoPopupRoute,
];

@NgModule({
    imports: [
        SuperPoliticosSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TipoAtributoSpComponent,
        TipoAtributoSpDetailComponent,
        TipoAtributoSpDialogComponent,
        TipoAtributoSpDeleteDialogComponent,
        TipoAtributoSpPopupComponent,
        TipoAtributoSpDeletePopupComponent,
    ],
    entryComponents: [
        TipoAtributoSpComponent,
        TipoAtributoSpDialogComponent,
        TipoAtributoSpPopupComponent,
        TipoAtributoSpDeleteDialogComponent,
        TipoAtributoSpDeletePopupComponent,
    ],
    providers: [
        TipoAtributoSpService,
        TipoAtributoSpPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SuperPoliticosTipoAtributoSpModule {}
