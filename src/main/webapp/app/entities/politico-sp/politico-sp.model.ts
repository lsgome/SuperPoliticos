import { BaseEntity } from './../../shared';

export const enum TipoPolitico {
    'VEREADORES',
    'DEPUTADOS_ESTADUAIS',
    'PREFEITOS',
    'DEPUTADOS_FEDERAIS',
    'SENADORES',
    'GOVERNADORES',
    'PRESIDENTES'
}

export class PoliticoSp implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public tipoPolitico?: TipoPolitico,
        public atributos?: BaseEntity[],
    ) {
    }
}
