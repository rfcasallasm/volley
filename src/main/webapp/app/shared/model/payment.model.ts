import { Moment } from 'moment';
import { IInvoice } from 'app/shared/model/invoice.model';

export interface IPayment {
    id?: number;
    paymentNumber?: number;
    paymentDate?: Moment;
    paymentAmount?: number;
    invoice?: IInvoice;
}

export class Payment implements IPayment {
    constructor(
        public id?: number,
        public paymentNumber?: number,
        public paymentDate?: Moment,
        public paymentAmount?: number,
        public invoice?: IInvoice
    ) {}
}
