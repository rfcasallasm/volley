import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILetter } from 'app/shared/model/letter.model';

@Component({
    selector: 'jhi-letter-detail',
    templateUrl: './letter-detail.component.html'
})
export class LetterDetailComponent implements OnInit {
    letter: ILetter;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ letter }) => {
            this.letter = letter;
        });
    }

    previousState() {
        window.history.back();
    }
}
