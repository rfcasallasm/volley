import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILetter } from 'app/shared/model/letter.model';
import { LetterService } from './letter.service';

@Component({
    selector: 'jhi-letter-delete-dialog',
    templateUrl: './letter-delete-dialog.component.html'
})
export class LetterDeleteDialogComponent {
    letter: ILetter;

    constructor(protected letterService: LetterService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.letterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'letterListModification',
                content: 'Deleted an letter'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-letter-delete-popup',
    template: ''
})
export class LetterDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ letter }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LetterDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.letter = letter;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/letter', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/letter', { outlets: { popup: null } }]);
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
