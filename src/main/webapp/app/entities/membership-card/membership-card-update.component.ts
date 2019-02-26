import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IMembershipCard } from 'app/shared/model/membership-card.model';
import { MembershipCardService } from './membership-card.service';
import { IPostalAddress } from 'app/shared/model/postal-address.model';
import { PostalAddressService } from 'app/entities/postal-address';

@Component({
    selector: 'jhi-membership-card-update',
    templateUrl: './membership-card-update.component.html'
})
export class MembershipCardUpdateComponent implements OnInit {
    membershipCard: IMembershipCard;
    isSaving: boolean;

    postaladdresses: IPostalAddress[];
    commencementDateDp: any;
    birthDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected membershipCardService: MembershipCardService,
        protected postalAddressService: PostalAddressService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ membershipCard }) => {
            this.membershipCard = membershipCard;
        });
        this.postalAddressService
            .query({ filter: 'membershipcard-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IPostalAddress[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPostalAddress[]>) => response.body)
            )
            .subscribe(
                (res: IPostalAddress[]) => {
                    if (!this.membershipCard.postalAddress || !this.membershipCard.postalAddress.id) {
                        this.postaladdresses = res;
                    } else {
                        this.postalAddressService
                            .find(this.membershipCard.postalAddress.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IPostalAddress>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IPostalAddress>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IPostalAddress) => (this.postaladdresses = [subRes].concat(res)),
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
        if (this.membershipCard.id !== undefined) {
            this.subscribeToSaveResponse(this.membershipCardService.update(this.membershipCard));
        } else {
            this.subscribeToSaveResponse(this.membershipCardService.create(this.membershipCard));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMembershipCard>>) {
        result.subscribe((res: HttpResponse<IMembershipCard>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPostalAddressById(index: number, item: IPostalAddress) {
        return item.id;
    }
}
