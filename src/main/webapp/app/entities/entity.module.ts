import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SuperPoliticosPoliticoSpModule } from './politico-sp/politico-sp.module';
import { SuperPoliticosTipoAtributoSpModule } from './tipo-atributo-sp/tipo-atributo-sp.module';
import { SuperPoliticosLogAtualizacaoSpModule } from './log-atualizacao-sp/log-atualizacao-sp.module';
import { SuperPoliticosPoliticoAtributoSpModule } from './politico-atributo-sp/politico-atributo-sp.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SuperPoliticosPoliticoSpModule,
        SuperPoliticosTipoAtributoSpModule,
        SuperPoliticosLogAtualizacaoSpModule,
        SuperPoliticosPoliticoAtributoSpModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SuperPoliticosEntityModule {}
