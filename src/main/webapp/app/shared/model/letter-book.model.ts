import { Moment } from 'moment';
import { ILetter } from 'app/shared/model/letter.model';

export interface ILetterBook {
    id?: number;
    incomingMailNumber?: number;
    incomingMailDate?: Moment;
    letters?: ILetter[];
}

export class LetterBook implements ILetterBook {
    constructor(public id?: number, public incomingMailNumber?: number, public incomingMailDate?: Moment, public letters?: ILetter[]) {}
}
