import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILetterBook } from 'app/shared/model/letter-book.model';

type EntityResponseType = HttpResponse<ILetterBook>;
type EntityArrayResponseType = HttpResponse<ILetterBook[]>;

@Injectable({ providedIn: 'root' })
export class LetterBookService {
    public resourceUrl = SERVER_API_URL + 'api/letter-books';

    constructor(protected http: HttpClient) {}

    create(letterBook: ILetterBook): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(letterBook);
        return this.http
            .post<ILetterBook>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(letterBook: ILetterBook): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(letterBook);
        return this.http
            .put<ILetterBook>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ILetterBook>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILetterBook[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(letterBook: ILetterBook): ILetterBook {
        const copy: ILetterBook = Object.assign({}, letterBook, {
            incomingMailDate:
                letterBook.incomingMailDate != null && letterBook.incomingMailDate.isValid()
                    ? letterBook.incomingMailDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.incomingMailDate = res.body.incomingMailDate != null ? moment(res.body.incomingMailDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((letterBook: ILetterBook) => {
                letterBook.incomingMailDate = letterBook.incomingMailDate != null ? moment(letterBook.incomingMailDate) : null;
            });
        }
        return res;
    }
}
