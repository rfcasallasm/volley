import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILetterBook } from 'app/shared/model/letter-book.model';

@Component({
    selector: 'jhi-letter-book-detail',
    templateUrl: './letter-book-detail.component.html'
})
export class LetterBookDetailComponent implements OnInit {
    letterBook: ILetterBook;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ letterBook }) => {
            this.letterBook = letterBook;
        });
    }

    previousState() {
        window.history.back();
    }
}
