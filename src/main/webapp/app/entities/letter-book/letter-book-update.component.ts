import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { ILetterBook } from 'app/shared/model/letter-book.model';
import { LetterBookService } from './letter-book.service';

@Component({
    selector: 'jhi-letter-book-update',
    templateUrl: './letter-book-update.component.html'
})
export class LetterBookUpdateComponent implements OnInit {
    letterBook: ILetterBook;
    isSaving: boolean;
    incomingMailDateDp: any;

    constructor(protected letterBookService: LetterBookService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ letterBook }) => {
            this.letterBook = letterBook;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.letterBook.id !== undefined) {
            this.subscribeToSaveResponse(this.letterBookService.update(this.letterBook));
        } else {
            this.subscribeToSaveResponse(this.letterBookService.create(this.letterBook));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILetterBook>>) {
        result.subscribe((res: HttpResponse<ILetterBook>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
