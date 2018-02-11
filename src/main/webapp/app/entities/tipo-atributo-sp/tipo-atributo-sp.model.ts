import { BaseEntity } from './../../shared';

export const enum TipoValorAtributo {
    'INTEIRO_CRESCENTE',
    'INTEIRO_DECRESCENTE'
}

export class TipoAtributoSp implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public tipo?: string,
        public tipoValorAtributo?: TipoValorAtributo,
        public atributos?: BaseEntity[],
    ) {
    }
}
