import { Moment } from 'moment';
import { IMembershipCard } from 'app/shared/model/membership-card.model';

export interface IMembership {
    id?: number;
    commencementDate?: Moment;
    fee?: number;
    card?: IMembershipCard;
}

export class Membership implements IMembership {
    constructor(public id?: number, public commencementDate?: Moment, public fee?: number, public card?: IMembershipCard) {}
}
