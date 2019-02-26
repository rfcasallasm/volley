import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMembershipCard } from 'app/shared/model/membership-card.model';
import { MembershipCardService } from './membership-card.service';

@Component({
    selector: 'jhi-membership-card-delete-dialog',
    templateUrl: './membership-card-delete-dialog.component.html'
})
export class MembershipCardDeleteDialogComponent {
    membershipCard: IMembershipCard;

    constructor(
        protected membershipCardService: MembershipCardService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.membershipCardService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'membershipCardListModification',
                content: 'Deleted an membershipCard'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-membership-card-delete-popup',
    template: ''
})
export class MembershipCardDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ membershipCard }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MembershipCardDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.membershipCard = membershipCard;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/membership-card', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/membership-card', { outlets: { popup: null } }]);
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
