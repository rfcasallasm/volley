import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILetter } from 'app/shared/model/letter.model';

type EntityResponseType = HttpResponse<ILetter>;
type EntityArrayResponseType = HttpResponse<ILetter[]>;

@Injectable({ providedIn: 'root' })
export class LetterService {
    public resourceUrl = SERVER_API_URL + 'api/letters';

    constructor(protected http: HttpClient) {}

    create(letter: ILetter): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(letter);
        return this.http
            .post<ILetter>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(letter: ILetter): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(letter);
        return this.http
            .put<ILetter>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ILetter>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILetter[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(letter: ILetter): ILetter {
        const copy: ILetter = Object.assign({}, letter, {
            birthDate: letter.birthDate != null && letter.birthDate.isValid() ? letter.birthDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.birthDate = res.body.birthDate != null ? moment(res.body.birthDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((letter: ILetter) => {
                letter.birthDate = letter.birthDate != null ? moment(letter.birthDate) : null;
            });
        }
        return res;
    }
}
