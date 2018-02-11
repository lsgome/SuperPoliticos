import { BaseEntity } from './../../shared';

export class LogAtualizacaoSp implements BaseEntity {
    constructor(
        public id?: number,
        public descricao?: string,
        public data?: any,
        public logReferencias?: BaseEntity[],
    ) {
    }
}
