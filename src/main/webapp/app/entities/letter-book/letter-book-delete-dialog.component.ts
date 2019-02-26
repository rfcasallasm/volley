import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILetterBook } from 'app/shared/model/letter-book.model';
import { LetterBookService } from './letter-book.service';

@Component({
    selector: 'jhi-letter-book-delete-dialog',
    templateUrl: './letter-book-delete-dialog.component.html'
})
export class LetterBookDeleteDialogComponent {
    letterBook: ILetterBook;

    constructor(
        protected letterBookService: LetterBookService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.letterBookService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'letterBookListModification',
                content: 'Deleted an letterBook'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-letter-book-delete-popup',
    template: ''
})
export class LetterBookDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ letterBook }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LetterBookDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.letterBook = letterBook;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/letter-book', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/letter-book', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
