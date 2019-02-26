import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMembershipCard } from 'app/shared/model/membership-card.model';

type EntityResponseType = HttpResponse<IMembershipCard>;
type EntityArrayResponseType = HttpResponse<IMembershipCard[]>;

@Injectable({ providedIn: 'root' })
export class MembershipCardService {
    public resourceUrl = SERVER_API_URL + 'api/membership-cards';

    constructor(protected http: HttpClient) {}

    create(membershipCard: IMembershipCard): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(membershipCard);
        return this.http
            .post<IMembershipCard>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(membershipCard: IMembershipCard): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(membershipCard);
        return this.http
            .put<IMembershipCard>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IMembershipCard>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMembershipCard[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(membershipCard: IMembershipCard): IMembershipCard {
        const copy: IMembershipCard = Object.assign({}, membershipCard, {
            commencementDate:
                membershipCard.commencementDate != null && membershipCard.commencementDate.isValid()
                    ? membershipCard.commencementDate.format(DATE_FORMAT)
                    : null,
            birthDate:
                membershipCard.birthDate != null && membershipCard.birthDate.isValid() ? membershipCard.birthDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.commencementDate = res.body.commencementDate != null ? moment(res.body.commencementDate) : null;
            res.body.birthDate = res.body.birthDate != null ? moment(res.body.birthDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((membershipCard: IMembershipCard) => {
                membershipCard.commencementDate = membershipCard.commencementDate != null ? moment(membershipCard.commencementDate) : null;
                membershipCard.birthDate = membershipCard.birthDate != null ? moment(membershipCard.birthDate) : null;
            });
        }
        return res;
    }
}
