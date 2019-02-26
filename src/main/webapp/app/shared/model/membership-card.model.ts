import { Moment } from 'moment';
import { IPostalAddress } from 'app/shared/model/postal-address.model';

export interface IMembershipCard {
    id?: number;
    membershipNumber?: number;
    commencementDate?: Moment;
    name?: string;
    birthDate?: Moment;
    postalAddress?: IPostalAddress;
}

export class MembershipCard implements IMembershipCard {
    constructor(
        public id?: number,
        public membershipNumber?: number,
        public commencementDate?: Moment,
        public name?: string,
        public birthDate?: Moment,
        public postalAddress?: IPostalAddress
    ) {}
}
