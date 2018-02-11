import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SuperPoliticosSharedModule } from '../../shared';
import {
    LogAtualizacaoSpService,
    LogAtualizacaoSpPopupService,
    LogAtualizacaoSpComponent,
    LogAtualizacaoSpDetailComponent,
    LogAtualizacaoSpDialogComponent,
    LogAtualizacaoSpPopupComponent,
    LogAtualizacaoSpDeletePopupComponent,
    LogAtualizacaoSpDeleteDialogComponent,
    logAtualizacaoRoute,
    logAtualizacaoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...logAtualizacaoRoute,
    ...logAtualizacaoPopupRoute,
];

@NgModule({
    imports: [
        SuperPoliticosSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LogAtualizacaoSpComponent,
        LogAtualizacaoSpDetailComponent,
        LogAtualizacaoSpDialogComponent,
        LogAtualizacaoSpDeleteDialogComponent,
        LogAtualizacaoSpPopupComponent,
        LogAtualizacaoSpDeletePopupComponent,
    ],
    entryComponents: [
        LogAtualizacaoSpComponent,
        LogAtualizacaoSpDialogComponent,
        LogAtualizacaoSpPopupComponent,
        LogAtualizacaoSpDeleteDialogComponent,
        LogAtualizacaoSpDeletePopupComponent,
    ],
    providers: [
        LogAtualizacaoSpService,
        LogAtualizacaoSpPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SuperPoliticosLogAtualizacaoSpModule {}
