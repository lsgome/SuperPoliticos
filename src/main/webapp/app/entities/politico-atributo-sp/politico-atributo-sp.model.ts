import { BaseEntity } from './../../shared';

export class PoliticoAtributoSp implements BaseEntity {
    constructor(
        public id?: number,
        public valor?: number,
        public politico?: BaseEntity,
        public tipoAtributo?: BaseEntity,
        public logAtualizacao?: BaseEntity,
    ) {
    }
}
