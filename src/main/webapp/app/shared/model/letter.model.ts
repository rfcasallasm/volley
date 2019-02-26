import { Moment } from 'moment';
import { IPostalAddress } from 'app/shared/model/postal-address.model';
import { ILetterBook } from 'app/shared/model/letter-book.model';

export interface ILetter {
    id?: number;
    firstname?: string;
    surname?: string;
    birthDate?: Moment;
    sex?: boolean;
    telephoneNumber?: number;
    postalAddress?: IPostalAddress;
    letterBook?: ILetterBook;
}

export class Letter implements ILetter {
    constructor(
        public id?: number,
        public firstname?: string,
        public surname?: string,
        public birthDate?: Moment,
        public sex?: boolean,
        public telephoneNumber?: number,
        public postalAddress?: IPostalAddress,
        public letterBook?: ILetterBook
    ) {
        this.sex = this.sex || false;
    }
}
