export interface IPostalAddress {
    id?: number;
    street?: string;
    houseNumber?: number;
    zipCode?: string;
    residence?: string;
}

export class PostalAddress implements IPostalAddress {
    constructor(
        public id?: number,
        public street?: string,
        public houseNumber?: number,
        public zipCode?: string,
        public residence?: string
    ) {}
}
