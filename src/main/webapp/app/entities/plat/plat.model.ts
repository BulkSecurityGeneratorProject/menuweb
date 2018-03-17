import { BaseEntity } from './../../shared';

export const enum TypePlat {
    'ENTREE',
    'PLAT_UNIQUE',
    'ACCOMPAGNEMENT',
    'PROTEINE',
    'DESSERT'
}

export class Plat implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public type?: TypePlat,
        public description?: string,
        public createurId?: number,
        public tags?: BaseEntity[],
    ) {
    }
}
