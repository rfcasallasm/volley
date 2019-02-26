import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IMembership } from 'app/shared/model/membership.model';
import { MembershipService } from './membership.service';
import { IMembershipCard } from 'app/shared/model/membership-card.model';
import { MembershipCardService } from 'app/entities/membership-card';

@Component({
    selector: 'jhi-membership-update',
    templateUrl: './membership-update.component.html'
})
export class MembershipUpdateComponent implements OnInit {
    membership: IMembership;
    isSaving: boolean;

    cards: IMembershipCard[];
    commencementDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected membershipService: MembershipService,
        protected membershipCardService: MembershipCardService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ membership }) => {
            this.membership = membership;
        });
        this.membershipCardService
            .query({ filter: 'membership-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IMembershipCard[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMembershipCard[]>) => response.body)
            )
            .subscribe(
                (res: IMembershipCard[]) => {
                    if (!this.membership.card || !this.membership.card.id) {
                        this.cards = res;
                    } else {
                        this.membershipCardService
                            .find(this.membership.card.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IMembershipCard>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IMembershipCard>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IMembershipCard) => (this.cards = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.membership.id !== undefined) {
            this.subscribeToSaveResponse(this.membershipService.update(this.membership));
        } else {
            this.subscribeToSaveResponse(this.membershipService.create(this.membership));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMembership>>) {
        result.subscribe((res: HttpResponse<IMembership>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackMembershipCardById(index: number, item: IMembershipCard) {
        return item.id;
    }
}
