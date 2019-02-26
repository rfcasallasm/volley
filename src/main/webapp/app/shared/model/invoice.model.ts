import { Moment } from 'moment';
import { IMembership } from 'app/shared/model/membership.model';
import { IPayment } from 'app/shared/model/payment.model';

export interface IInvoice {
    id?: number;
    ammount?: number;
    invoiceDate?: Moment;
    fee?: IMembership;
    payments?: IPayment[];
}

export class Invoice implements IInvoice {
    constructor(
        public id?: number,
        public ammount?: number,
        public invoiceDate?: Moment,
        public fee?: IMembership,
        public payments?: IPayment[]
    ) {}
}
