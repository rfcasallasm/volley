import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ILetter } from 'app/shared/model/letter.model';
import { LetterService } from './letter.service';
import { IPostalAddress } from 'app/shared/model/postal-address.model';
import { PostalAddressService } from 'app/entities/postal-address';
import { ILetterBook } from 'app/shared/model/letter-book.model';
import { LetterBookService } from 'app/entities/letter-book';

@Component({
    selector: 'jhi-letter-update',
    templateUrl: './letter-update.component.html'
})
export class LetterUpdateComponent implements OnInit {
    letter: ILetter;
    isSaving: boolean;

    postaladdresses: IPostalAddress[];

    letterbooks: ILetterBook[];
    birthDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected letterService: LetterService,
        protected postalAddressService: PostalAddressService,
        protected letterBookService: LetterBookService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ letter }) => {
            this.letter = letter;
        });
        this.postalAddressService
            .query({ filter: 'letter-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IPostalAddress[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPostalAddress[]>) => response.body)
            )
            .subscribe(
                (res: IPostalAddress[]) => {
                    if (!this.letter.postalAddress || !this.letter.postalAddress.id) {
                        this.postaladdresses = res;
                    } else {
                        this.postalAddressService
                            .find(this.letter.postalAddress.id)
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
        this.letterBookService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ILetterBook[]>) => mayBeOk.ok),
                map((response: HttpResponse<ILetterBook[]>) => response.body)
            )
            .subscribe((res: ILetterBook[]) => (this.letterbooks = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.letter.id !== undefined) {
            this.subscribeToSaveResponse(this.letterService.update(this.letter));
        } else {
            this.subscribeToSaveResponse(this.letterService.create(this.letter));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILetter>>) {
        result.subscribe((res: HttpResponse<ILetter>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackLetterBookById(index: number, item: ILetterBook) {
        return item.id;
    }
}
